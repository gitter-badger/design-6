// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import org.scalajs.dom.document
import org.scalajs.dom.raw.Element

// Position
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
  def calculate(
    position: Position,
    target: Element,
    content: Element,
    verticalOffset: Double = 0,
    horizontalOffset: Double = 0
  ): Unit = {
    val rect = target.getBoundingClientRect()
    val (h, w) = (content.clientHeight, content.clientWidth)
    val (top, left) = position match {
      case PositionTopLeft  => (rect.top - h, rect.left)
      case PositionTop      => (rect.top - h, rect.left + 0.5 * rect.width - 0.5 * w)
      case PositionTopRight => (rect.top - h, rect.left + rect.width - w)

      case PositionRightTop    => (rect.top, rect.left + rect.width)
      case PositionRight       => (rect.top + 0.5 * rect.height - 0.5 * h, rect.left + rect.width)
      case PositionRightBottom => (rect.top + rect.height - h, rect.left + rect.width)

      case PositionBottomRight => (rect.top + rect.height, rect.left + rect.width - w)
      case PositionBottom      => (rect.top + rect.height, rect.left + 0.5 * rect.width - 0.5 * w)
      case PositionBottomLeft  => (rect.top + rect.height, rect.left)

      case PositionLeftTop    => (rect.top, rect.left - w)
      case PositionLeft       => (rect.top + 0.5 * rect.height - 0.5 * h, rect.left - w)
      case PositionLeftBottom => (rect.top + rect.height - h, rect.left - w)
    }
    val offsetTop = top + verticalOffset + document.body.scrollTop
    val offsetLeft = left + horizontalOffset + document.body.scrollLeft
    content.setAttribute("style", s"top: ${offsetTop}px; left: ${offsetLeft}px")
  }
  // scalastyle:on cyclomatic.complexity
}
