// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.style

private[style] final case class Position(classes: List[String] = List.empty) {
  // position
  def fixed: Style = new Style(classes :+ "fixed")
  def absolute: Style = new Style(classes :+ "absolute")
  def relative: Style = new Style(classes :+ "relative")
  def sticky: Style = new Style(classes :+ "sticky")
  // top right bottom left
  def pinTop: Style = new Style(classes :+ "pin-t")
  def pinRight: Style = new Style(classes :+ "pin-r")
  def pinBottom: Style = new Style(classes :+ "pin-b")
  def pinLeft: Style = new Style(classes :+ "pin-l")
  def pinAll: Style = new Style(classes :+ "pin")
}
