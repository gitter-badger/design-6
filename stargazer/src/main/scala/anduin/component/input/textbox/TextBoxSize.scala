// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.textbox

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

trait TextBoxSize {
  def height: Int
  def horPadding: Int
  def text: TagMod

  // convenient helpers
  final def heightPx: String = s"${height}px"
  final def horPaddingPx: String = s"${horPadding}px"
}

private[textbox] object TextBoxSize {
  trait Px32 extends TextBoxSize {
    final def height: Int = 32
    final def horPadding: Int = 12
    final def text: TagMod = Style.fontSize.px13.lineHeight.px20
  }
  trait Px40 extends TextBoxSize {
    final def height: Int = 40
    final def horPadding: Int = 16
    final def text: TagMod = Style.fontSize.px15.lineHeight.px24
  }
}
