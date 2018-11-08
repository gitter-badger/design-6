// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class FontSize(classes: List[String] = List.empty) {
  def px48: Style = new Style(classes :+ "fs-48")
  def px32: Style = new Style(classes :+ "fs-32")
  def px24: Style = new Style(classes :+ "fs-24")
  def px20: Style = new Style(classes :+ "fs-20")
  def px16: Style = new Style(classes :+ "fs-16")
  def px15: Style = new Style(classes :+ "fs-15")
  def px13: Style = new Style(classes :+ "fs-13")
  def px12: Style = new Style(classes :+ "fs-12")
  def px10: Style = new Style(classes :+ "fs-10")
}
