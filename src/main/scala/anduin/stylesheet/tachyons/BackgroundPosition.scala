// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_background-position.css
private[tachyons] final case class BackgroundPosition(classes: List[String] = List.empty) {

  def center: Tachyons = new Tachyons(classes :+ "bg-center")
  def top: Tachyons = new Tachyons(classes :+ "bg-top")
  def right: Tachyons = new Tachyons(classes :+ "bg-right")
  def bottom: Tachyons = new Tachyons(classes :+ "bg-bottom")
  def left: Tachyons = new Tachyons(classes :+ "bg-left")
}
