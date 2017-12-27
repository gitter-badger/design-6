// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.slate

import japgolly.scalajs.react.component.Scala.Unmounted
import org.scalajs.dom.KeyboardEvent

import anduin.component.slate.Editor.{RenderMarkProps, RenderNodeProps}
import anduin.scalajs.slate.Slate.{Change, Value}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class RichEditor(
  value: Value,
  onChange: Change => Callback,
  readOnly: Boolean
) {
  def apply(): ScalaComponent.Unmounted[_, _, _] = RichEditor.component(this)
}

object RichEditor {

  private final val ComponentName = this.getClass.getSimpleName

  // A helper function to create a read-only instance of editor from given HTML
  def readOnlyFromHtml(html: String, maxLengthOpt: Option[Int] = None): Unmounted[_, _, _] = {
    val body = maxLengthOpt.map(html.substring(0, _)).getOrElse(html)
    RichEditor(
      value = Serializer.deserialize(body),
      onChange = _ => Callback.empty,
      readOnly = true
    )()
  }

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

    // scalastyle:off null
    private def renderNode(props: RenderNodeProps) = {
      props.node.nodeType match {
        case OrderedListNode.nodeType => <.ol(PropsChildren.fromRawProps(props)).rawElement
        case UnorderedListNode.nodeType => <.ul(PropsChildren.fromRawProps(props)).rawElement
        case ListItemNode.nodeType => <.li(PropsChildren.fromRawProps(props)).rawElement
        case LinkNode.nodeType => {
          val href = props.node.data.get("href").toOption.map(_.asInstanceOf[String]).getOrElse("")
          <.a(^.href := href, PropsChildren.fromRawProps(props)).rawElement
        }
        case _ => null
      }
    }

    private def renderMark(props: RenderMarkProps) = {
      props.mark.markType match {
        case BoldNode.nodeType => <.strong(PropsChildren.fromRawProps(props)).rawElement
        case ItalicNode.nodeType => <.em(PropsChildren.fromRawProps(props)).rawElement
        case UnderlineNode.nodeType => <.u(PropsChildren.fromRawProps(props)).rawElement
        case _ => null
      }
    }
    // scalastyle:on null

    def render(props: RichEditor): VdomElement = {
      <.div(
        Editor(
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
