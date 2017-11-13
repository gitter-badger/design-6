// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_max-widths.css
private[tachyons] final case class MaxWidth(classes: List[String] = List.empty) {

  def mw1: Tachyons = new Tachyons(classes :+ "mw1")
  def mw2: Tachyons = new Tachyons(classes :+ "mw2")
  def mw3: Tachyons = new Tachyons(classes :+ "mw3")
  def mw4: Tachyons = new Tachyons(classes :+ "mw4")
  def mw5: Tachyons = new Tachyons(classes :+ "mw5")
  def mw6: Tachyons = new Tachyons(classes :+ "mw6")
  def mw7: Tachyons = new Tachyons(classes :+ "mw7")
  def mw8: Tachyons = new Tachyons(classes :+ "mw8")
  def mw9: Tachyons = new Tachyons(classes :+ "mw9")

  def percent100: Tachyons = new Tachyons(classes :+ "mw-100")
  def none: Tachyons = new Tachyons(classes :+ "mw-none")
}
