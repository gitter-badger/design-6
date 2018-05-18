// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class Focus(classes: List[String] = List.empty) {
  def outline: Style = new Style(classes :+ "fc-o")
  def outlineDark: Style = new Style(classes :+ "fc-od")
  def border: Style = new Style(classes :+ "fc-b")
  def shadow: Style = new Style(classes :+ "fc-s")
}
