// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import anduin.scalajs.popper.PopperPlacement

// This is anduin.component Position, which might not be mapped directly 1:1
// to Popper or whatever library's position system
sealed trait Position

sealed trait PositionTop extends Position
sealed trait PositionRight extends Position
sealed trait PositionBottom extends Position
sealed trait PositionLeft extends Position

case object PositionTopLeft extends PositionTop
case object PositionTopCenter extends PositionTop
case object PositionTopRight extends PositionTop
case object PositionRightTop extends PositionRight
case object PositionRightCenter extends PositionRight
case object PositionRightBottom extends PositionRight
case object PositionBottomLeft extends PositionBottom
case object PositionBottomCenter extends PositionBottom
case object PositionBottomRight extends PositionBottom
case object PositionLeftTop extends PositionLeft
case object PositionLeftCenter extends PositionLeft
case object PositionLeftBottom extends PositionLeft

object Position {

  // scalastyle:off cyclomatic.complexity
  private[component] def getPopperPlacement(position: Position): PopperPlacement = {
    position match {
      case PositionTopLeft      => PopperPlacement.TopStart
      case PositionTopCenter    => PopperPlacement.Top
      case PositionTopRight     => PopperPlacement.TopEnd
      case PositionRightTop     => PopperPlacement.RightStart
      case PositionRightCenter  => PopperPlacement.Right
      case PositionRightBottom  => PopperPlacement.RightEnd
      case PositionBottomLeft   => PopperPlacement.BottomStart
      case PositionBottomCenter => PopperPlacement.Bottom
      case PositionBottomRight  => PopperPlacement.BottomEnd
      case PositionLeftTop      => PopperPlacement.LeftStart
      case PositionLeftCenter   => PopperPlacement.Left
      case PositionLeftBottom   => PopperPlacement.LeftEnd
    }
  }
  // scalastyle:on cyclomatic.complexity
}
