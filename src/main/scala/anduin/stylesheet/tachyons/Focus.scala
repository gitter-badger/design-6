// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See
private[tachyons] final case class Focus(classes: List[String] = List.empty) {
  def shadowPrimary: Tachyons = new Tachyons(classes :+ "focus-shadow-primary")
}
