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

    private def renderNode(props: RenderNodeProps) = {
      props.node.nodeType match {
        case BlockQuoteNode.nodeType => <.blockquote(PropsChildren.fromRawProps(props)).rawElement
        case ParagraphNode.nodeType => <.p(PropsChildren.fromRawProps(props)).rawElement
        case CodeNode.nodeType => <.pre(<.code(PropsChildren.fromRawProps(props))).rawElement
        case OrderedListNode.nodeType => <.ol(PropsChildren.fromRawProps(props)).rawElement
        case UnorderedListNode.nodeType => <.ul(PropsChildren.fromRawProps(props)).rawElement
        case ListItemNode.nodeType => <.li(PropsChildren.fromRawProps(props)).rawElement
        case LinkNode.nodeType =>
          val href = props.node.data.get("href").toOption.map(_.asInstanceOf[String]).getOrElse("")
          <.a(^.href := href, PropsChildren.fromRawProps(props)).rawElement
      }
    }

    private def renderMark(props: RenderMarkProps) = {
      props.mark.markType match {
        case BoldNode.nodeType => <.strong(PropsChildren.fromRawProps(props)).rawElement
        case ItalicNode.nodeType => <.em(PropsChildren.fromRawProps(props)).rawElement
        case UnderlineNode.nodeType => <.u(PropsChildren.fromRawProps(props)).rawElement
        case StrikeThroughNode.nodeType => <.del(PropsChildren.fromRawProps(props)).rawElement
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
