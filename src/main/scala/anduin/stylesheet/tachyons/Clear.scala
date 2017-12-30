// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_clears.css
private[tachyons] final case class Clear(classes: List[String] = List.empty) {

  def left: Tachyons = new Tachyons(classes :+ "cl")
  def right: Tachyons = new Tachyons(classes :+ "cr")
  def both: Tachyons = new Tachyons(classes :+ "cb")
  def none: Tachyons = new Tachyons(classes :+ "cn")
  def fix: Tachyons = new Tachyons(classes :+ "cf")
}
