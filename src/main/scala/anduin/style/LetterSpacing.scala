// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class LetterSpacing(classes: List[String] = List.empty) {
  def em0p1: Style = new Style(classes :+ "tracked")
  def em0p05: Style = new Style(classes :+ "tracked-tight")
  def em0p25: Style = new Style(classes :+ "tracked-mega")

  // aliases
  def normal: Style = em0p1
  def tight: Style = em0p05
  def wide: Style = em0p25
}
