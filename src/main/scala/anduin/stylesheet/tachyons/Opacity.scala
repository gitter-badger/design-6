// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_opacity.css
private[tachyons] final case class Opacity(classes: List[String] = List.empty) {

  def zero: Tachyons = new Tachyons(classes :+ "o-0")
  def opacity025: Tachyons = new Tachyons(classes :+ "o-025")
  def opacity05: Tachyons = new Tachyons(classes :+ "o-05")
  def opacity10: Tachyons = new Tachyons(classes :+ "o-10")
  def opacity20: Tachyons = new Tachyons(classes :+ "o-20")
  def opacity30: Tachyons = new Tachyons(classes :+ "o-30")
  def opacity40: Tachyons = new Tachyons(classes :+ "o-40")
  def opacity50: Tachyons = new Tachyons(classes :+ "o-50")
  def opacity60: Tachyons = new Tachyons(classes :+ "o-60")
  def opacity70: Tachyons = new Tachyons(classes :+ "o-70")
  def opacity80: Tachyons = new Tachyons(classes :+ "o-80")
  def opacity90: Tachyons = new Tachyons(classes :+ "o-90")
  def opacity100: Tachyons = new Tachyons(classes :+ "o-100")
}
