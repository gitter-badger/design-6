// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.button

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Button(
  // Common styling via ButtonStyle
  color: ButtonStyle.Color = ButtonStyle.ColorWhite,
  size: ButtonStyle.Size = ButtonStyle.Size32,
  style: ButtonStyle.Style = ButtonStyle.StyleFull,
  isFullWidth: Boolean = false,
  isSelected: Boolean = false,
  // Specific behaviours for Button
  tpe: Button.Tpe = Button.TpeButton,
  autoFocus: Boolean = false,
  isDisabled: Boolean = false,
  onClick: Callback = Callback.empty
) {
  def apply(children: VdomNode*): VdomElement = {
    Button.component(this)(children: _*)
  }
}

object Button {

  private type Props = Button

  sealed trait Tpe
  case object TpeButton extends Tpe
  case object TpeSubmit extends Tpe
  case object TpeReset extends Tpe

  private def getTpeValue(tpe: Tpe): String = tpe match {
    case TpeButton => "button"
    case TpeSubmit => "submit"
    case TpeReset  => "reset"
  }

  private def render(props: Props, children: PropsChildren): VdomElement = {
    <.button(
      // styles
      ButtonStyle.getStyles(
        color = props.color,
        size = props.size,
        style = props.style,
        isSelected = props.isSelected,
        isFullWidth = props.isFullWidth
      ),
      // behaviours
      ^.tpe := getTpeValue(props.tpe),
      ^.disabled := props.isDisabled,
      ^.autoFocus := props.autoFocus,
      TagMod.when(!props.isDisabled) { ^.onClick --> props.onClick },
      // content
      children
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
