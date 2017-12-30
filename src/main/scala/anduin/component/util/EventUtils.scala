// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.util

import japgolly.scalajs.react.ReactMouseEventFromHtml
import org.scalajs.dom.MouseEvent
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

  def clickInside(e: MouseEvent, rect: ClientRect): Boolean = {
    rect.left <= e.clientX && e.clientX <= (rect.left + rect.width) && rect.top <= e.clientY && e.clientY <= (rect.top + rect.height)
  }

  def clickInside(e: MouseEvent, element: Element): Boolean = {
    clickInside(e, element.getBoundingClientRect())
  }
}
