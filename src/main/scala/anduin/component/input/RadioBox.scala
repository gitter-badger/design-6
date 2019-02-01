// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input

import anduin.component.icon.Icon
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class RadioBox(
  onClick: Callback,
  isChecked: Boolean,
  isDisabled: Boolean = false
) {
  def apply(children: VdomNode*): VdomElement = {
    RadioBox.component(this)(children: _*)
  }
}

object RadioBox {

  type Props = RadioBox

  private val check: VdomElement = {
    <.div(
      Style.position.absolute.borderRadius.pc100.background.white.color.success4,
      Style.flexbox.flex.flexbox.itemsCenter.flexbox.justifyCenter,
      TagMod(^.width := "20px", ^.height := "20px", ^.top := "-10px", ^.right := "-10px"),
      Icon(name = Icon.Glyph.CheckCircle)()
    )
  }

  private val styles = TagMod(
    Style.display.block.textAlign.left.focus.outline.transition.allWithOutline,
    Style.border.all.borderWidth.px2.hover.borderGray3.active.borderGray5,
    Style.borderRadius.px2.padding.all16.position.relative,
    Style.width.pc100.height.pc100
  )

  private def render(props: Props, children: PropsChildren): VdomElement = {
    <.button(
      // style
      styles,
      if (props.isChecked) Style.borderColor.success4 else Style.borderColor.gray4,
      TagMod.when(props.isDisabled) { Style.opacity.pc50 },
      // behaviour
      ^.tpe := "button",
      ^.onClick --> props.onClick,
      ^.disabled := props.isChecked || props.isDisabled,
      // content
      children,
      TagMod.when(props.isChecked) { check }
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
