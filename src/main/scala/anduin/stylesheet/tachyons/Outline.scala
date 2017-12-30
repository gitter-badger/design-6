// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_outlines.css
private[tachyons] final case class Outline(classes: List[String] = List.empty) {

  def solid: Tachyons = new Tachyons(classes :+ "outline")
  def transparent: Tachyons = new Tachyons(classes :+ "outline-transparent")
  def zero: Tachyons = new Tachyons(classes :+ "outline-0")
}
