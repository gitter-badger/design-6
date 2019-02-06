// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class TextTransform(classes: List[String] = List.empty) {
  def capitalize: Style = new Style(classes :+ "ttc")
  def uppercase: Style = new Style(classes :+ "ttu")
  def none: Style = new Style(classes :+ "ttn")
}
