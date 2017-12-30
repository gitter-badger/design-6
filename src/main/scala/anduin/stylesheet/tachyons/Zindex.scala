// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_z-index.css
private[tachyons] final case class Zindex(classes: List[String] = List.empty) {

  def z0: Tachyons = new Tachyons(classes :+ "z-0")
  def z1: Tachyons = new Tachyons(classes :+ "z-1")
  def z2: Tachyons = new Tachyons(classes :+ "z-2")
  def z3: Tachyons = new Tachyons(classes :+ "z-3")
  def z4: Tachyons = new Tachyons(classes :+ "z-4")
  def z5: Tachyons = new Tachyons(classes :+ "z-5")
  def z999: Tachyons = new Tachyons(classes :+ "z-999")
  def z9999: Tachyons = new Tachyons(classes :+ "z-9999")
  def zMax: Tachyons = new Tachyons(classes :+ "z-max")
  def inherit: Tachyons = new Tachyons(classes :+ "z-inherit")
  def initial: Tachyons = new Tachyons(classes :+ "z-initial")
  def unset: Tachyons = new Tachyons(classes :+ "z-unset")
}
