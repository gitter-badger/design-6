// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_vertical-align.css
private[style] final case class VerticalAlign(classes: List[String] = List.empty) {

  def base: Style = new Style(classes :+ "v-base")
  def middle: Style = new Style(classes :+ "v-mid")
  def top: Style = new Style(classes :+ "v-top")
  def bottom: Style = new Style(classes :+ "v-btm")
}
