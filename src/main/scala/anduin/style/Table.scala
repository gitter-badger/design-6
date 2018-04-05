// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_tables.css
private[style] final case class Table(classes: List[String] = List.empty) {

  def collapse: Style = new Style(classes :+ "collapse")
  def stripedLightGray: Style = new Style(classes :+ "striped--light-gray")
}
