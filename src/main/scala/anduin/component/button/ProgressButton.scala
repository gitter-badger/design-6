// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.button

import anduin.component.loader.InlineLoader
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class ProgressButton(
  status: ProgressButton.Status = ProgressButton.Status.Default,
  labels: ProgressButton.Status => String,
  // passing through
  onClick: Callback = Callback.empty,
  color: ButtonStyle.Color = ButtonStyle.ColorWhite,
  tpe: Button.Tpe = Button.TpeButton,
  isDisabled: Boolean = false,
  isFullWidth: Boolean = false
) {
  def apply(): VdomElement = {
    ProgressButton.component(this)
  }
}
// scalastyle:on multiple.string.literals

object ProgressButton {

  private val ComponentName = this.getClass.getSimpleName

  sealed trait Status
  object Status {
    case object Default extends Status
    case object Loading extends Status
    case object Disabled extends Status
    case object Success extends Status
    case object Error extends Status
  }

  private case class Backend(scope: BackendScope[ProgressButton, _]) {
    def render(props: ProgressButton): VdomElement = {
      Button(
        tpe = props.tpe,
        color = props.color,
        onClick = props.onClick,
        isDisabled = props.status == Status.Disabled || props.status == Status.Loading || props.isDisabled,
        isFullWidth = props.isFullWidth
      )(
        <.div(
          Style.margin.right8,
          // hack to align
          // @TODO: use IconAcl
          ^.lineHeight := "1",
          TagMod.when(props.status != Status.Loading) { Style.display.none },
          InlineLoader()
        ),
        props.labels(props.status)
      )
    }
  }

  private val component = ScalaComponent
    .builder[ProgressButton](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}
