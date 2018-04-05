// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class Typography(classes: List[String] = List.empty) {
  def truncate: Style = new Style(classes :+ "truncate")
}
