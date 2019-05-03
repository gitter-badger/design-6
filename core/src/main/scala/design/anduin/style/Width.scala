// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

// scalastyle:off number.of.methods
private[style] final case class Width(classes: List[String] = List.empty) {
  // pixel
  def px1: Style = new Style(classes :+ "w-px1")
  def px8: Style = new Style(classes :+ "w-px8")
  def px16: Style = new Style(classes :+ "w-px16")
  def px20: Style = new Style(classes :+ "w-px20")
  def px32: Style = new Style(classes :+ "w-px32")
  def px40: Style = new Style(classes :+ "w-px40")
  def px48: Style = new Style(classes :+ "w-px48")
  def px64: Style = new Style(classes :+ "w-px64")
  def px128: Style = new Style(classes :+ "w-px128")
  def px256: Style = new Style(classes :+ "w-px256")
  def px512: Style = new Style(classes :+ "w-px512")
  def px768: Style = new Style(classes :+ "w-px768")
  def px1024: Style = new Style(classes :+ "w-px1024")
  def px1536: Style = new Style(classes :+ "w-px1536")
  // percent
  def pc10: Style = new Style(classes :+ "w-pc10")
  def pc20: Style = new Style(classes :+ "w-pc20")
  def pc25: Style = new Style(classes :+ "w-pc25") // 1/4
  def pc30: Style = new Style(classes :+ "w-pc30")
  def pc33: Style = new Style(classes :+ "w-pc33") // 1/3
  def pc40: Style = new Style(classes :+ "w-pc40")
  def pc50: Style = new Style(classes :+ "w-pc50")
  def pc60: Style = new Style(classes :+ "w-pc60")
  def pc66: Style = new Style(classes :+ "w-pc66") // 2/3
  def pc70: Style = new Style(classes :+ "w-pc70")
  def pc75: Style = new Style(classes :+ "w-pc75") // 3/4
  def pc80: Style = new Style(classes :+ "w-pc80")
  def pc90: Style = new Style(classes :+ "w-pc90")
  def pc100: Style = new Style(classes :+ "w-pc100")
  // special
  def auto: Style = new Style(classes :+ "w-auto")
  def minContent: Style = new Style(classes :+ "w-min")
  def maxContent: Style = new Style(classes :+ "w-max")
  def fitContent: Style = new Style(classes :+ "w-fit")
}
// scalastyle:on number.of.methods
