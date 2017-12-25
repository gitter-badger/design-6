// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.slate

import anduin.component.icon.Iconv2
import anduin.scalajs.slate.Slate.{Change, Value}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[slate] final case class MarkButtonBar(
  value: Value,
  onChange: Change => Callback
) {
  def apply(): ScalaComponent.Unmounted[_, _, _] = MarkButtonBar.component(this)
}

private[slate] object MarkButtonBar {

  private val ComponentName = this.getClass.getSimpleName

  private case class Backend(scope: BackendScope[MarkButtonBar, _]) {

    def render(props: MarkButtonBar): VdomElement = {
      <.div(
        List(
          (BoldAction, Iconv2.bold(), "Bold"),
          (ItalicAction, Iconv2.italic(), "Italic"),
          (UnderlineAction, Iconv2.underline(), "Underline")
        ).toVdomArray { case (markAction, icon, tip) =>
          MarkButton(
            markAction = markAction,
            tip = tip,
            active = props.value.activeMarks.some(item => item.markType == markAction.markType),
            onToggle = markAction => {
              val change = props.value.change().toggleMark(markAction.markType)
              props.onChange(change)
            }
          )(icon)
        }
      )
    }
  }

  private val component = ScalaComponent.builder[MarkButtonBar](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}
