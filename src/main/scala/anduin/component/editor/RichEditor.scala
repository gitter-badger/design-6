// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor

import org.scalajs.dom.KeyboardEvent

import anduin.component.editor.renderer.{ImageRenderer, LinkRenderer, MarkRenderer, TextAlignRenderer}
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
  renderWrapper: (Callback, VdomElement) => VdomElement = (_, editor: VdomElement) => editor
) {
  def apply(): VdomElement = RichEditor.component(this)
}

object RichEditor {

  private val ComponentName = this.getClass.getSimpleName

  private class Backend(scope: BackendScope[RichEditor, _]) {

    private val editorRef = Ref.toJsComponent(SlateReact.component)

    private def onKeyDown(e: KeyboardEvent, change: Editor) = {
      Callback.when(e.metaKey) {
        e.key match {
          case "b" =>
            Callback {
              e.preventDefault()
              change.toggleMark(BoldNode.nodeType)
            }
          case "i" =>
            Callback {
              e.preventDefault()
              change.toggleMark(ItalicNode.nodeType)
            }
          case "u" =>
            Callback {
              e.preventDefault()
              change.toggleMark(UnderlineNode.nodeType)
            }
          case "z" =>
            e.preventDefault()
            for {
              props <- scope.props
              _ <- Callback.when(props.value.hasUndos)(props.onChange(change.undo().value))
            } yield ()
          case "y" =>
            e.preventDefault()
            for {
              props <- scope.props
              _ <- Callback.when(props.value.hasRedos)(props.onChange(change.redo().value))
            } yield ()
          case _ => Callback.empty
        }
      }
    }

    // scalastyle:off cyclomatic.complexity
    private def renderNode(editor: RichEditor)(props: SlateReact.RenderNodeProps) = {
      val data = props.node.data
      val children = PropsChildren.fromRawProps(props)
      props.node.nodeType match {
        case BlockQuoteNode.nodeType    => <.blockquote(children).rawElement
        case ParagraphNode.nodeType     => <.p(Style.margin.bottom12, children).rawElement
        case CodeNode.nodeType          => <.pre(<.code(children)).rawElement
        case OrderedListNode.nodeType   => <.ol(children).rawElement
        case UnorderedListNode.nodeType => <.ul(children).rawElement
        case ListItemNode.nodeType      => <.li(children).rawElement
        case DivNode.nodeType           => <.div(children).rawElement
        case LinkNode.nodeType          => LinkRenderer(data, props.children, editor.readOnly)
        case ImageNode.nodeType         => ImageRenderer(data)
        case TextAlignNode.nodeType     => TextAlignRenderer(data, props.children)
      }
    }
    // scalastyle:on cyclomatic.complexity

    private def focus() = {
      editorRef.get.map(_.raw.focus()).toCallback
    }

    def render(props: RichEditor): VdomElement = {
      props.renderWrapper(
        focus(),
        <.div(
          ComponentUtils.testId(this, "ContentEditor"),
          ^.cls := "editor",
          editorRef.component(
            SlateReact.props(
              placeholder = props.placeholder,
              value = props.value,
              readOnly = props.readOnly,
              onChange = props.onChange,
              onKeyDown = onKeyDown,
              renderNode = renderNode(props),
              renderMark = (props: SlateReact.RenderMarkProps) => MarkRenderer(props.mark, props.children)
            )
          )
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[RichEditor](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}
