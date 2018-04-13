// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class MaxWidth(classes: List[String] = List.empty) {
  def px16: Style = new Style(classes :+ "mw1")
  def px32: Style = new Style(classes :+ "mw2")
  def px64: Style = new Style(classes :+ "mw3")
  def px128: Style = new Style(classes :+ "mw4")
  def px256: Style = new Style(classes :+ "mw5")
  def px512: Style = new Style(classes :+ "mw6")
  def px768: Style = new Style(classes :+ "mw7")
  def px1024: Style = new Style(classes :+ "mw8")
  def px1536: Style = new Style(classes :+ "mw9")

  def pc100: Style = new Style(classes :+ "mw-100")
  def none: Style = new Style(classes :+ "mw-none")
}
