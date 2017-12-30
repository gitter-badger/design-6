// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_flexbox.css
// scalastyle:off number.of.methods
private[tachyons] final case class Flexbox(classes: List[String] = List.empty) {

  def flex: Tachyons = new Tachyons(classes :+ "flex")
  def inlineFlex: Tachyons = new Tachyons(classes :+ "inline-flex")
  def auto: Tachyons = new Tachyons(classes :+ "flex-auto")
  def none: Tachyons = new Tachyons(classes :+ "flex-none")
  def column: Tachyons = new Tachyons(classes :+ "flex-column")
  def row: Tachyons = new Tachyons(classes :+ "flex-row")
  def wrap: Tachyons = new Tachyons(classes :+ "flex-wrap")
  def noWrap: Tachyons = new Tachyons(classes :+ "flex-nowrap")
  def wrapReverse: Tachyons = new Tachyons(classes :+ "flex-wrap-reverse")
  def columnReverse: Tachyons = new Tachyons(classes :+ "flex-column-reverse")
  def rowReverse: Tachyons = new Tachyons(classes :+ "flex-row-reverse")

  def itemsStart: Tachyons = new Tachyons(classes :+ "items-start")
  def itemsEnd: Tachyons = new Tachyons(classes :+ "items-end")
  def itemsCenter: Tachyons = new Tachyons(classes :+ "items-center")
  def itemsBaseline: Tachyons = new Tachyons(classes :+ "items-baseline")
  def itemsStretch: Tachyons = new Tachyons(classes :+ "items-stretch")

  def selfStart: Tachyons = new Tachyons(classes :+ "self-start")
  def selfEnd: Tachyons = new Tachyons(classes :+ "self-end")
  def selfCenter: Tachyons = new Tachyons(classes :+ "self-center")
  def selfBaseline: Tachyons = new Tachyons(classes :+ "self-baseline")
  def selfStretch: Tachyons = new Tachyons(classes :+ "self-stretch")

  def justifyStart: Tachyons = new Tachyons(classes :+ "justify-start")
  def justifyEnd: Tachyons = new Tachyons(classes :+ "justify-end")
  def justifyCenter: Tachyons = new Tachyons(classes :+ "justify-center")
  def justifyBetween: Tachyons = new Tachyons(classes :+ "justify-between")
  def justifyAround: Tachyons = new Tachyons(classes :+ "justify-around")

  def contentStart: Tachyons = new Tachyons(classes :+ "content-start")
  def contentEnd: Tachyons = new Tachyons(classes :+ "content-end")
  def contentCenter: Tachyons = new Tachyons(classes :+ "content-center")
  def contentBetween: Tachyons = new Tachyons(classes :+ "content-between")
  def contentAround: Tachyons = new Tachyons(classes :+ "content-around")
  def contentStretch: Tachyons = new Tachyons(classes :+ "content-stretch")

  def order0: Tachyons = new Tachyons(classes :+ "order-0")
  def order1: Tachyons = new Tachyons(classes :+ "order-1")
  def order2: Tachyons = new Tachyons(classes :+ "order-2")
  def order3: Tachyons = new Tachyons(classes :+ "order-3")
  def order4: Tachyons = new Tachyons(classes :+ "order-4")
  def order5: Tachyons = new Tachyons(classes :+ "order-5")
  def order6: Tachyons = new Tachyons(classes :+ "order-6")
  def order7: Tachyons = new Tachyons(classes :+ "order-7")
  def order8: Tachyons = new Tachyons(classes :+ "order-8")
  def orderLast: Tachyons = new Tachyons(classes :+ "order-last")

  def grow0: Tachyons = new Tachyons(classes :+ "flex-grow-0")
  def grow1: Tachyons = new Tachyons(classes :+ "flex-grow-1")

  def shrink0: Tachyons = new Tachyons(classes :+ "flex-shrink-0")
  def shrink1: Tachyons = new Tachyons(classes :+ "flex-shrink-1")
}
// scalastyle:on number.of.methods
