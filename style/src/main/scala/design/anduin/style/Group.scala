// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class Group(classes: List[String] = List.empty) {
  def group: Style = new Style(classes :+ "group")
  def visibleOnHover: Style = new Style(classes :+ "group-hover:visible")
}
