// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class Width(classes: List[String] = List.empty) {

  def px16: Style = new Style(classes :+ "w1")
  def px32: Style = new Style(classes :+ "w2")
  def px64: Style = new Style(classes :+ "w3")
  def px128: Style = new Style(classes :+ "w4")
  def px256: Style = new Style(classes :+ "w5")

  def pc10: Style = new Style(classes :+ "w-10")
  def pc20: Style = new Style(classes :+ "w-20")
  def pc25: Style = new Style(classes :+ "w-25")
  def pc30: Style = new Style(classes :+ "w-30")
  def pc40: Style = new Style(classes :+ "w-40")
  def pc50: Style = new Style(classes :+ "w-50")
  def pc60: Style = new Style(classes :+ "w-60")
  def pc70: Style = new Style(classes :+ "w-70")
  def pc75: Style = new Style(classes :+ "w-75")
  def pc80: Style = new Style(classes :+ "w-80")
  def pc90: Style = new Style(classes :+ "w-90")
  def pc100: Style = new Style(classes :+ "w-100")

  def pcOneRd: Style = new Style(classes :+ "w-third")
  def pcTwoRd: Style = new Style(classes :+ "w-two-thirds")

  def auto: Style = new Style(classes :+ "w-auto")
}
