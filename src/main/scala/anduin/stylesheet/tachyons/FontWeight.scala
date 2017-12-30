// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_font-weight.css
private[tachyons] final case class FontWeight(classes: List[String] = List.empty) {

  def normal: Tachyons = new Tachyons(classes :+ "normal")
  def bold: Tachyons = new Tachyons(classes :+ "b")
  def w100: Tachyons = new Tachyons(classes :+ "fw1")
  def w200: Tachyons = new Tachyons(classes :+ "fw2")
  def w300: Tachyons = new Tachyons(classes :+ "fw3")
  def w400: Tachyons = new Tachyons(classes :+ "fw4")
  def w500: Tachyons = new Tachyons(classes :+ "fw5")
  def w600: Tachyons = new Tachyons(classes :+ "fw6")
  def w700: Tachyons = new Tachyons(classes :+ "fw7")
  def w800: Tachyons = new Tachyons(classes :+ "fw8")
  def w900: Tachyons = new Tachyons(classes :+ "fw9")
}
