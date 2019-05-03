// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class VerticalAlign(classes: List[String] = List.empty) {
  def base: Style = new Style(classes :+ "align-baseline")
  def middle: Style = new Style(classes :+ "align-middle")
  def top: Style = new Style(classes :+ "align-top")
  def bottom: Style = new Style(classes :+ "align-bottom")
  def inherit: Style = new Style(classes :+ "align-inherit")
}
