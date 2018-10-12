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
    // This can be used to render ghost Option,
    // in which downshift is not available
    downshift: Option[DownshiftRenderProps[A]],
    option: Dropdown.Opt[A],
    index: Int
  ) {
    def apply(): VdomElement = component(this)
  }

  private def renderIcon(props: Props): VdomElement = {
    val value = props.option.value
    val isSelected = props.downshift.exists(_.selectedItem.contains(value))
    val iconName = if (isSelected) Icon.NameCheck else Icon.NameBlank
    <.span(
      TagMod(Style.margin.right8, ^.marginLeft := "-4px"),
      Icon(iconName, Icon.SizeDynamic("12"))()
    )
  }

  private def getMods(props: Props): TagMod = {
    val options = new GetItemPropsOptions[A](
      props.index,
      props.option.value,
      Some(props.option.isDisabled).orUndefined
    )
    props.downshift
      .map(_.getItemProps(options))
      .map(jsProps => Util.getModsFromProps(jsProps))
      .getOrElse(TagMod.empty)
  }

  private def getStyles(props: Props): TagMod = TagMod(
    // isHighlighted
    TagMod.when(props.downshift.exists {
      _.highlightedIndex.contains(props.index)
    })(Style.backgroundColor.gray2),
    // isDisabled
    TagMod.when(props.option.isDisabled)(Style.color.gray6)
  )

  private def renderBody(props: Props) = {
    val op = props.outer
    op.renderOption.getOrElse(op.renderValue)(props.option.value)
  }

  private def render(props: Props) = {
    <.button(
      MenuItem.buttonStyles,
      getStyles(props),
      getMods(props),
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
