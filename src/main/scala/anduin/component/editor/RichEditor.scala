// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor

import org.scalajs.dom.KeyboardEvent

import anduin.component.editor.renderer.{ImageRenderer, LinkRenderer, TextAlignRenderer}
import anduin.component.util.ComponentUtils
import anduin.scalajs.slate.SlateReact
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

  private class Backend(scope: BackendScope[Props, _]) {

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

    def render(props: Props): VdomElement = {
      <.div(
        ComponentUtils.testId(this, "ContentEditor"),
        ^.cls := "editor",
        SlateReact.component.withRef(props.ref())(
          SlateReact.props(
            autoFocusParam = !props.readOnly,
            placeholderParam = props.placeholder,
            valueParam = props.value,
            readOnlyParam = props.readOnly,
            onChangeParam = props.onChange,
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
    .stateless
    .renderBackend[Backend]
    .build
}
