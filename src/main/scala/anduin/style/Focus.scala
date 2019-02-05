// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class Focus(classes: List[String] = List.empty) {
  def outlineNone: Style = new Style(classes :+ "focus:outline-none")
  def outlineLight: Style = new Style(classes :+ "focus:outline-light")
  def outlineDark: Style = new Style(classes :+ "focus:outline-dark")
  def border: Style = new Style(classes :+ "focus:border")
  def spread: Style = new Style(classes :+ "focus:spread")
}
