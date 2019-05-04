// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.style

private[style] final case class FontSize(classes: List[String] = List.empty) {
  def px35: Style = new Style(classes :+ "text-35")
  def px30: Style = new Style(classes :+ "text-30")
  def px26: Style = new Style(classes :+ "text-26")
  def px23: Style = new Style(classes :+ "text-23")
  def px20: Style = new Style(classes :+ "text-20")
  def px17: Style = new Style(classes :+ "text-17")
  def px15: Style = new Style(classes :+ "text-15")
  def px13: Style = new Style(classes :+ "text-13")
  def px11: Style = new Style(classes :+ "text-11")
  def px10: Style = new Style(classes :+ "text-10")
  def px9: Style = new Style(classes :+ "text-9")
}
