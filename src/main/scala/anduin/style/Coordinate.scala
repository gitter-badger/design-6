// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_coordinates.css
private[style] final case class Coordinate(classes: List[String] = List.empty) {
  def top0: Style = new Style(classes :+ "top-0")
  def right0: Style = new Style(classes :+ "right-0")
  def bottom0: Style = new Style(classes :+ "bottom-0")
  def left0: Style = new Style(classes :+ "left-0")
  def fill: Style = new Style(classes :+ "absolute--fill")
}
