// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.dropdown

import anduin.component.input.textbox.{TextBox, TextBoxStyle}
import anduin.scalajs.downshift.DownshiftRenderProps
import anduin.scalajs.util.ScalaJSUtils
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[dropdown] class DropdownFilter[A] {

  def apply(): Props.type = Props

  case class Props(
    dropdown: Dropdown[A]#Props,
    downshift: DownshiftRenderProps[A]
  ) {
    def apply(): VdomElement = component(this)
  }

  private def render(props: Props): Option[VdomElement] = {
    props.dropdown.options
      .lift(10)
      .map(_ => {
        val input = <.input(
          DropdownFilter.staticMods,
          ScalaJSUtils.jsPropsToTagMod(props.downshift.getInputProps())
        )
        <.div(Style.padding.hor8.margin.bottom8, input)
      })
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}

private[dropdown] object DropdownFilter {

  // static mods
  private val staticMods = TagMod(
    TextBoxStyle.getStyles(
      style = TextBox.Style.Full,
      customColor = None,
      customBg = None,
      customBorderColor = None,
      customSize = None
    ),
    ^.autoFocus := true,
    ^.placeholder := "Search…"
  )

  def byValue[A](
    dropdown: Dropdown[A]#Props,
    downshift: DownshiftRenderProps[A]
  )(option: Dropdown.Opt[A]): Boolean = {
    val filter = dropdown.getFilterValue.getOrElse(dropdown.getValueString)
    val value = filter(option.value).toLowerCase
    value.contains(downshift.inputValue.toLowerCase)
  }

}
