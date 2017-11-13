// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_vertical-align.css
private[tachyons] final case class VerticalAlign(classes: List[String] = List.empty) {

  def base: Tachyons = new Tachyons(classes :+ "v-base")
  def middle: Tachyons = new Tachyons(classes :+ "v-mid")
  def top: Tachyons = new Tachyons(classes :+ "v-top")
  def bottom: Tachyons = new Tachyons(classes :+ "v-btm")
}
