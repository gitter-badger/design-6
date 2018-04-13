// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class Listing(classes: List[String] = List.empty) {
  def list: Style = new Style(classes :+ "list")
}
