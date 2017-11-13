// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_position.css
private[tachyons] final case class Position(classes: List[String] = List.empty) {

  def fixed: Tachyons = new Tachyons(classes :+ "fixed")
  def absolute: Tachyons = new Tachyons(classes :+ "absolute")
  def static: Tachyons = new Tachyons(classes :+ "static")
  def relative: Tachyons = new Tachyons(classes :+ "relative")
}
