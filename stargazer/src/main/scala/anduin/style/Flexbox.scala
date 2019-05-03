// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class Flexbox(classes: List[String] = List.empty) {
  // display
  def flex: Style = new Style(classes :+ "flex")
  def inlineFlex: Style = new Style(classes :+ "inline-flex")
  // direction & wrap
  def row: Style = new Style(classes :+ "flex-row")
  def column: Style = new Style(classes :+ "flex-col")
  def wrap: Style = new Style(classes :+ "flex-wrap")
  // align-items
  def itemsStart: Style = new Style(classes :+ "items-start")
  def itemsEnd: Style = new Style(classes :+ "items-end")
  def itemsCenter: Style = new Style(classes :+ "items-center")
  def itemsBaseline: Style = new Style(classes :+ "items-baseline")
  def itemsStretch: Style = new Style(classes :+ "items-stretch")
  // justify-content
  def justifyStart: Style = new Style(classes :+ "justify-start")
  def justifyEnd: Style = new Style(classes :+ "justify-end")
  def justifyCenter: Style = new Style(classes :+ "justify-center")
  def justifyBetween: Style = new Style(classes :+ "justify-between")
  def justifyAround: Style = new Style(classes :+ "justify-around")
  // sizing children
  def fill: Style = new Style(classes :+ "flex-fill")
  def none: Style = new Style(classes :+ "flex-none")
}
