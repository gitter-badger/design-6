// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_word-break.css
private[style] final case class WordBreak(classes: List[String] = List.empty) {

  def normal: Style = new Style(classes :+ "word-normal")
  def wrap: Style = new Style(classes :+ "word-wrap")
  def noWrap: Style = new Style(classes :+ "word-nowrap")
}
