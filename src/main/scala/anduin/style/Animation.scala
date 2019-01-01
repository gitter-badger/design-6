// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class Animation(classes: List[String] = List.empty) {
  def translateX330: Style = new Style(classes :+ "an-tx330")
  def ripple: Style = new Style(classes :+ "an-ripple")
  def circle: Style = new Style(classes :+ "an-circle")
}
