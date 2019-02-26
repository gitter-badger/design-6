// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.layout.header.utils

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object MainHeaderUtils {
  val vr = <.div(Style.height.px16.margin.hor8.border.left.borderColor.gray7)

  val interactionStyles: TagMod = TagMod(
    Style.background.hoverGray7.color.hoverWhite,
    Style.background.activeGray9.color.activeWhite,
    Style.outline.focusDark.transition.allWithOutline
  )

  val buttonStyles: TagMod = TagMod(
    interactionStyles,
    Style.display.block.borderRadius.px2.padding.all4.color.gray5
  )

  def popButtonStyles(isOpened: Boolean): TagMod = TagMod(
    buttonStyles,
    TagMod.when(isOpened) { Style.background.gray9 }
  )
}
