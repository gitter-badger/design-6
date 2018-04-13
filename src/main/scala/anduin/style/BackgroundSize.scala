// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class BackgroundSize(classes: List[String] = List.empty) {
  def cover: Style = new Style(classes :+ "cover")
  def contain: Style = new Style(classes :+ "contain")
}
