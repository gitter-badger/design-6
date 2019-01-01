// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.textbox

import anduin.component.icon.Icon
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

// Internal styling logic of TextBox
private[component] object TextBoxStyle {

  private type Props = TextBox

  // Border

  private val borderStatic = Style.border.all.borderWidth.px1.borderRadius.px2

  // Common styles for both input and context

  private def getCommon(props: Props): TagMod = TagMod(
    borderStatic,
    Style.padding.hor12.padding.ver8,
    props.tpe.getSize(props.size)
  )

  // Input

  private val inputStatic = TagMod(
    borderStatic,
    Style.display.block.width.pc100.padding.hor12.padding.ver8,
    Style.focus.spread.focus.border.transition.allWithShadow
  )

  private def getColor(props: Props): TagMod =
    if (props.isDisabled) Style.color.gray6 else Style.color.gray8

  private def getBackground(props: Props): TagMod = {
    if (props.isReadOnly) {
      Style.backgroundColor.gray1
    } else if (props.isDisabled) {
      Style.backgroundColor.gray2
    } else {
      Style.backgroundColor.white
    }
  }

  private def getBorderColor(props: Props): TagMod = props.status match {
    case TextBox.StatusInvalid => Style.borderColor.danger4
    case TextBox.StatusValid   => Style.borderColor.success4
    case _                     => Style.borderColor.gray4
  }

  def getInput(props: Props): TagMod = TagMod(
    inputStatic,
    getCommon(props),
    getBorderColor(props),
    getBackground(props),
    getColor(props),
    TagMod.when(props.context.isDefined) {
      Style.borderRadius.right
    }
  )

  // Context

  private val contextStatic = TagMod(
    Style.backgroundColor.gray2.color.gray6,
    Style.borderWidth.right0.borderRadius.left.borderColor.gray4,
    Style.flexbox.flex.flexbox.itemsCenter
  )

  def getContext(props: Props): TagMod =
    TagMod(getCommon(props), contextStatic)

  // Icon

  private val iconWrapper = TagMod(
    Style.position.absolute.margin.right8.margin.verAuto,
    Style.coordinate.top0.coordinate.bottom0.coordinate.right0,
    Style.backgroundColor.white.height.px16
  )

  def getIcon(props: Props): Option[VdomElement] = props.status match {
    case TextBox.StatusValid =>
      Some(<.div(iconWrapper, Icon(Icon.NameCheck)(), Style.color.success4))
    // case StatusBusy => ??? should have some icon here
    case _ => None
  }
}
