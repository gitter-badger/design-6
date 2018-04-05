// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_text-align.css
private[style] final case class TextAlign(classes: List[String] = List.empty) {

  def left: Style = new Style(classes :+ "tl")
  def right: Style = new Style(classes :+ "tr")
  def center: Style = new Style(classes :+ "tc")
  def justify: Style = new Style(classes :+ "tj")
}
