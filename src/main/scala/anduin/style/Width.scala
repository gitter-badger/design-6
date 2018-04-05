// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_widths.css
private[style] final case class Width(classes: List[String] = List.empty) {

  def size1: Style = new Style(classes :+ "w1")
  def size2: Style = new Style(classes :+ "w2")
  def size3: Style = new Style(classes :+ "w3")
  def size4: Style = new Style(classes :+ "w4")
  def size5: Style = new Style(classes :+ "w5")

  def percent10: Style = new Style(classes :+ "w-10")
  def percent20: Style = new Style(classes :+ "w-20")
  def percent25: Style = new Style(classes :+ "w-25")
  def percent30: Style = new Style(classes :+ "w-30")
  def percent40: Style = new Style(classes :+ "w-40")
  def percent50: Style = new Style(classes :+ "w-50")
  def percent60: Style = new Style(classes :+ "w-60")
  def percent70: Style = new Style(classes :+ "w-70")
  def percent75: Style = new Style(classes :+ "w-75")
  def percent80: Style = new Style(classes :+ "w-80")
  def percent90: Style = new Style(classes :+ "w-90")
  def percent100: Style = new Style(classes :+ "w-100")

  def third: Style = new Style(classes :+ "w-third")
  def twoThirds: Style = new Style(classes :+ "w-two-thirds")
  def auto: Style = new Style(classes :+ "w-auto")
}
