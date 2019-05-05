// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.style

private[style] final case class OverflowWrap(classes: List[String] = List.empty) {
  def breakWord: Style = new Style(classes :+ "break-words")
}
