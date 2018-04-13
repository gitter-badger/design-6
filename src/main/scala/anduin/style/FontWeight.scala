// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class FontWeight(classes: List[String] = List.empty) {
  def thin: Style = new Style(classes :+ "fw1")
  def extraLight: Style = new Style(classes :+ "fw2")
  def light: Style = new Style(classes :+ "fw3")
  def normal: Style = new Style(classes :+ "fw4")
  def medium: Style = new Style(classes :+ "fw5")
  def semiBold: Style = new Style(classes :+ "fw6")
  def bold: Style = new Style(classes :+ "fw7")
}
