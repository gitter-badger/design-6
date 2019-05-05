// Copyright (C) 2014-2019 Anduin Transactions Inc.
package design.anduin.style

private[style] final case class BorderRadius(classes: List[String] = List.empty) {
  def px0: Style = new Style(classes :+ "rounded-0")
  def px2: Style = new Style(classes :+ "rounded-2")
  def px4: Style = new Style(classes :+ "rounded-4")
  def pill: Style = new Style(classes :+ "rounded-full")
  def bottom: Style = new Style(classes :+ "rounded-bottom")
  def top: Style = new Style(classes :+ "rounded-top")
  def right: Style = new Style(classes :+ "rounded-right")
  def left: Style = new Style(classes :+ "rounded-left")
}
