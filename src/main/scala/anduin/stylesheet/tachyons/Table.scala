// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_tables.css
private[tachyons] final case class Table(classes: List[String] = List.empty) {

  def collapse: Tachyons = new Tachyons(classes :+ "collapse")
  def stripedLightGray: Tachyons = new Tachyons(classes :+ "striped--light-gray")
}
