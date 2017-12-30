// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_utilities.css
private[tachyons] final case class Utilities(classes: List[String] = List.empty) {

  def overflowContainer: Tachyons = new Tachyons(classes :+ "overflow-container")
  def center: Tachyons = new Tachyons(classes :+ "center")
  def marginLeftAuto: Tachyons = new Tachyons(classes :+ "ml-auto")
  def marginRightAuto: Tachyons = new Tachyons(classes :+ "mr-auto")
}
