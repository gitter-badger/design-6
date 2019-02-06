// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.checkbox

import anduin.component.icon.Icon
import anduin.component.icon.Icon.Glyph.{Blank, CheckBold, MinusBold}
import anduin.component.input.labelwrapper.LabelWrapper
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Checkbox(
  isChecked: Boolean,
  onChange: Boolean => Callback = _ => Callback.empty,
  isDisabled: Boolean = false,
  isIndeterminate: Boolean = false
) {
  def apply(children: VdomNode*): VdomElement = {
    Checkbox.component(this)(children: _*)
  }
}

object Checkbox {

  private type Props = Checkbox

  private def inputOnChange(props: Props)(e: ReactEventFromInput) =
    props.onChange(e.target.checked)

  private val boxStaticStyles = TagMod(
    Style.display.block.width.px16.height.px16.borderRadius.px2.border.all,
    Style.focus.outlineLight.transition.allWithOutline
  )

  private def boxGetStyles(props: Props): TagMod = {
    val specific: TagMod = if (props.isDisabled) {
      Style.background.gray1.borderColor.gray3
    } else if (props.isChecked || props.isIndeterminate) {
      TagMod(
        Style.background.blue4.background.hoverBlue3.background.activeBlue5,
        Style.borderColor.blue5.shadow.px1Dark
      )
    } else {
      TagMod(
        Style.background.gray1.background.hoverWhite.background.activeGray2,
        Style.borderColor.gray4.shadow.px1Light
      )
    }
    TagMod(boxStaticStyles, specific)
  }

  // This is the actual <input>
  private def renderBox(props: Props): VdomElement = {
    <.input(
      boxGetStyles(props),
      ^.tpe := "checkbox",
      ^.checked := props.isChecked,
      ^.onChange ==> inputOnChange(props),
      ^.disabled := props.isDisabled
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

  private def render(props: Props, children: PropsChildren): VdomElement = {
    LabelWrapper(
      inputChildren = List((16, renderBox(props)), (16, renderGlyph(props))),
      isDisabled = props.isDisabled,
      hasPointer = true
    )(children)
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
