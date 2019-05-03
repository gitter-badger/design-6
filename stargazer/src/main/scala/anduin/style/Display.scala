// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class Display(classes: List[String] = List.empty) {
  def none: Style = new Style(classes :+ "hidden")
  def block: Style = new Style(classes :+ "block")
  def inlineBlock: Style = new Style(classes :+ "inline-block")
}
