// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class FontWeight(classes: List[String] = List.empty) {
  def normal: Style = new Style(classes :+ "font-normal")
  def medium: Style = new Style(classes :+ "font-medium")
  def semiBold: Style = new Style(classes :+ "font-semi-bold")
  def bold: Style = new Style(classes :+ "font-bold")
}
