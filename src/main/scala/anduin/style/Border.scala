// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class Border(classes: List[String] = List.empty) {
  def all: Style = new Style(classes :+ "ba")
  def top: Style = new Style(classes :+ "bt")
  def right: Style = new Style(classes :+ "br")
  def bottom: Style = new Style(classes :+ "bb")
  def left: Style = new Style(classes :+ "bl")
  def none: Style = new Style(classes :+ "bn")
}
