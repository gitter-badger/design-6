// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.popper

sealed abstract class PopperPlacement(val raw: String)

object PopperPlacement {
  case object AutoStart extends PopperPlacement("auto-start")
  case object Auto extends PopperPlacement("auto")
  case object AutoEnd extends PopperPlacement("auto-end")
  case object TopStart extends PopperPlacement("top-start")
  case object Top extends PopperPlacement("top")
  case object TopEnd extends PopperPlacement("top-end")
  case object RightStart extends PopperPlacement("right-start")
  case object Right extends PopperPlacement("right")
  case object RightEnd extends PopperPlacement("right-end")
  case object BottomEnd extends PopperPlacement("bottom-end")
  case object Bottom extends PopperPlacement("bottom")
  case object BottomStart extends PopperPlacement("bottom-start")
  case object LeftEnd extends PopperPlacement("left-end")
  case object Left extends PopperPlacement("left")
  case object LeftStart extends PopperPlacement("left-start")
}
