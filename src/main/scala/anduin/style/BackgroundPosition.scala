// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_background-position.css
private[style] final case class BackgroundPosition(classes: List[String] = List.empty) {

  def center: Style = new Style(classes :+ "bg-center")
  def top: Style = new Style(classes :+ "bg-top")
  def right: Style = new Style(classes :+ "bg-right")
  def bottom: Style = new Style(classes :+ "bg-bottom")
  def left: Style = new Style(classes :+ "bg-left")
}
