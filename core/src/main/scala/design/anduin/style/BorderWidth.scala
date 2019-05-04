// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.style

private[style] final case class BorderWidth(classes: List[String] = List.empty) {
  def px1: Style = new Style(classes :+ "border-1")
  def px2: Style = new Style(classes :+ "border-2")
  def px4: Style = new Style(classes :+ "border-4")
}
