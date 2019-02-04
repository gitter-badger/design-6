// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.checkbox

import anduin.component.icon.Icon
import anduin.component.icon.Icon.Glyph.{MinusBold, CheckBold, Blank}
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class CheckboxInput(
  isChecked: Boolean,
  onChange: Boolean => Callback,
  isDisabled: Boolean,
  isIndeterminate: Boolean
) {
  def apply(): VdomElement = CheckboxInput.component(this)
}

object CheckboxInput {

  private type Props = CheckboxInput

  private val inputStaticStyles = TagMod(
    Style.display.block.width.px16.height.px16.borderRadius.px2.border.all,
    Style.focus.outline.transition.allWithOutline
  )

  private def inputOnChange(props: Props)(e: ReactEventFromInput) =
    props.onChange(e.target.checked)

  private def inputGetStyles(props: Props): TagMod = {
    val specific: TagMod = if (props.isDisabled) {
      Style.background.gray1.borderColor.gray3
    } else if (props.isChecked || props.isIndeterminate) {
      TagMod(
        Style.background.blue4.background.hoverBlue3.background.activeBlue5,
        Style.borderColor.blue5.shadow.blur1Dark
      )
    } else {
      TagMod(
        Style.background.gray1.background.hoverWhite.background.activeGray2,
        Style.borderColor.gray4.shadow.blur1Light
      )
    }
    TagMod(inputStaticStyles, specific)
  }

  // This is the actual <input>
  private def renderInput(props: Props): VdomElement = {
    <.input(
      inputGetStyles(props),
      ^.tpe := "checkbox",
      ^.disabled := props.isDisabled,
      ^.checked := props.isChecked,
      ^.onChange ==> inputOnChange(props)
    )
  }

  private def renderGlyph(props: Props): VdomElement = {
    <.div(
      // Should not consume events
      Style.pointerEvents.none,
      if (props.isDisabled) Style.color.gray4 else Style.color.white,
      Icon(if (props.isIndeterminate) MinusBold else if (props.isChecked) CheckBold else Blank)()
    )
  }

  private val childStyles = TagMod(
    Style.display.block.margin.verAuto.height.px16,
    Style.position.absolute.coordinate.fill
  )

  def render(props: Props): VdomElement = {
    <.span(
      // The size of the box is 16x16 but we want it to align nicely with
      // the label, which line-height is 20px and might have several lines
      // - We also need to define the width here since its children are all
      //   absolutely positioned
      Style.display.block.width.px16.height.px20.position.relative,
      <.span(childStyles, renderInput(props)),
      <.span(childStyles, renderGlyph(props))
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
