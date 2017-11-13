// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_font-family.css
private[tachyons] final case class FontFamily(classes: List[String] = List.empty) {

  def sansSerif: Tachyons = new Tachyons(classes :+ "sans-serif")
  def serif: Tachyons = new Tachyons(classes :+ "serif")
  def systemSansSerif: Tachyons = new Tachyons(classes :+ "system-sans-serif")
  def systemSerif: Tachyons = new Tachyons(classes :+ "system-serif")
  def code: Tachyons = new Tachyons(classes :+ "code")
  def courier: Tachyons = new Tachyons(classes :+ "courier")
  def helvetica: Tachyons = new Tachyons(classes :+ "helvetica")
  def avenir: Tachyons = new Tachyons(classes :+ "avenir")
  def athelas: Tachyons = new Tachyons(classes :+ "athelas")
  def georgia: Tachyons = new Tachyons(classes :+ "georgia")
  def times: Tachyons = new Tachyons(classes :+ "times")
  def bodoni: Tachyons = new Tachyons(classes :+ "bodoni")
  def calisto: Tachyons = new Tachyons(classes :+ "calisto")
  def garamond: Tachyons = new Tachyons(classes :+ "garamond")
  def baskerville: Tachyons = new Tachyons(classes :+ "baskerville")
}
