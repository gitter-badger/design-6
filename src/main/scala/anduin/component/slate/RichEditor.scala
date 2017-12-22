// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.slate

import org.scalajs.dom.KeyboardEvent

import anduin.component.slate.Editor.RenderMarkProps
import anduin.scalajs.slate.Slate.{Change, Value}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class RichEditor(
  value: Value,
  onChange: Change => Callback
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
            change.addMark("bold")
          }
          case _ => Callback.empty
        }
      }
    }

    // scalastyle:off null
    private def renderMark(props: RenderMarkProps) = {
      props.mark.markType match {
        case "bold" => <.strong(PropsChildren.fromRawProps(props)).rawElement
        case _ => null
      }
    }
    // scalastyle:on null

    def render(props: RichEditor): VdomElement = {
      <.div(
        Editor(
          value = props.value,
          onChange = props.onChange,
          onKeyDown = onKeyDown,
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
