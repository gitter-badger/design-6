// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.input

import anduin.component.button.ButtonStyle
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Radio[V](
  name: String,
  value: V,
  onChange: V => Callback,
  isDisabled: Boolean = false,
  isChecked: Boolean
) {
  def apply(children: VdomNode*): VdomElement = component(this)(children: _*)

  private val component = ScalaComponent
    .builder[Radio[V]](this.getClass.getSimpleName)
    .stateless
    .renderBackendWithChildren[Radio.Backend[V]]
    .build
}

object Radio {

  private val size = TagMod(^.minWidth := "20px", ^.height := "20px")

  private case class Backend[V](scope: BackendScope[Radio[V], _]) {

    def render(props: Radio[V], children: PropsChildren): VdomElement = {
      <.label(
        Style.flexbox.flex.flexbox.itemsCenter.cursor.pointer.position.relative,
        renderInput(props),
        renderDot(props),
        renderLabel(props, children)
      )
    }

    private def renderLabel(props: Radio[V], children: PropsChildren) = {
      <.span(
        Style.margin.left8,
        TagMod.when(props.isDisabled) { Style.color.gray6 },
        children
      )
    }

    def renderInput(props: Radio[V]): VdomElement = {
      <.input(
        // behaviours
        ^.tpe := "radio",
        ^.name := props.name,
        ^.disabled := props.isDisabled,
        ^.checked := props.isChecked,
        ^.onChange --> props.onChange(props.value),
        // styles
        if (props.isChecked) ButtonStyle.ColorPrimary.full else ButtonStyle.ColorWhite.full,
        Style.focus.outline.transition.allWithOutline.borderRadius.pc100.border.all,
        Style.disabled.backgroundGray2.disabled.borderGray4.disabled.shadowNone,
        size
      )
    }

    private def renderDot(props: Radio[V]) = TagMod.when(props.isChecked) {
      // we intentionally use a 20 x 20px wrapper here to support dynamic alignment of the dot
      <.span(
        size,
        Style.position.absolute.coordinate.left0.coordinate.top0,
        Style.flexbox.flex.flexbox.justifyCenter.flexbox.itemsCenter,
        <.span(
          TagMod(^.width := "8px", ^.height := "8px"),
          if (props.isDisabled) Style.backgroundColor.gray6 else Style.backgroundColor.white,
          Style.borderRadius.pc100
        )
      )
    }
  }

}
