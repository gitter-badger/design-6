// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.input.checkbox

import anduin.component.button.Button
import anduin.component.icon.Icon
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Checkbox(
  isChecked: Boolean,
  isDisabled: Boolean = false,
  onChange: Boolean => Callback = _ => Callback.empty
) {
  def apply(children: VdomNode*): VdomElement = {
    Checkbox.component(this)(children: _*)
  }
}

object Checkbox {

  private type Props = Checkbox

  private val size = "20"
  private val sizePx = s"${size}px"

  private def renderLabel(props: Checkbox, children: PropsChildren) = TagMod.when(children.nonEmpty) {
    <.span(
      Style.flexbox.fixed,
      Style.margin.left8,
      TagMod.when(props.isDisabled) { Style.color.gray6 },
      children
    )
  }

  private def renderTick(props: Checkbox) = TagMod.when(props.isChecked) {
    <.span(
      Style.position.absolute.flexbox.flex.flexbox.justifyCenter.flexbox.itemsCenter,
      // Should not consume events
      Style.pointerEvents.none,
      TagMod(^.width := sizePx, ^.height := sizePx),
      if (props.isDisabled) Style.color.gray6 else Style.color.white,
      Icon(name = Icon.NameCheckBold)()
    )
  }

  private val inputStyles = TagMod(
    TagMod(^.width := sizePx, ^.height := sizePx),
    Style.flexbox.none,
    Style.focus.outline.transition.allWithOutline,
    Style.borderRadius.px2.border.all.cursor.pointer,
    Style.disabled.backgroundGray2.disabled.borderGray4.disabled.shadowNone
  )

  private def renderInput(props: Checkbox) = {
    <.input(
      // behaviours
      ^.tpe := "checkbox",
      ^.disabled := props.isDisabled,
      ^.checked := props.isChecked,
      ^.onChange ==> { e: ReactEventFromInput =>
        props.onChange(e.target.checked)
      },
      // styles
      inputStyles,
      if (props.isChecked) {
        Button.Style.Full(color = Button.Color.Blue).colorNormal
      } else {
        Button.Style.Full(color = Button.Color.White).colorNormal
      }
    )
  }

  private def render(props: Props, children: PropsChildren): VdomElement = {
    <.label(
      Style.flexbox.flex.flexbox.itemsStart,
      Style.width.maxContent.maxWidth.pc100,
      Style.cursor.pointer.position.relative,
      renderInput(props),
      renderTick(props),
      renderLabel(props, children)
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
