// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.style

private[style] final case class Cursor(classes: List[String] = List.empty) {
  def pointer: Style = new Style(classes :+ "cursor-pointer")
  def crosshair: Style = new Style(classes :+ "cursor-crosshair")
  def help: Style = new Style(classes :+ "cursor-help")
  def notAllowed: Style = new Style(classes :+ "cursor-not-allowed")
}
