// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class Opacity(classes: List[String] = List.empty) {
  def pc0: Style = new Style(classes :+ "o-0")
  def pc20: Style = new Style(classes :+ "o-20")
  def pc50: Style = new Style(classes :+ "o-50")
  def pc100: Style = new Style(classes :+ "o-100")
}
