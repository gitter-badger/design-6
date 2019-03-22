// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.textbox

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

// This is not only the exposed API but also the main style implementation
// for a text box (see getStyles method).
// - Other props can provide further minor customizations (e.g. TextBoxStatus
//   can provide a custom border color) but the end decision must be made here.
// - The API here should be designed to not knowing the API of TextBox
// - Note that this is for the actual control element (e.g. `input` or
//   `textarea` tag), not the container. In other words, this is for
//   TextBoxBody, not TextBox
sealed trait TextBoxStyle {
  def borderColor: TagMod
  def borderRadius: TagMod
}

object TextBoxStyle {

  trait Minimal extends TextBoxStyle {
    final def borderColor: TagMod = Style.borderColor.transparent
    final def borderRadius: TagMod = TagMod.empty
  }

  trait Full extends TextBoxStyle {
    final def borderColor: TagMod = Style.borderColor.gray4
    final def borderRadius: TagMod = Style.borderRadius.px2
  }

  private val staticStyles = TagMod(
    // Border should always be defined because we use it for focus state
    // - Only the border's color is customizable
    Style.display.block.width.pc100.border.all.borderWidth.px1,
    Style.shadow.focusSpread.borderColor.focusBlue4.transition.allWithShadow
  )

  def getStyles(
    style: TextBoxStyle,
    customColor: Option[TagMod], // isReadOnly or isDisabled
    customBg: Option[TagMod], // isReadOnly or isDisabled
    customBorderColor: Option[TagMod], // TextBoxStatus
    customSize: Option[TagMod] // TextBoxSize, icon and TextBoxStatus
  ): TagMod = TagMod(
    staticStyles,
    customColor.getOrElse(Style.color.gray8),
    customBg.getOrElse(Style.background.white),
    style.borderRadius,
    style match {
      case minimal: Minimal => minimal.borderColor
      case full: Full       => customBorderColor.getOrElse(full.borderColor)
    },
    customSize.getOrElse(Style.height.px32.lineHeight.px16.padding.hor12.fontSize.px13)
  )
}
