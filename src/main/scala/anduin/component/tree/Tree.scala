// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.tree

import anduin.component.button.Button
import anduin.component.icon.Icon
import anduin.style.Style

import scala.concurrent.Future

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object Tree {
  object Loader {
    case class Sync[A](loadSync: A => Seq[A]) extends TreeLoader.Sync[A]
    case class Async[A](hasChildren: A => Boolean, loadAsync: A => Future[Seq[A]]) extends TreeLoader.Async[A]
  }
}

class Tree[A] {

  def apply(): Props.type = Props

  case class RenderProps(
    node: A,
    parents: Seq[A],
    toggle: Callback,
    isOpened: Boolean
  )

  case class Props(
    node: A,
    render: RenderProps => VdomElement,
    getKey: A => String,
    loader: TreeLoader[A],
    parentNodes: Seq[A] = Seq.empty,
    shouldOpen: A => Boolean = _ => false,
    isSelfHidden: Boolean = false
  ) {
    def apply(): VdomElement = component(this)
  }

  case class State(isOpened: Boolean, children: Seq[A])

  private class Backend(scope: BackendScope[Props, State]) {

    def setChildren(children: Seq[A]): Callback =
      scope.modState(_.copy(children = children))

    def setOpened(isOpened: Boolean): Callback =
      scope.modState(_.copy(isOpened = isOpened))

    private def toggle(props: Props, state: State): Callback = scope.modState(
      _.copy(isOpened = !state.isOpened),
      Callback.when(!state.isOpened)(props.loader.load(props.node, setChildren))
    )

    private def renderButton(props: Props, state: State): VdomNode = {
      if (props.loader.hasChildren(props.node)) {
        val icon = if (state.isOpened) Icon.Glyph.CaretDown else Icon.Glyph.CaretRight
        Button(
          style = Button.Style.Minimal(icon = Some(icon), height = Button.Height.Fix24),
          onClick = toggle(props, state)
        )()
      } else {
        <.div(^.width := "24px", ^.height := "24px")
      }
    }

    private def renderChildren(props: Props, state: State): VdomNode = {
      val children = state.children.toVdomArray { node =>
        val element = props.copy(
          node = node,
          parentNodes = props.parentNodes :+ props.node,
          shouldOpen = props.shouldOpen,
          isSelfHidden = false
        )()
        <.div(^.key := props.getKey(node), element)
      }
      <.div(
        TagMod.when(!state.isOpened)(Style.display.none),
        Style.border.left.borderColor.gray3.padding.left8,
        ^.marginLeft := "11px", // 12px - 1px of the border
        children
      )
    }

    private def renderContent(props: Props, state: State): VdomElement = {
      val rProps = RenderProps(
        props.node,
        props.parentNodes,
        toggle(props, state),
        state.isOpened
      )
      props.render(rProps)
    }

    def render(props: Props, state: State): VdomElement = {
      <.div(
        TagMod.unless(props.isSelfHidden)(
          <.div(
            Style.flexbox.flex.flexbox.itemsStart,
            <.div(Style.flexbox.none, renderButton(props, state)),
            <.div(Style.flexbox.fixed, renderContent(props, state))
          )
        ),
        <.div(renderChildren(props, state))
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialStateFromProps(props => {
      State(isOpened = props.shouldOpen(props.node), children = Seq.empty)
    })
    .renderBackend[Backend]
    .componentDidMount(scope => {
      Callback.when(scope.state.isOpened) {
        scope.props.loader.load(scope.props.node, scope.backend.setChildren)
      }
    })
    .componentDidUpdate { scope =>
      Callback.when(scope.prevProps.shouldOpen != scope.currentProps.shouldOpen) {
        scope.backend.setOpened(scope.currentProps.shouldOpen(scope.currentProps.node))
      } >>
        Callback.when(
          scope.prevProps.node != scope.currentProps.node || (scope.currentState.isOpened != scope.prevState.isOpened && scope.currentState.isOpened)
        ) {
          scope.currentProps.loader.load(scope.currentProps.node, scope.backend.setChildren)
        }
    }
    .build
}
