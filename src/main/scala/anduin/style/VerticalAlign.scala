// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class VerticalAlign(classes: List[String] = List.empty) {
  def base: Style = new Style(classes :+ "v-base")
  def middle: Style = new Style(classes :+ "v-mid")
  def top: Style = new Style(classes :+ "v-top")
  def bottom: Style = new Style(classes :+ "v-btm")
  def inherit: Style = new Style(classes :+ "v-ihr")
}
