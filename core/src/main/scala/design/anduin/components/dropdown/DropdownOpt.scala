// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.components.dropdown

import design.anduin.components.icon.Icon
import design.anduin.components.menu.MenuItem
import design.anduin.facades.downshift.{DownshiftItemOptions, DownshiftRenderProps}
import design.anduin.facades.util.ScalaJSUtils
import design.anduin.style.Style

import scala.scalajs.js

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[dropdown] class DropdownOpt[A] {

  def apply(): Props.type = Props

  case class Props(
    // == Specific props for this option
    option: Dropdown.Opt[A],
    index: Int,
    // == Common props for all sibling options
    dropdown: Dropdown[A]#Props,
    // This can be used to render ghost Option, in which case "downshift" is
    // not available
    downshift: Option[DownshiftRenderProps[A]] = None
  ) {
    def apply(): VdomElement = component(this)
  }

  private def renderIcon(props: Props): VdomElement = {
    val value = props.option.value
    val isSelected = props.downshift.exists { downshift =>
      ScalaJSUtils.jsNullToOption(downshift.selectedItem).contains(value)
    }
    val iconName = if (isSelected) Icon.Glyph.Check else Icon.Glyph.Blank
    <.span(
      TagMod(Style.margin.right8, ^.marginLeft := "-4px"),
      Icon(iconName, Icon.Size.Custom(12))()
    )
  }

  private def getMods(props: Props): TagMod = {
    val options = new DownshiftItemOptions[A](
      index = props.index,
      item = props.option.value,
      disabled = js.defined(props.option.isDisabled)
    )
    props.downshift
      .map(_.getItemProps(options))
      .map(ScalaJSUtils.jsPropsToTagMod(_))
      .getOrElse(TagMod.empty)
  }

  private def getStyles(props: Props): TagMod = TagMod(
    // isHighlighted
    TagMod.when(props.downshift.exists { downshift =>
      ScalaJSUtils.jsNullToOption(downshift.highlightedIndex).contains(props.index)
    })(Style.background.gray2),
    // isDisabled
    TagMod.when(props.option.isDisabled)(Style.color.gray6)
  )

  private def renderBody(props: Props): VdomNode = {
    val (op, v) = (props.dropdown, props.option.value)
    op.renderOption.map(_(v)).getOrElse(op.getValueString(v))
  }

  private def render(props: Props) = {
    <.button(
      MenuItem.buttonStyles,
      getStyles(props),
      getMods(props)
    )(
      renderIcon(props),
      renderBody(props),
      <.span(Style.width.px16) // visual balance
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
