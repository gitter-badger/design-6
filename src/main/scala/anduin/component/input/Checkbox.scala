// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.input

import anduin.style.Style
import anduin.component.button.ButtonStyle

import japgolly.scalajs.react.vdom.svg_<^

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Checkbox(
  isChecked: Boolean,
  isDisabled: Boolean = false,
  onChange: Callback = Callback.empty
) {
  def apply(children: VdomNode*): VdomElement = {
    Checkbox.component(this)(children: _*)
  }
}

object Checkbox {

  private val size = "20"
  private val sizePx = s"${size}px"

  private val tick = {
    svg_<^.<.svg(
      Style.display.block,
      svg_<^.^.width := size,
      svg_<^.^.height := size,
      svg_<^.^.xmlns := "http://www.w3.org/2000/svg",
      svg_<^.^.fill := "currentColor",
      // scalastyle:off line.size.limit
      svg_<^.<.path(
        svg_<^.^.d := "M8.31 13.93l-3.16-2.98a.5.5 0 0 1 0-.71l1.07-1.08c.2-.2.5-.2.7-.01l1.57 1.39 4.59-4.4a.5.5 0 0 1 .7 0l1.07 1.08c.2.2.2.51 0 .7l-6.18 6c-.1.1-.26.1-.36 0z"
      )
      // scalastyle:on line.size.limit
    )
  }

  private class Backend() {
    def render(props: Checkbox, children: PropsChildren): VdomElement = {
      <.label(
        Style.flexbox.flex.flexbox.itemsCenter.cursor.pointer.position.relative,
        renderInput(props),
        renderTick(props),
        renderLabel(props, children)
      )
    }

    private def renderLabel(props: Checkbox, children: PropsChildren) = {
      <.span(
        Style.margin.left8,
        TagMod.when(props.isDisabled) { Style.color.gray6 },
        children
      )
    }

    private def renderTick(props: Checkbox) = TagMod.when(props.isChecked) {
      <.span(
        Style.position.absolute.coordinate.top0.coordinate.left0,
        if (props.isDisabled) Style.color.gray6 else Style.color.white,
        tick
      )
    }

    private def renderInput(props: Checkbox) = {
      <.input(
        // behaviours
        ^.tpe := "checkbox",
        ^.disabled := props.isDisabled,
        ^.checked := props.isChecked,
        ^.onChange --> props.onChange,
        // styles
        ^.width := sizePx,
        ^.height := sizePx,
        if (props.isChecked) ButtonStyle.ColorPrimary.full else ButtonStyle.ColorWhite.full,
        Style.focus.outline.transition.allWithOutline.borderRadius.px2.cursor.pointer,
        Style.disabled.backgroundGray2.disabled.borderGray4.disabled.shadowNone
      )
    }
  }

  private val component = ScalaComponent
    .builder[Checkbox](this.getClass.getSimpleName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}
