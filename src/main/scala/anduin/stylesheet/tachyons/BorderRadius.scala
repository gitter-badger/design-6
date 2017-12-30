// Copyright (C) 2014-2018 Anduin Transactions Inc.
package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_border-radius.css
private[tachyons] final case class BorderRadius(classes: List[String] = List.empty) {

  def r0: Tachyons = new Tachyons(classes :+ "br0")
  def r1: Tachyons = new Tachyons(classes :+ "br1")
  def r2: Tachyons = new Tachyons(classes :+ "br2")
  def r3: Tachyons = new Tachyons(classes :+ "br3")
  def r4: Tachyons = new Tachyons(classes :+ "br4")
  def r100: Tachyons = new Tachyons(classes :+ "br-100")
  def pill: Tachyons = new Tachyons(classes :+ "br-pill")
  def bottom: Tachyons = new Tachyons(classes :+ "br--bottom")
  def top: Tachyons = new Tachyons(classes :+ "br--top")
  def right: Tachyons = new Tachyons(classes :+ "br--right")
  def left: Tachyons = new Tachyons(classes :+ "br--left")
}
