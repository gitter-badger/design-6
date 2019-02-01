// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.radio

import anduin.component.button.Button
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Radio(
  name: String,
  value: String,
  onChange: String => Callback,
  isDisabled: Boolean = false,
  isChecked: Boolean
) {
  def apply(children: VdomNode*): VdomElement =
    Radio.component(this)(children: _*)
}

object Radio {

  private type Props = Radio

  private val size = TagMod(^.minWidth := "20px", ^.height := "20px")

  private def renderLabel(props: Radio, children: PropsChildren) = {
    <.span(
      Style.margin.left8,
      TagMod.when(props.isDisabled) { Style.color.gray6 },
      children
    )
  }

  private val inputStyles = TagMod(
    Style.focus.outline.transition.allWithOutline,
    Style.borderRadius.pc100.border.all,
    Style.disabled.backgroundGray2.disabled.borderGray4.disabled.shadowNone,
    size
  )

  private def renderInput(props: Radio): VdomElement = {
    <.input(
      // behaviours
      ^.tpe := "radio",
      ^.name := props.name,
      ^.disabled := props.isDisabled,
      ^.checked := props.isChecked,
      ^.onChange --> props.onChange(props.value),
      // styles
      if (props.isChecked) {
        Button.Style.Full(color = Button.Color.Blue).colorNormal
      } else {
        Button.Style.Full(color = Button.Color.White).colorNormal
      },
      inputStyles
    )
  }

  private def renderDot(props: Radio) = TagMod.when(props.isChecked) {
    // we intentionally use a 20 x 20px wrapper here to support dynamic
    // alignment of the dot
    <.span(
      size,
      Style.position.absolute.coordinate.left0.coordinate.top0,
      Style.flexbox.flex.flexbox.justifyCenter.flexbox.itemsCenter,
      <.span(
        TagMod(^.width := "8px", ^.height := "8px"),
        if (props.isDisabled) {
          Style.background.gray6
        } else {
          Style.background.white
        },
        Style.borderRadius.pc100
      )
    )
  }

  private def render(props: Radio, children: PropsChildren): VdomElement = {
    <.label(
      Style.flexbox.flex.flexbox.itemsCenter.cursor.pointer.position.relative,
      renderInput(props),
      renderDot(props),
      renderLabel(props, children)
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build

}
