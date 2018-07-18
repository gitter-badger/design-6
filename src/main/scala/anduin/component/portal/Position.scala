// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

// Position
sealed trait Position {
  // The `placement` for Popper
  // See https://github.com/FezVrasta/popper.js/blob/master/docs/_includes/popper-documentation.md#popperplacements--enum
  val placement: String
}

case object PositionTopLeft extends Position {
  override val placement: String = "top-end"
}
case object PositionTop extends Position {
  override val placement: String = "top"
}
case object PositionTopRight extends Position {
  override val placement: String = "top-start"
}

case object PositionRightTop extends Position {
  override val placement: String = "right-start"
}
case object PositionRight extends Position {
  override val placement: String = "right"
}
case object PositionRightBottom extends Position {
  override val placement: String = "right-end"
}

case object PositionBottomLeft extends Position {
  override val placement: String = "bottom-start"
}
case object PositionBottom extends Position {
  override val placement: String = "bottom"
}
case object PositionBottomRight extends Position {
  override val placement: String = "bottom-end"
}

case object PositionLeftTop extends Position {
  override val placement: String = "left-start"
}
case object PositionLeft extends Position {
  override val placement: String = "left"
}
case object PositionLeftBottom extends Position {
  override val placement: String = "left-bottom"
}
