// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_tables.css
private[tachyons] final case class Table(classes: List[String] = List.empty) {

  def collapse: Tachyons = new Tachyons(classes :+ "collapse")
  def stripeLight: Tachyons = new Tachyons(classes :+ "stripe-light")
  def stripeDark: Tachyons = new Tachyons(classes :+ "stripe-dark")
  def stripedNearWhite: Tachyons = new Tachyons(classes :+ "striped--near-white")
  def stripedLightGray: Tachyons = new Tachyons(classes :+ "striped--light-gray")
  def stripedMoonGray: Tachyons = new Tachyons(classes :+ "striped--moon-gray")
  def stripedLightSilver: Tachyons = new Tachyons(classes :+ "striped--light-silver")
}
