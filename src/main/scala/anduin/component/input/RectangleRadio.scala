// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input

import anduin.component.input.radio.Radio
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class RectangleRadio(
  name: String,
  value: String,
  onChange: String => Callback,
  isChecked: Boolean,
  isDisabled: Boolean = false,
  color: RectangleRadio.Color = RectangleRadio.ColorPrimary
) {
  def apply(children: VdomNode*): VdomElement = RectangleRadio.component(this)(children: _*)
}

object RectangleRadio {

  private type Props = RectangleRadio

  sealed trait Color {
    val default: Style
    val selected: Style
  }
  case object ColorGray extends Color {
    val default: Style = Style.backgroundColor.gray3.borderColor.gray4.hover.borderGray5
    val selected: Style = Style.borderColor.gray6
  }
  case object ColorPrimary extends Color {
    val default: Style = Style.backgroundColor.primary1.borderColor.primary2.hover.borderPrimary3
    val selected: Style = Style.borderColor.primary4
  }
  case object ColorPrimaryWhite extends Color {
    val default: Style = Style.backgroundColor.white.borderColor.gray4.hover.borderGray5
    val selected: Style = Style.borderColor.primary3
  }
  case class StyleCustom(
    default: Style = new Style(List.empty),
    selected: Style = new Style(List.empty)
  ) extends Color

  def render(props: Props, children: PropsChildren): VdomElement = {
    <.div(
      Style.padding.hor12.padding.ver8.border.all.cursor.pointer,
      if (props.isChecked) props.color.selected else props.color.default,
      TagMod.when(!props.isDisabled)(^.onClick --> props.onChange(props.value)),
      Radio(
        name = props.name,
        value = props.value,
        isChecked = props.isChecked,
        onChange = props.onChange,
        isDisabled = props.isDisabled
      )(children)
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build

}
