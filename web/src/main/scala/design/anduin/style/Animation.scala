// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.style

private[style] final case class Animation(classes: List[String] = List.empty) {
  def translateX330: Style = new Style(classes :+ "an-tx330")
  def circle: Style = new Style(classes :+ "an-circle")
}
