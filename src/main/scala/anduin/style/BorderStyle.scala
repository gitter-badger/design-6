// Copyright (C) 2014-2019 Anduin Transactions Inc.
package anduin.style

private[style] final case class BorderStyle(classes: List[String] = List.empty) {
  def dotted: Style = new Style(classes :+ "b--dotted")
  def dashed: Style = new Style(classes :+ "b--dashed")
  def solid: Style = new Style(classes :+ "b--solid")
  def none: Style = new Style(classes :+ "b--none")
}
