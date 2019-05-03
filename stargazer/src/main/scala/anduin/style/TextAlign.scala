// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class TextAlign(classes: List[String] = List.empty) {
  def left: Style = new Style(classes :+ "text-left")
  def right: Style = new Style(classes :+ "text-right")
  def center: Style = new Style(classes :+ "text-center")
  def justify: Style = new Style(classes :+ "text-justify")
}
