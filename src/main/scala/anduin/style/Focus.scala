// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class Focus(classes: List[String] = List.empty) {
  def outline: Style = new Style(classes :+ "fc-o")
}
