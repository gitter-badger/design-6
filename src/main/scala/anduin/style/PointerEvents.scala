// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class PointerEvents(classes: List[String] = List.empty) {
  def none: Style = new Style(classes :+ "pe-n")
  def all: Style = new Style(classes :+ "pe-a")
}
