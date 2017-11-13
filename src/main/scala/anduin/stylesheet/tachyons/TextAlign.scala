// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_text-align.css
private[tachyons] final case class TextAlign(classes: List[String] = List.empty) {

  def left: Tachyons = new Tachyons(classes :+ "tl")
  def right: Tachyons = new Tachyons(classes :+ "tr")
  def center: Tachyons = new Tachyons(classes :+ "tc")
  def justify: Tachyons = new Tachyons(classes :+ "tj")
}
