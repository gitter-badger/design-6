// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class LineHeight(classes: List[String] = List.empty) {
  def px16: Style = new Style(classes :+ "lh-1")
  def px24: Style = new Style(classes :+ "lh-2")
  def px40: Style = new Style(classes :+ "lh-3")
  def ratio1p5: Style = new Style(classes :+ "lh-copy")
  def copy: Style = ratio1p5
}
