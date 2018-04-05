// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

private[tachyons] final case class Display(classes: List[String] = List.empty) {
  def none: Tachyons = new Tachyons(classes :+ "dn")
  def inline: Tachyons = new Tachyons(classes :+ "di")
  def block: Tachyons = new Tachyons(classes :+ "db")
  def inlineBlock: Tachyons = new Tachyons(classes :+ "dib")
}
