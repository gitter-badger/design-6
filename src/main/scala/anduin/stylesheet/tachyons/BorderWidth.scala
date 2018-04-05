// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

private[tachyons] final case class BorderWidth(classes: List[String] = List.empty) {
  def size0: Tachyons = new Tachyons(classes :+ "bw0")
  def size1: Tachyons = new Tachyons(classes :+ "bw1")
  def size2: Tachyons = new Tachyons(classes :+ "bw2")
  def size3: Tachyons = new Tachyons(classes :+ "bw3")

  def top0: Tachyons = new Tachyons(classes :+ "bt-0")
  def right0: Tachyons = new Tachyons(classes :+ "br-0")
  def bottom0: Tachyons = new Tachyons(classes :+ "bb-0")
  def left0: Tachyons = new Tachyons(classes :+ "bl-0")
}
