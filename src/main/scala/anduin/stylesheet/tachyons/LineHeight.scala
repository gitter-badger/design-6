// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_line-height.css
private[tachyons] final case class LineHeight(classes: List[String] = List.empty) {

  def solid: Tachyons = new Tachyons(classes :+ "lh-solid")
  def title: Tachyons = new Tachyons(classes :+ "lh-title")
  def copy: Tachyons = new Tachyons(classes :+ "lh-copy")
}
