// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_letter-spacing.css
private[tachyons] final case class LetterSpacing(classes: List[String] = List.empty) {

  def tracked: Tachyons = new Tachyons(classes :+ "tracked")
  def trackedTight: Tachyons = new Tachyons(classes :+ "tracked-tight")
  def trackedMega: Tachyons = new Tachyons(classes :+ "tracked-mega")
}
