// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

// scalastyle:off number.of.methods
private[style] final case class Flexbox(classes: List[String] = List.empty) {

  def flex: Style = new Style(classes :+ "flex")
  def inlineFlex: Style = new Style(classes :+ "inline-flex")

  def row: Style = new Style(classes :+ "flex-row")
  def rowReverse: Style = new Style(classes :+ "flex-row-reverse")
  def column: Style = new Style(classes :+ "flex-column")
  def columnReverse: Style = new Style(classes :+ "flex-column-reverse")
  def wrap: Style = new Style(classes :+ "flex-wrap")
  def wrapReverse: Style = new Style(classes :+ "flex-wrap-reverse")
  def noWrap: Style = new Style(classes :+ "flex-nowrap")

  def itemsStart: Style = new Style(classes :+ "items-start")
  def itemsEnd: Style = new Style(classes :+ "items-end")
  def itemsCenter: Style = new Style(classes :+ "items-center")
  def itemsBaseline: Style = new Style(classes :+ "items-baseline")
  def itemsStretch: Style = new Style(classes :+ "items-stretch")

  def justifyStart: Style = new Style(classes :+ "justify-start")
  def justifyEnd: Style = new Style(classes :+ "justify-end")
  def justifyCenter: Style = new Style(classes :+ "justify-center")
  def justifyBetween: Style = new Style(classes :+ "justify-between")
  def justifyAround: Style = new Style(classes :+ "justify-around")

  // Children
  def fixed: Style = new Style(classes :+ "flex-fixed")
  def none: Style = new Style(classes :+ "flex-none")

  def basis15: Style = new Style(classes :+ "basis-15")
  def basis25: Style = new Style(classes :+ "basis-25")
  def basis40: Style = new Style(classes :+ "basis-40")
  def basis50: Style = new Style(classes :+ "basis-50")
  def basis60: Style = new Style(classes :+ "basis-60")
  def basis70: Style = new Style(classes :+ "basis-70")
}
// scalastyle:on number.of.methods
