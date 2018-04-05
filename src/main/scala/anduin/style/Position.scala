// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_position.css
private[style] final case class Position(classes: List[String] = List.empty) {

  def fixed: Style = new Style(classes :+ "fixed")
  def absolute: Style = new Style(classes :+ "absolute")
  def static: Style = new Style(classes :+ "static")
  def relative: Style = new Style(classes :+ "relative")
}
