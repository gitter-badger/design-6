// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_border-widths.css
private[tachyons] final case class BorderWidth(classes: List[String] = List.empty) {

  def w0: Tachyons = new Tachyons(classes :+ "bw0")
  def w1: Tachyons = new Tachyons(classes :+ "bw1")
  def w2: Tachyons = new Tachyons(classes :+ "bw2")
  def w3: Tachyons = new Tachyons(classes :+ "bw3")
  def w4: Tachyons = new Tachyons(classes :+ "bw4")
  def w5: Tachyons = new Tachyons(classes :+ "bw5")

  def top0: Tachyons = new Tachyons(classes :+ "bt-0")
  def right0: Tachyons = new Tachyons(classes :+ "br-0")
  def bottom0: Tachyons = new Tachyons(classes :+ "bb-0")
  def left0: Tachyons = new Tachyons(classes :+ "bl-0")
}
