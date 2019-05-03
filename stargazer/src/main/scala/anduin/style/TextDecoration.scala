// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class TextDecoration(classes: List[String] = List.empty) {
  def hoverNone: Style = new Style(classes :+ "hover:text-none")
  def hoverUnderline: Style = new Style(classes :+ "hover:text-underline")
}
