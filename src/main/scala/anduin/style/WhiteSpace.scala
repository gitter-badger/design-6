// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class WhiteSpace(classes: List[String] = List.empty) {
  def normal: Style = new Style(classes :+ "ws-normal")
  def noWrap: Style = new Style(classes :+ "nowrap")
  def pre: Style = new Style(classes :+ "pre")
}
