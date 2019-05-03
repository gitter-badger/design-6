// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.tree

import anduin.component.button.Button
import anduin.component.icon.Icon
import anduin.style.Style

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object Tree {

  sealed trait Loader[A] {
    def hasChildren: A => Boolean
  }
  object Loader {
    case class Sync[A](loadSync: A => Seq[A], hasChildrenOpt: Option[A => Boolean] = None) extends Loader[A] {
      def hasChildren: A => Boolean = hasChildrenOpt.getOrElse(loadSync(_).nonEmpty)
    }
    case class Async[A](hasChildren: A => Boolean, loadAsync: A => Future[Seq[A]]) extends Loader[A]
  }

  // Props to use to render a node (see class Tree#render)
  case class NodeRenderProps[A](
    node: A,
    // ancestors of current node, from furthest to closest (direct parent)
    // - ie. ancestorNodes.tail is parent of current node
    ancestorNodes: Seq[A],
    toggle: Callback,
    isExpanded: Boolean
  )
}

// A is the type of a node
class Tree[A] {

  def apply(): Props.type = Props

  case class Props(
    node: A, // The current node
    getKey: A => String, // How to stringify a node
    loader: Tree.Loader[A], // How to load child nodes of a node

    // How to render a node.
    // - Return Some to specify the right side of the ">" button
    // - Return None to not render anything at all, including the ">" button
    render: Tree.NodeRenderProps[A] => Option[VdomElement],
    // The parent nodes of the current node.
    // - This is mostly used to render a node (see Tree.NodeRenderProps) and
    //   is passed by the Tree component to non-root nodes.
    // - The consumers, who pass the root node, usually don't need this
    ancestorNodes: Seq[A] = Seq.empty,
    // Should a node expanded automatically
    // - This will be called on both didMount and didUpdate
    // - This is NOT a controlled prop. Think of this as getDerivedStateFromProps
    //   which works like a condition when there are changes from parent to
    //   turn state.isExpanded to true if it is currently false.
    // - eg. if state.isExpanded is true already then this has no effect
    // - eg. users can still turn state.isExpanded to false afterward
    // - IMPORTANT: state.isExpanded is still the single source of truth
    shouldExpanded: A => Boolean = _ => false,
    rendererOnExpand: (A, A => VdomElement) => VdomElement = (data, renderer) => renderer(data)
  ) {
    def apply(): VdomElement = component(this)
  }

  case class State(
    isExpanded: Boolean,
    // the "children" state should be used to store children in the case of
    // asyncLoader only. For sync loader, simply get children on "render"
    asyncChildren: Seq[A]
  )

  private class Backend(scope: BackendScope[Props, State]) {

    def setAsyncChildren(children: Seq[A]): Callback = scope.modState(_.copy(asyncChildren = children))
    def setIsExpanded(isExpanded: Boolean): Callback = scope.modState(_.copy(isExpanded = isExpanded))
    def toggle: Callback = scope.modState(state => state.copy(isExpanded = !state.isExpanded))

    private def renderButton(props: Props, state: State): VdomNode = {
      val hasChildren = props.loader.hasChildren(props.node)
      if (hasChildren) {
        val icon = if (state.isExpanded) Icon.Glyph.CaretDown else Icon.Glyph.CaretRight
        val style = Button.Style.Minimal(icon = Some(icon), height = Button.Height.Fix24)
        Button(style = style, onClick = toggle)()
      } else {
        <.div(^.width := "24px", ^.height := "24px")
      }
    }

    private val indent = TagMod(
      Style.border.left.borderColor.gray3.padding.left8,
      ^.marginLeft := "11px" // 12px - 1px of the border
    )

    private def renderChildren(props: Props, state: State, content: Option[VdomElement]): VdomNode = {
      val childrenNodes = props.loader match {
        case _: Tree.Loader.Async[A]     => state.asyncChildren
        case loader: Tree.Loader.Sync[A] => loader.loadSync(props.node)
      }
      val childrenVdoms = childrenNodes.toVdomArray { node =>
        val ancestorNodes = props.ancestorNodes :+ props.node
        val element = props.copy(node = node, ancestorNodes = ancestorNodes)()
        <.div(^.key := props.getKey(node), element)
      }
      <.div(
        TagMod.when(!state.isExpanded)(Style.display.none),
        TagMod.when(content.isDefined)(indent),
        childrenVdoms
      )
    }

    private def renderBody(props: Props, state: State, contentOpt: Option[VdomElement]): Option[VdomElement] = {
      contentOpt.map { content =>
        <.div(
          Style.flexbox.flex.flexbox.itemsStart,
          <.div(Style.flexbox.none, renderButton(props, state)),
          <.div(Style.flexbox.fill, content)
        )
      }
    }

    private def renderSubTree(props: Props, state: State) = {
      val rProps = Tree.NodeRenderProps[A](props.node, props.ancestorNodes, toggle, state.isExpanded)
      val content = props.render(rProps)
      <.div(
        <.div(renderBody(props, state, content)),
        <.div(renderChildren(props, state, content))
      )
    }

    def render(props: Props, state: State): VdomElement = {
      // Call rendererOnExpand to fetch data when the node is expanded. However, the root doesn't have any parent but
      // is always present, it should also fetches data.
      if (state.isExpanded || props.ancestorNodes.isEmpty) {
        props.rendererOnExpand(props.node, completeNode => renderSubTree(props.copy(node = completeNode), state))
      } else {
        renderSubTree(props, state)
      }
    }
  }

  private def loadAsyncChildren(props: Props, state: State, cb: Seq[A] => Callback): Callback = {
    Callback.when(state.isExpanded) {
      props.loader match {
        case async: Tree.Loader.Async[A] => Callback.future(async.loadAsync(props.node).map(cb))
        case _: Tree.Loader.Sync[A]      => Callback.empty
      }
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialStateFromProps(props => {
      State(isExpanded = props.shouldExpanded(props.node), asyncChildren = Seq.empty)
    })
    .renderBackend[Backend]
    .componentDidMount(scope => {
      loadAsyncChildren(scope.props, scope.state, scope.backend.setAsyncChildren)
    })
    .componentDidUpdate { scope =>
      for {
        // reload async children if needed
        _ <- Callback.when(
          scope.prevProps.node != scope.currentProps.node ||
            scope.prevState.isExpanded != scope.currentState.isExpanded
        ) {
          loadAsyncChildren(scope.currentProps, scope.currentState, scope.backend.setAsyncChildren)
        }
        // set isExpanded if needed
        _ <- Callback.when({
          scope.currentProps != scope.prevProps &&
          scope.currentProps.shouldExpanded(scope.currentProps.node)
        })(scope.backend.setIsExpanded(true))
      } yield ()
    }
    .build
}
