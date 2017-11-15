// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.facade.draftjs

import scala.scalajs.js

object StyleMap {

  final val RightTextAlign = "RightTextAlign"
  final val CenterTextAlign = "CenterTextAlign"

  final val TextAlignStyle = js.Dynamic.literal(
    RightTextAlign = js.Dynamic.literal(display = "block", textAlign = "right"),
    CenterTextAlign = js.Dynamic.literal(display = "block", textAlign = "center")
  ).asInstanceOf[js.Object]
}
