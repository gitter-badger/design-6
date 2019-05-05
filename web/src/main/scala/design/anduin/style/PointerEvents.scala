// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.style

private[style] final case class PointerEvents(classes: List[String] = List.empty) {
  def none: Style = new Style(classes :+ "pointer-events-none")
  def auto: Style = new Style(classes :+ "pointer-events-auto")
}
