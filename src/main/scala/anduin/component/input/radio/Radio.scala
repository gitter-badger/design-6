// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.radio

import anduin.component.icon.Icon
import anduin.component.icon.Icon.Glyph.{Blank, Circle}
import anduin.component.input.labelwrapper.LabelWrapper
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Radio(
  name: String,
  value: String,
  isChecked: Boolean,
  onChange: String => Callback,
  isDisabled: Boolean = false
) {
  def apply(children: VdomNode*): VdomElement =
    Radio.component(this)(children: _*)
}

object Radio {

  private type Props = Radio

  private def inputOnChange(props: Props)(e: ReactEventFromInput) =
    props.onChange(e.target.value)

  private val boxStaticStyles = TagMod(
    Style.display.block.width.px16.height.px16.borderRadius.pill.border.all,
    Style.focus.outlineLight.transition.allWithOutline
  )

  private def boxGetStyles(props: Props): TagMod = {
    val specific: TagMod = if (props.isDisabled) {
      Style.background.gray1.borderColor.gray3
    } else if (props.isChecked) {
      Style.background.blue4.borderColor.blue5
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
      ^.tpe := "radio",
      ^.name := props.name,
      ^.value := props.value,
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
      Icon(if (props.isChecked) Circle else Blank, Icon.Size.Custom(12))()
    )
  }

  private def render(props: Props, children: PropsChildren): VdomElement = {
    LabelWrapper(
      inputChildren = List((16, renderBox(props)), (12, renderGlyph(props))),
      isDisabled = props.isDisabled,
      hasPointer = !props.isChecked
    )(children)
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
