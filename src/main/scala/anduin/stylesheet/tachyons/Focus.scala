// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See /vendors/tachyons-4.9.1/_focus.css
private[tachyons] final case class Focus(classes: List[String] = List.empty) {
  def shadowPrimary: Tachyons = new Tachyons(classes :+ "focus-shadow-primary")
}
