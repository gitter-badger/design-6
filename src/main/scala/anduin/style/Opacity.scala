// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_opacity.css
private[style] final case class Opacity(classes: List[String] = List.empty) {

  def zero: Style = new Style(classes :+ "o-0")
  def opacity025: Style = new Style(classes :+ "o-025")
  def opacity05: Style = new Style(classes :+ "o-05")
  def opacity10: Style = new Style(classes :+ "o-10")
  def opacity20: Style = new Style(classes :+ "o-20")
  def opacity30: Style = new Style(classes :+ "o-30")
  def opacity40: Style = new Style(classes :+ "o-40")
  def opacity50: Style = new Style(classes :+ "o-50")
  def opacity60: Style = new Style(classes :+ "o-60")
  def opacity70: Style = new Style(classes :+ "o-70")
  def opacity80: Style = new Style(classes :+ "o-80")
  def opacity90: Style = new Style(classes :+ "o-90")
  def opacity100: Style = new Style(classes :+ "o-100")
}
