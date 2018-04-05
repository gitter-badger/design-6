// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_widths.css
private[tachyons] final case class Width(classes: List[String] = List.empty) {

  def size1: Tachyons = new Tachyons(classes :+ "w1")
  def size2: Tachyons = new Tachyons(classes :+ "w2")
  def size3: Tachyons = new Tachyons(classes :+ "w3")
  def size4: Tachyons = new Tachyons(classes :+ "w4")
  def size5: Tachyons = new Tachyons(classes :+ "w5")

  def percent10: Tachyons = new Tachyons(classes :+ "w-10")
  def percent20: Tachyons = new Tachyons(classes :+ "w-20")
  def percent25: Tachyons = new Tachyons(classes :+ "w-25")
  def percent30: Tachyons = new Tachyons(classes :+ "w-30")
  def percent40: Tachyons = new Tachyons(classes :+ "w-40")
  def percent50: Tachyons = new Tachyons(classes :+ "w-50")
  def percent60: Tachyons = new Tachyons(classes :+ "w-60")
  def percent70: Tachyons = new Tachyons(classes :+ "w-70")
  def percent75: Tachyons = new Tachyons(classes :+ "w-75")
  def percent80: Tachyons = new Tachyons(classes :+ "w-80")
  def percent90: Tachyons = new Tachyons(classes :+ "w-90")
  def percent100: Tachyons = new Tachyons(classes :+ "w-100")

  def third: Tachyons = new Tachyons(classes :+ "w-third")
  def twoThirds: Tachyons = new Tachyons(classes :+ "w-two-thirds")
  def auto: Tachyons = new Tachyons(classes :+ "w-auto")
}
