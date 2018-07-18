// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import japgolly.scalajs.react.vdom.TagMod
import org.scalajs.dom.document
import org.scalajs.dom.raw.Element

import anduin.style.Style

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

object Position {

  // these are required styles for all consumer of Position (eg: Popover, Tooltip)
  val styles: TagMod = Style.position.fixed.opacity.pc0.coordinate.top0

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
    val style = s"opacity: 1; top: ${offsetTop}px; left: ${offsetLeft}px"
    content.setAttribute("style", style)
  }
  // scalastyle:on cyclomatic.complexity
}
