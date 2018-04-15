// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class WordBreak(classes: List[String] = List.empty) {
  def normal: Style = new Style(classes :+ "word-normal")
  def wrap: Style = new Style(classes :+ "word-wrap")
  def noWrap: Style = new Style(classes :+ "word-nowrap")
  def break: Style = new Style(classes :+ "word-break")
}
