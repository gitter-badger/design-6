// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import anduin.scalajs.popper.PopperPlacement

// This is anduin.component Position, which might not be mapped directly 1:1
// to Popper or whatever library's position system
sealed trait Position

// @TODO: to avoid huge changes in first implementation, we append "Base" to
// these trait. However, a proper way is to rename PositionTop object to
// PositionTopCenter, so these traits don't need "Base" suffix
sealed trait PositionTopBase extends Position
sealed trait PositionRightBase extends Position
sealed trait PositionBottomBase extends Position
sealed trait PositionLeftBase extends Position

case object PositionTopLeft extends PositionTopBase
case object PositionTop extends PositionTopBase
case object PositionTopRight extends PositionTopBase
case object PositionRightTop extends PositionRightBase
case object PositionRight extends PositionRightBase
case object PositionRightBottom extends PositionRightBase
case object PositionBottomLeft extends PositionBottomBase
case object PositionBottom extends PositionBottomBase
case object PositionBottomRight extends PositionBottomBase
case object PositionLeftTop extends PositionLeftBase
case object PositionLeft extends PositionLeftBase
case object PositionLeftBottom extends PositionLeftBase

object Position {

  // scalastyle:off cyclomatic.complexity
  def getPopperPlacement(position: Position): PopperPlacement = {
    position match {
      case PositionTopLeft     => PopperPlacement.TopStart
      case PositionTop         => PopperPlacement.Top
      case PositionTopRight    => PopperPlacement.TopEnd
      case PositionRightTop    => PopperPlacement.RightStart
      case PositionRight       => PopperPlacement.Right
      case PositionRightBottom => PopperPlacement.RightEnd
      case PositionBottomLeft  => PopperPlacement.BottomStart
      case PositionBottom      => PopperPlacement.Bottom
      case PositionBottomRight => PopperPlacement.BottomEnd
      case PositionLeftTop     => PopperPlacement.LeftStart
      case PositionLeft        => PopperPlacement.Left
      case PositionLeftBottom  => PopperPlacement.LeftEnd
    }
  }
  // scalastyle:on cyclomatic.complexity
}
