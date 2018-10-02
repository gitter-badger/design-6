// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.dropdown

import anduin.component.icon.Icon
import anduin.component.input.TextBox
import anduin.component.menu.MenuItem
import anduin.scalajs.downshift.{DownshiftRenderProps, GetItemPropsOptions}
import anduin.scalajs.util.Util
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import scala.scalajs.js.JSConverters._
// scalastyle:on underscore.import

private[dropdown] class DropdownContent[A] {

  def apply(): Props.type = Props

  case class Props(
    outer: Dropdown[A]#OuterProps,
    downshift: DownshiftRenderProps[A]
  ) {
    def apply(): VdomElement = component(this)
  }

  private def renderSearch(props: Props): Option[VdomElement] = {
    if (props.outer.options.length > 10) {
      val input = <.input(
        DropdownContent.Search.inputMods,
        Util.getModsFromProps(props.downshift.getInputProps())
      )
      val element = <.div(Style.padding.hor8.margin.bottom8, input)
      Some(element)
    } else {
      None
    }
  }

  private def renderOption(props: Props)(tuple: (Dropdown.Opt[A], Int)) = {
    val (option, index) = tuple

    val isDisabled = Some(option.isDisabled).orUndefined
    val options = new GetItemPropsOptions[A](index, option.value, isDisabled)
    val itemProps = props.downshift.getItemProps(options)
    val renderOptionProps = DropdownContent.Option.RenderProps(
      option = option,
      mods = TagMod(
        ^.key := props.outer.valueToString(option.value),
        Util.getModsFromProps(itemProps)
      ),
      renderValue = props.outer.renderValue,
      isHighlighted = props.downshift.highlightedIndex.contains(index),
      isSelected = props.downshift.selectedItem.contains(option.value)
    )

    props.outer.renderOption(renderOptionProps)
  }

  private def filterOption(props: Props)(option: Dropdown.Opt[A]) = {
    val inputOpt = props.downshift.inputValue.toOption.filter(_ != null)
    inputOpt match {
      case None | Some("") => true
      case Some(input) =>
        val value = props.outer.valueToString(option.value).toLowerCase
        value.contains(input.toLowerCase)
    }
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
        renderSearch(props),
        renderOptions(props)
      ),
      props.outer.footer
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}

private[dropdown] object DropdownContent {

  object Search {
    // static mods
    val inputMods = TagMod(
      TextBox.Styles.input,
      TextBox.Size32.font,
      TextBox.Size32.height,
      TextBox.StatusNone.styles,
      ^.autoFocus := true,
      ^.placeholder := "Search…"
    )
  }

  object Option {

    final case class RenderProps[A](
      option: Dropdown.Opt[A],
      mods: TagMod,
      renderValue: A => VdomNode,
      isSelected: Boolean,
      isHighlighted: Boolean
    )
    type Render[A] = RenderProps[A] => VdomNode

    // Could be mouse hover or via keyboard navigation
    private val highlightedStyles = Style.backgroundColor.gray2
    private val disabledStyles = Style.color.gray6
    private val void = <.span(Style.width.px16) // visual balance
    private val iconStyles = TagMod(Style.margin.right8, ^.marginLeft := "-4px")

    def defaultRender[A](props: RenderProps[A]): VdomElement = {
      val value = props.renderValue(props.option.value)
      val styles: TagMod = TagMod(
        TagMod.when(props.option.isDisabled)(disabledStyles),
        TagMod.when(props.isHighlighted)(highlightedStyles)
      )
      val iconName = if (props.isSelected) Icon.NameCheck else Icon.NameBlank
      val icon = <.span(iconStyles, Icon(iconName, Icon.SizeDynamic("12"))())
      <.button(MenuItem.buttonStyles, styles, props.mods)(icon, value, void)
    }
  }

}
