// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.checkbox

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

  private def renderText(props: Checkbox, children: PropsChildren): Option[VdomElement] = {
    if (children.isEmpty) {
      None
    } else {
      val color = TagMod.when(props.isDisabled)(Style.color.gray5)
      val label = <.span(Style.flexbox.fill.margin.left8, color, children)
      Some(label)
    }
  }

  private def renderInput(props: Props): VdomElement = {
    val input = CheckboxInput(
      isChecked = props.isChecked,
      onChange = props.onChange,
      isDisabled = props.isDisabled,
      isIndeterminate = props.isIndeterminate
    )()
    <.div(Style.flexbox.none, input)
  }

  private def render(props: Props, children: PropsChildren): VdomElement = {
    <.label(
      Style.flexbox.flex.flexbox.itemsStart,
      Style.position.relative.width.maxContent.maxWidth.pc100,
      if (props.isDisabled) Style.pointerEvents.none else Style.cursor.pointer,
      renderInput(props),
      renderText(props, children)
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
