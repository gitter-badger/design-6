// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_letter-spacing.css
private[style] final case class LetterSpacing(classes: List[String] = List.empty) {

  def tracked: Style = new Style(classes :+ "tracked")
  def trackedTight: Style = new Style(classes :+ "tracked-tight")
  def trackedMega: Style = new Style(classes :+ "tracked-mega")
}
