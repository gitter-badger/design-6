// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class Table(classes: List[String] = List.empty) {
  def collapse: Style = new Style(classes :+ "tb-c")
  def fixed: Style = new Style(classes :+ "tb-f")
  def stripedGray2: Style = new Style(classes :+ "striped--gray-2")
}
