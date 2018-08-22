// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

// scalastyle:off underscore.import
import anduin.scalajs.popper._
// scalastyle:on underscore.import

// This is anduin.component Position, which might not be mapped directly 1:1
// to Popper or whatever library's position system
sealed trait Position

case object PositionTopLeft extends Position
case object PositionTop extends Position
case object PositionTopRight extends Position
case object PositionRightTop extends Position
case object PositionRight extends Position
case object PositionRightBottom extends Position
case object PositionBottomLeft extends Position
case object PositionBottom extends Position
case object PositionBottomRight extends Position
case object PositionLeftTop extends Position
case object PositionLeft extends Position
case object PositionLeftBottom extends Position

object Position {
  // scalastyle:off cyclomatic.complexity
  def getPopperPlacement(position: Position): Placement = {
    position match {
      case PositionTopLeft     => PlacementTopStart
      case PositionTop         => PlacementTop
      case PositionTopRight    => PlacementTopEnd
      case PositionRightTop    => PlacementRightStart
      case PositionRight       => PlacementRight
      case PositionRightBottom => PlacementRightEnd
      case PositionBottomLeft  => PlacementBottomStart
      case PositionBottom      => PlacementBottom
      case PositionBottomRight => PlacementBottomEnd
      case PositionLeftTop     => PlacementLeftStart
      case PositionLeft        => PlacementLeft
      case PositionLeftBottom  => PlacementLeftEnd
    }
  }
  // scalastyle:on cyclomatic.complexity
}
