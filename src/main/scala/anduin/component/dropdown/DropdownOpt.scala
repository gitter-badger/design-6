// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.dropdown

import anduin.component.icon.Icon
import anduin.component.menu.MenuItem
import anduin.scalajs.downshift.{DownshiftRenderProps, GetItemPropsOptions}
import anduin.scalajs.util.Util
import anduin.style.Style

// scalastyle:off underscore.import
import scala.scalajs.js.JSConverters._

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[dropdown] class DropdownOption[A] {

  def apply(): Props.type = Props

  case class Props(
    outer: Dropdown[A]#Props,
    downshift: DownshiftRenderProps[A],
    option: Dropdown.Opt[A],
    index: Int
  ) {
    def apply(): VdomElement = component(this)
  }

  private def render(props: Props) = {
    val value = props.option.value
    val isDisabled = Some(props.option.isDisabled).orUndefined
    val options = new GetItemPropsOptions[A](props.index, value, isDisabled)
    val itemProps = props.downshift.getItemProps(options)
    val renderOptionProps = DropdownOption.RenderProps(
      option = props.option,
      mods = Util.getModsFromProps(itemProps),
      renderValue = props.outer.renderValue,
      isHighlighted = props.downshift.highlightedIndex.contains(props.index),
      isSelected = props.downshift.selectedItem.contains(value)
    )
    props.outer.renderOption(renderOptionProps)
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}

private[dropdown] object DropdownOption {

  final case class RenderProps[A](
    option: Dropdown.Opt[A],
    mods: TagMod,
    renderValue: A => VdomNode,
    isSelected: Boolean = false,
    isHighlighted: Boolean = false
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

  // This render a option without any functionality. This should not be used
  // to render actual options nodes that user will interact with. Instead, it
  // should be used for behind-the-scene measure and layout purpose
  def renderPlain[A](props: Dropdown[A]#Props)(option: Dropdown.Opt[A]): VdomNode = {
    val renderOptionProps = DropdownOption.RenderProps(option, TagMod.empty, props.renderValue)
    props.renderOption(renderOptionProps)
  }
}
