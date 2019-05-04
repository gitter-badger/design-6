// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.style

private[style] final case class Visibility(classes: List[String] = List.empty) {
  def visible: Style = new Style(classes :+ "visible")
  def invisible: Style = new Style(classes :+ "invisible")
}
