// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.dropdown

import anduin.component.input.textbox.{TextBox, TextBoxStyle}
import anduin.scalajs.util.Util
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[dropdown] class DropdownContent[A] {

  private val OptionRender = (new DropdownOption[A])()
  private val Filter = new DropdownFilter[A]

  private type Props = DropdownInnerProps[A]

  private def renderOption(props: Props)(tuple: (Dropdown.Opt[A], Int)) = {
    val (option, index) = tuple
    <.div(
      ^.key := props.outer.getFilterValue(option.value),
      Opt(props.outer, props.downshift, option, index)()
    )
  }

  private def renderOptions(props: Props): VdomElement = {
    val options = props.outer.options
      .filter(filterOption(props))
      .zipWithIndex
      .toVdomArray(renderOption(props))
    <.div(Style.overflow.autoY, ^.maxHeight := "40vh", options)
  }

  private def render(props: Props): VdomElement = {
    <.div(
      Util.getModsFromProps(props.downshift.getMenuProps()),
      props.outer.header,
      <.div(
        Style.padding.ver8,
        Filter.component(props),
        renderOptions(props)
      ),
      props.outer.footer
    )
  }

  val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}

