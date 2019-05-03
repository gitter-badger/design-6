// Copyright (C) 2014-2019 Anduin Transactions Inc.
package anduin.style

private[style] final case class BorderStyle(classes: List[String] = List.empty) {
  def dashed: Style = new Style(classes :+ "border-dashed")
}
