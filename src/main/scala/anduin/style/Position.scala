// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class Position(classes: List[String] = List.empty) {
  def fixed: Style = new Style(classes :+ "fixed")
  def absolute: Style = new Style(classes :+ "absolute")
  def static: Style = new Style(classes :+ "static")
  def relative: Style = new Style(classes :+ "relative")
}
