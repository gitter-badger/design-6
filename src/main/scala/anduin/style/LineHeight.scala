// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class LineHeight(classes: List[String] = List.empty) {
  def px16: Style = new Style(classes :+ "lh-16")
  def px20: Style = new Style(classes :+ "lh-20")
  def px24: Style = new Style(classes :+ "lh-24")
  def px40: Style = new Style(classes :+ "lh-40")
  def ratio1p5: Style = new Style(classes :+ "lh-r1p5")
  def copy: Style = ratio1p5
}
