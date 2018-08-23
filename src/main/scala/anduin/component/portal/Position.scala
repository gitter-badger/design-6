// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

// scalastyle:off underscore.import
import anduin.scalajs.popper._
// scalastyle:on underscore.import

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
