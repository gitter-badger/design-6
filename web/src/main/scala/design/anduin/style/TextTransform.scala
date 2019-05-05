// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.style

private[style] final case class TextTransform(classes: List[String] = List.empty) {
  def capitalize: Style = new Style(classes :+ "capitalize")
  def uppercase: Style = new Style(classes :+ "uppercase")
  def none: Style = new Style(classes :+ "normal-case")
}
