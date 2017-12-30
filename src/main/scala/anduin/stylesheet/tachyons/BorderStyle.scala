// Copyright (C) 2014-2018 Anduin Transactions Inc.
package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_border-style.css
private[tachyons] final case class BorderStyle(classes: List[String] = List.empty) {

  def dotted: Tachyons = new Tachyons(classes :+ "b--dotted")
  def dashed: Tachyons = new Tachyons(classes :+ "b--dashed")
  def solid: Tachyons = new Tachyons(classes :+ "b--solid")
  def none: Tachyons = new Tachyons(classes :+ "b--none")
}
