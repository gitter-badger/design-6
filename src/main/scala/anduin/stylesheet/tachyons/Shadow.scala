// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See /vendors/tachyons-4.9.1/_shadow.css
private[tachyons] final case class Shadow(classes: List[String] = List.empty) {
  def s1: Tachyons = new Tachyons(classes :+ "shadow-1")
  def s2: Tachyons = new Tachyons(classes :+ "shadow-2")
  def s3: Tachyons = new Tachyons(classes :+ "shadow-3")
  def s4: Tachyons = new Tachyons(classes :+ "shadow-4")
}
