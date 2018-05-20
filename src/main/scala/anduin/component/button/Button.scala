// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.button

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Button(
  // Common styling via ButtonStyle
  color: ButtonStyle.Color = ButtonStyle.ColorWhite,
  size: ButtonStyle.Size = ButtonStyle.SizeMedium,
  style: ButtonStyle.Style = ButtonStyle.StyleFull,
  isFullWidth: Boolean = false,
  isSelected: Boolean = false,
  // Specific behaviours for Button
  tpe: Button.Tpe = Button.TpeButton,
  isDisabled: Boolean = false,
  onClick: Callback = Callback.empty
) {
  def apply(children: VdomNode*): VdomElement = {
    Button.component(this)(children: _*)
  }
}

object Button {

  type Props = Button

  sealed trait Tpe { val value: String }
  case object TpeButton extends Tpe { val value = "button" }
  case object TpeSubmit extends Tpe { val value = "submit" }
  case object TpeReset extends Tpe { val value = "reset" }

  private class Backend(scope: BackendScope[Props, _]) {

    private def onClick(e: ReactEventFromHtml) = {
      for {
        _ <- e.preventDefaultCB
        _ <- e.stopPropagationCB
        props <- scope.props
        _ <- props.onClick
      } yield ()
    }

    def render(props: Props, children: PropsChildren): VdomElement = <.button(
      // styles
      ButtonStyle.getStyles(
        color = props.color,
        size = props.size,
        style = props.style,
        isSelected = props.isSelected,
        isFullWidth = props.isFullWidth
      ),
      // behaviours
      ^.tpe := props.tpe.value,
      ^.disabled := props.isDisabled,
      TagMod.when(!props.isDisabled && !props.onClick.isEmpty_?) { ^.onClick ==> onClick },
      // content
      children
    )
  }

  private val component = ScalaComponent
    .builder[Button](this.getClass.getSimpleName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}
