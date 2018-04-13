// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class BorderWidth(classes: List[String] = List.empty) {
  def px0: Style = new Style(classes :+ "bw0")
  def px1: Style = new Style(classes :+ "bw1")
  def px2: Style = new Style(classes :+ "bw2")
  def px4: Style = new Style(classes :+ "bw3")

  def top0: Style = new Style(classes :+ "bt-0")
  def right0: Style = new Style(classes :+ "br-0")
  def bottom0: Style = new Style(classes :+ "bb-0")
  def left0: Style = new Style(classes :+ "bl-0")
}
