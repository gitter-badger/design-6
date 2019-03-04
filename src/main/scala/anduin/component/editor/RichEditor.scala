// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor

import scala.scalajs.js

import org.scalajs.dom.KeyboardEvent

import anduin.component.editor.renderer.{ImageRenderer, LinkRenderer, TextAlignRenderer}
import anduin.component.util.ComponentUtils
import anduin.scalajs.slate.SlateReact
import anduin.scalajs.slate.SlateReact.Change
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.scalajs.slate.Slate._
// scalastyle:on underscore.import

final case class RichEditor(
  placeholder: String,
  value: Value,
  onChange: Value => Callback,
  readOnly: Boolean,
  ref: () => SlateReact.EditorComponentRef
) {
  def apply(): VdomElement = RichEditor.component(this)
}

object RichEditor {

  private type Props = RichEditor

  private case class State(value: Value)

  private class Backend(scope: BackendScope[Props, State]) {

    private def onKeyDown(e: KeyboardEvent, editor: Editor, next: SlateReact.KeyDownNextFn) = {
      if (e.metaKey) {
        e.key match {
          case "b" =>
            Callback {
              e.preventDefault()
              editor.toggleMark(BoldNode.nodeType)
            }
          case "i" =>
            Callback {
              e.preventDefault()
              editor.toggleMark(ItalicNode.nodeType)
            }
          case "u" =>
            Callback {
              e.preventDefault()
              editor.toggleMark(UnderlineNode.nodeType)
            }
          case "z" =>
            e.preventDefault()
            for {
              props <- scope.props
              _ <- Callback.when(SlateUtil.hasUndo(props.value))(props.onChange(editor.undo().value))
            } yield ()
          case "y" =>
            e.preventDefault()
            for {
              props <- scope.props
              _ <- Callback.when(SlateUtil.hasRedo(props.value))(props.onChange(editor.redo().value))
            } yield ()
          case _ => Callback(next())
        }
      } else {
        Callback(next())
      }
    }

    // scalastyle:off cyclomatic.complexity
    private def renderNode(
      renderNodeProps: SlateReact.RenderNodeProps,
      editor: Editor,
      next: SlateReact.RenderNextFn
    ) = {
      val data = renderNodeProps.node.data
      val children = PropsChildren.fromRawProps(renderNodeProps)
      renderNodeProps.node.nodeType match {
        case BlockQuoteNode.nodeType    => <.blockquote(children).rawElement
        case ParagraphNode.nodeType     => <.p(Style.margin.bottom12, children).rawElement
        case CodeNode.nodeType          => <.pre(<.code(children)).rawElement
        case OrderedListNode.nodeType   => <.ol(children).rawElement
        case UnorderedListNode.nodeType => <.ul(children).rawElement
        case ListItemNode.nodeType      => <.li(children).rawElement
        case DivNode.nodeType           => <.div(children).rawElement
        case LinkNode.nodeType          => LinkRenderer(data, renderNodeProps.children, editor.readOnly)
        case ImageNode.nodeType         => ImageRenderer(data)
        case TextAlignNode.nodeType     => TextAlignRenderer(data, renderNodeProps.children)
        case _                          => next()
      }
    }
    // scalastyle:on cyclomatic.complexity

    private def renderMark(
      renderMarkProps: SlateReact.RenderMarkProps,
      editor: Editor,
      next: SlateReact.RenderNextFn
    ) = {
      val _ = editor
      val childrenEle = PropsChildren.fromRawProps(renderMarkProps)
      renderMarkProps.mark.markType match {
        case BoldNode.nodeType          => <.strong(childrenEle).rawElement
        case ItalicNode.nodeType        => <.em(childrenEle).rawElement
        case UnderlineNode.nodeType     => <.u(childrenEle).rawElement
        case StrikeThroughNode.nodeType => <.del(childrenEle).rawElement
        case _                          => next()
      }
    }

    private def onChange(change: Change) = {
      for {
        props <- scope.props
        // Slate always triggers `onChange` even if there's no actual changes in the document.
        // For example, it is called when you focus on the editor.
        // In order to avoid unnecessary saving, we need to check if there is at least one "edit" operation
        skipSaving = change.operations.forall { operation =>
          operation.tpe == "set_selection" ||
          (operation.tpe == "set_value" &&
          operation.properties.toOption.exists(js.Object.keys(_).forall(_ == "decorations")))
        }
        _ <- scope.modState(
          _.copy(value = change.value),
          Callback.unless(skipSaving) {
            props.onChange(change.value)
          }
        )
      } yield ()
    }

    def render(props: Props, state: State): VdomElement = {
      <.div(
        ComponentUtils.testId(this, "ContentEditor"),
        ^.cls := "editor",
        SlateReact.component.withRef(props.ref())(
          SlateReact.props(
            autoFocusParam = !props.readOnly,
            placeholderParam = props.placeholder,
            valueParam = state.value,
            readOnlyParam = props.readOnly,
            onChangeParam = onChange,
            onKeyDownParam = onKeyDown,
            renderNodeParam = renderNode,
            renderMarkParam = renderMark
          )
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialStateFromProps { props =>
      State(props.value)
    }
    .renderBackend[Backend]
    .build
}
