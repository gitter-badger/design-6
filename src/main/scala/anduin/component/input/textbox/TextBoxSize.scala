// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.textbox

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

sealed trait TextBoxSize {
  protected def common: TagMod
  protected def singleOnly: TagMod
  protected final val areaOnly: TagMod = Style.lineHeight.ratio1p5
  def single: TagMod = TagMod(common, singleOnly)
  def area: TagMod = TagMod(common, areaOnly)
}

private[textbox] object TextBoxSize {
  trait Px32 extends TextBoxSize {
    val common: TagMod = Style.fontSize.px13.padding.hor12
    val singleOnly: TagMod = Style.lineHeight.px16.height.px32
  }
  trait Px40 extends TextBoxSize {
    val common: TagMod = Style.fontSize.px16.padding.hor16
    val singleOnly: TagMod = Style.lineHeight.px24.height.px40
  }
}
