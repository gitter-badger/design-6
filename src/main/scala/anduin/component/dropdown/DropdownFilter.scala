// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.dropdown

import anduin.component.input.textbox.{TextBox, TextBoxStyle}
import anduin.scalajs.util.ScalaJSUtils
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[dropdown] class DropdownFilter[A] {

  def apply(): OuterProps.type = OuterProps

  case class OuterProps(props: Props) {
    def apply(): VdomElement = component(this)
  }

  private type Props = Dropdown[A]#InnerProps

  private def render(outerProps: OuterProps): Option[VdomElement] = {
    val props = outerProps.props
    props.outer.options
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
    .builder[OuterProps](this.getClass.getSimpleName)
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

  def byValue[A](props: Dropdown[A]#InnerProps)(option: Dropdown.Opt[A]): Boolean = {
    val filter = props.outer.getFilterValue.getOrElse(props.outer.getValueString)
    val value = filter(option.value).toLowerCase
    value.contains(props.downshift.inputValue.toLowerCase)
  }

}
