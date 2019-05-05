// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.style

private[style] final case class Transition(classes: List[String] = List.empty) {
  def all: Style = new Style(classes :+ "trns-a")
  def allWithOutline: Style = new Style(classes :+ "trns-o")
  def allWithShadow: Style = new Style(classes :+ "trns-s")
}
