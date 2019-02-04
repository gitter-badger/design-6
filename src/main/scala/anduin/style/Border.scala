// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class Border(classes: List[String] = List.empty) {
  def all: Style = new Style(classes :+ "border-all")
  def top: Style = new Style(classes :+ "border-top")
  def right: Style = new Style(classes :+ "border-right")
  def bottom: Style = new Style(classes :+ "border-bottom")
  def left: Style = new Style(classes :+ "border-left")
  def none: Style = new Style(classes :+ "border-none")
}
