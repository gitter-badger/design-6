// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.dropdown

import anduin.component.input.textbox.{TextBox, TextBoxStyle}
import anduin.scalajs.util.Util
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[dropdown] class DropdownFilter[A] {
  private type Props = DropdownInnerProps[A]

  private def render(props: Props): Option[VdomElement] = {
    props.outer.options.lift(10).map(_ => {
      val input = <.input(
        DropdownFilter.staticMods,
        Util.getModsFromProps(props.downshift.getInputProps())
      )
      <.div(Style.padding.hor8.margin.bottom8, input)
    })
  }

  val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}

private[dropdown] object DropdownFilter {

  // static mods
  private val staticMods = TagMod(
    TextBoxStyle.getInput(TextBox(value = "")),
    ^.autoFocus := true,
    ^.placeholder := "Search…"
  )

  def byValue[A](props: DropdownInnerProps[A])(option: Dropdown.Opt[A]): Boolean = {
    val inputOpt = props.downshift.inputValue.toOption.filter(_ != null)
    inputOpt match {
      case None | Some("") => true
      case Some(input) =>
        val value = props.outer.getFilterValue(option.value).toLowerCase
        value.contains(input.toLowerCase)
    }
  }

}
