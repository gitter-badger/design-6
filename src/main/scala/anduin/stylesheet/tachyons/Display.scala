// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_display.css
private[tachyons] final case class Display(classes: List[String] = List.empty) {

  def none: Tachyons = new Tachyons(classes :+ "dn")
  def inline: Tachyons = new Tachyons(classes :+ "di")
  def block: Tachyons = new Tachyons(classes :+ "db")
  def inlineBlock: Tachyons = new Tachyons(classes :+ "dib")
  def inlineTable: Tachyons = new Tachyons(classes :+ "dit")
  def table: Tachyons = new Tachyons(classes :+ "dt")
  def tableCell: Tachyons = new Tachyons(classes :+ "dtc")
  def tableRow: Tachyons = new Tachyons(classes :+ "dt-row")
  def tableRowGroup: Tachyons = new Tachyons(classes :+ "dt-row-group")
  def tableColumn: Tachyons = new Tachyons(classes :+ "dt-column")
  def tableColumnGroup: Tachyons = new Tachyons(classes :+ "dt-column-group")
  def tableLayoutFixed: Tachyons = new Tachyons(classes :+ "dt--fixed")
}
