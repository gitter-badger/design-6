// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_word-break.css
private[tachyons] final case class WordBreak(classes: List[String] = List.empty) {

  def normal: Tachyons = new Tachyons(classes :+ "word-normal")
  def wrap: Tachyons = new Tachyons(classes :+ "word-wrap")
  def noWrap: Tachyons = new Tachyons(classes :+ "word-nowrap")
}
