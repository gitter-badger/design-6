// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.style

private[style] final case class LineHeight(classes: List[String] = List.empty) {
  def px16: Style = new Style(classes :+ "leading-16")
  def px20: Style = new Style(classes :+ "leading-20")
  def px24: Style = new Style(classes :+ "leading-24")
  def px28: Style = new Style(classes :+ "leading-28")
  def px32: Style = new Style(classes :+ "leading-32")
  def px40: Style = new Style(classes :+ "leading-40")
  def px44: Style = new Style(classes :+ "leading-44")
  def ratio1p5: Style = new Style(classes :+ "leading-r1p5")
}
