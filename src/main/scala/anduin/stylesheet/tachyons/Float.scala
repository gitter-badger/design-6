// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_floats.css
private[tachyons] final case class Float(classes: List[String] = List.empty) {

  def left: Tachyons = new Tachyons(classes :+ "fl")
  def right: Tachyons = new Tachyons(classes :+ "fr")
  def none: Tachyons = new Tachyons(classes :+ "fn")
}
