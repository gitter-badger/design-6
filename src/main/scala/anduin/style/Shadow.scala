// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class Shadow(classes: List[String] = List.empty) {
  def blur1Light: Style = new Style(classes :+ "s-1")
  def blur1Dark: Style = new Style(classes :+ "s-2")
  def blur8: Style = new Style(classes :+ "s-3")
  def blur12: Style = new Style(classes :+ "s-4")
}
