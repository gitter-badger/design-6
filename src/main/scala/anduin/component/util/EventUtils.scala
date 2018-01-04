// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.util

import japgolly.scalajs.react.ReactMouseEventFromHtml
import org.scalajs.dom.{MouseEvent, TouchEvent}
import org.scalajs.dom.raw.{ClientRect, Element}

object EventUtils {

  // `0` indicates a left-click
  private final val LeftButton = 0

  def leftButtonClicked(e: MouseEvent): Boolean = {
    e.button == LeftButton
  }

  def leftButtonClicked(e: ReactMouseEventFromHtml): Boolean = {
    e.button == LeftButton
  }

  def isMouseDownEvent(e: ReactMouseEventFromHtml): Boolean = {
    e.eventType == "mousedown"
  }

  private def isInside(x: Double, y: Double, rect: ClientRect): Boolean = {
    rect.left <= x && x <= (rect.left + rect.width) && rect.top <= y && y <= (rect.top + rect.height)
  }

  def clickInside(e: MouseEvent, rect: ClientRect): Boolean = {
    isInside(e.clientX, e.clientY, rect)
  }

  def clickInside(e: MouseEvent, element: Element): Boolean = {
    clickInside(e, element.getBoundingClientRect())
  }

  def clickInside(e: TouchEvent, element: Element): Boolean = {
    val clientX = e.touches(0).clientX
    val clientY = e.touches(0).clientY
    isInside(clientX, clientY, element.getBoundingClientRect())
  }
}
