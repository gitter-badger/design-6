// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class Shadow(classes: List[String] = List.empty) {
  def px1Light: Style = new Style(classes :+ "shadow-1-light")
  def px1Dark: Style = new Style(classes :+ "shadow-1-dark")
  def px4: Style = new Style(classes :+ "shadow-4")
  def px8: Style = new Style(classes :+ "shadow-8")
  def px12: Style = new Style(classes :+ "shadow-12")
  def hoverPx1Light: Style = new Style(classes :+ "hover:shadow-1-dark")
  def hoverPx1Dark: Style = new Style(classes :+ "hover:shadow-1-light")
  def focusSpread: Style = new Style(classes :+ "focus:shadow-spread")
}
