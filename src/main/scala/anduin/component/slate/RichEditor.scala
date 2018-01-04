// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.slate

import org.scalajs.dom.KeyboardEvent

import anduin.component.slate.Editor.{RenderMarkProps, RenderNodeProps}

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
          case "b" => Callback {
            e.preventDefault()
            change.toggleMark(BoldNode.nodeType)
          }
          case "i" => Callback {
            e.preventDefault()
            change.toggleMark(ItalicNode.nodeType)
          }
          case "u" => Callback {
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
        case BlockQuoteNode.nodeType => <.blockquote(children).rawElement
        case ParagraphNode.nodeType => <.p(children).rawElement
        case CodeNode.nodeType => <.pre(<.code(children)).rawElement
        case OrderedListNode.nodeType => <.ol(children).rawElement
        case UnorderedListNode.nodeType => <.ul(children).rawElement
        case ListItemNode.nodeType => <.li(children).rawElement
        case LinkNode.nodeType =>
          val href = DataUtil.value(data, "href")
          <.a(^.href := href, children).rawElement
        case ImageNode.nodeType => ImageNodeRenderer(data).rawElement

        // With text alignment
        case TextAlignNode.nodeType =>
          val textAlign = DataUtil.value(data, "textAlign")
          val textAlignAttr = TagMod.when(textAlign.nonEmpty)(^.textAlign := textAlign)

          // Keep the original node type if it supports text alignment
          val originalType = DataUtil.value(data, "originalType")
          originalType match {
            case ParagraphNode.nodeType => <.p(textAlignAttr, children).rawElement
            case OrderedListNode.nodeType => <.ol(textAlignAttr, children).rawElement
            case UnorderedListNode.nodeType => <.ul(textAlignAttr, children).rawElement
            case ListItemNode.nodeType => <.li(textAlignAttr, children).rawElement
            case _ => <.div(textAlignAttr, children).rawElement
          }
      }
    }
    // scalastyle:on cyclomatic.complexity

    private def renderMark(props: RenderMarkProps) = {
      val children = PropsChildren.fromRawProps(props)
      props.mark.markType match {
        case BoldNode.nodeType => <.strong(children).rawElement
        case ItalicNode.nodeType => <.em(children).rawElement
        case UnderlineNode.nodeType => <.u(children).rawElement
        case StrikeThroughNode.nodeType => <.del(children).rawElement
      }
    }

    def render(props: RichEditor): VdomElement = {
      <.div(^.cls := "editor",
        Editor(
          placeholder = props.placeholder,
          value = props.value,
          readOnly = props.readOnly,
          onChange = props.onChange,
          onKeyDown = onKeyDown,
          renderNode = renderNode,
          renderMark = renderMark
        )()
      )
    }
  }

  private val component = ScalaComponent.builder[RichEditor](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}
