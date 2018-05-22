// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class PointerEvents(classes: List[String] = List.empty) {
  def none: Style = new Style(classes :+ "pe-n")
}
