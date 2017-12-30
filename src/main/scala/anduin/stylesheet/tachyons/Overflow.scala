// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_overflow.css
private[tachyons] final case class Overflow(classes: List[String] = List.empty) {

  def visible: Tachyons = new Tachyons(classes :+ "overflow-visible")
  def hidden: Tachyons = new Tachyons(classes :+ "overflow-hidden")
  def scroll: Tachyons = new Tachyons(classes :+ "overflow-scroll")
  def auto: Tachyons = new Tachyons(classes :+ "overflow-auto")

  def visibleX: Tachyons = new Tachyons(classes :+ "overflow-x-visible")
  def hiddenX: Tachyons = new Tachyons(classes :+ "overflow-x-hidden")
  def scrollX: Tachyons = new Tachyons(classes :+ "overflow-x-scroll")
  def autoX: Tachyons = new Tachyons(classes :+ "overflow-x-auto")

  def visibleY: Tachyons = new Tachyons(classes :+ "overflow-y-visible")
  def hiddenY: Tachyons = new Tachyons(classes :+ "overflow-y-hidden")
  def scrollY: Tachyons = new Tachyons(classes :+ "overflow-y-scroll")
  def autoY: Tachyons = new Tachyons(classes :+ "overflow-y-auto")
}
