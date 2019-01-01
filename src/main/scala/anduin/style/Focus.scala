// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class Focus(classes: List[String] = List.empty) {
  def outline: Style = new Style(classes :+ "fc-o")
  def outlineNone: Style = new Style(classes :+ "fc-on")
  def outlineDark: Style = new Style(classes :+ "fc-od")
  def border: Style = new Style(classes :+ "fc-b")
  def spread: Style = new Style(classes :+ "fc-s")
}
