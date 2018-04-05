// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class Display(classes: List[String] = List.empty) {
  def none: Style = new Style(classes :+ "dn")
  def inline: Style = new Style(classes :+ "di")
  def block: Style = new Style(classes :+ "db")
  def inlineBlock: Style = new Style(classes :+ "dib")
}
