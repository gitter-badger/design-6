// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor

import org.scalajs.dom.KeyboardEvent

import anduin.component.editor.Editor.{RenderMarkProps, RenderNodeProps}
import anduin.component.editor.renderer.{
  ImageRenderer,
  LinkRenderer,
  MarkRenderer,
  TextAlignRenderer
}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.scalajs.slate.Slate._
// scalastyle:on underscore.import

final case class RichEditor(
    placeholder: String,
    value: Value,
    onChange: Change => Callback,
    readOnly: Boolean
) {
  def apply(): ScalaComponent.Unmounted[_, _, _] = RichEditor.component(this)
}

object RichEditor {

  private final val ComponentName = this.getClass.getSimpleName

  private case class Backend(scope: BackendScope[RichEditor, _]) {

    private def onKeyDown(e: KeyboardEvent, change: Change) = {
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
              _ <- Callback.when(props.value.hasUndos)(props.onChange(change.undo()))
            } yield ()
          case "y" =>
            e.preventDefault()
            for {
              props <- scope.props
              _ <- Callback.when(props.value.hasRedos)(props.onChange(change.redo()))
            } yield ()
          case _ => Callback.empty
        }
      }
    }

    // scalastyle:off cyclomatic.complexity
    private def renderNode(props: RenderNodeProps) = {
      val data = props.node.data
      val children = PropsChildren.fromRawProps(props)
      props.node.nodeType match {
        case BlockQuoteNode.nodeType    => <.blockquote(children).rawElement
        case ParagraphNode.nodeType     => <.p(children).rawElement
        case CodeNode.nodeType          => <.pre(<.code(children)).rawElement
        case OrderedListNode.nodeType   => <.ol(children).rawElement
        case UnorderedListNode.nodeType => <.ul(children).rawElement
        case ListItemNode.nodeType      => <.li(children).rawElement
        case LinkNode.nodeType          => LinkRenderer(data, props.children)
        case ImageNode.nodeType         => ImageRenderer(data)
        case TextAlignNode.nodeType     => TextAlignRenderer(data, props.children)
      }
    }
    // scalastyle:on cyclomatic.complexity

    def render(props: RichEditor): VdomElement = {
      <.div(
        ^.cls := "editor",
        Editor(
          placeholder = props.placeholder,
          value = props.value,
          readOnly = props.readOnly,
          onChange = props.onChange,
          onKeyDown = onKeyDown,
          renderNode = renderNode,
          renderMark = (props: RenderMarkProps) => MarkRenderer(props.mark, props.children)
        )()
      )
    }
  }

  private val component = ScalaComponent
    .builder[RichEditor](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}
