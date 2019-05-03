// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class Outline(classes: List[String] = List.empty) {
  def focusNone: Style = new Style(classes :+ "focus:outline-none")
  def focusLight: Style = new Style(classes :+ "focus:outline-light")
  def focusDark: Style = new Style(classes :+ "focus:outline-dark")
}
