// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class FontSize(classes: List[String] = List.empty) {
  def px48: Style = new Style(classes :+ "f1")
  def px32: Style = new Style(classes :+ "f2")
  def px26: Style = new Style(classes :+ "f3")
  def px20: Style = new Style(classes :+ "f4")
  def px16: Style = new Style(classes :+ "f5")
  def px14: Style = new Style(classes :+ "f6")
  def px12: Style = new Style(classes :+ "f7")
}
