// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class Opacity(classes: List[String] = List.empty) {
  def pc0: Style = new Style(classes :+ "opacity-0")
  def pc10: Style = new Style(classes :+ "opacity-10")
  def pc20: Style = new Style(classes :+ "opacity-20")
  def pc30: Style = new Style(classes :+ "opacity-30")
  def pc40: Style = new Style(classes :+ "opacity-40")
  def pc50: Style = new Style(classes :+ "opacity-50")
  def pc60: Style = new Style(classes :+ "opacity-60")
  def pc70: Style = new Style(classes :+ "opacity-70")
  def pc80: Style = new Style(classes :+ "opacity-80")
  def pc90: Style = new Style(classes :+ "opacity-90")
  def pc100: Style = new Style(classes :+ "opacity-100")
}
