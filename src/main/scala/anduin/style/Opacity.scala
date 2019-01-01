// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class Opacity(classes: List[String] = List.empty) {
  def pc0: Style = new Style(classes :+ "o-0")
  def pc10: Style = new Style(classes :+ "o-10")
  def pc20: Style = new Style(classes :+ "o-20")
  def pc30: Style = new Style(classes :+ "o-30")
  def pc50: Style = new Style(classes :+ "o-50")
  def pc80: Style = new Style(classes :+ "o-80")
  def pc90: Style = new Style(classes :+ "o-90")
  def pc100: Style = new Style(classes :+ "o-100")
}
