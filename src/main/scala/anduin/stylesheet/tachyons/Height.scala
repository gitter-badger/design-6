// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_heights.css
private[tachyons] final case class Height(classes: List[String] = List.empty) {

  def size1: Tachyons = new Tachyons(classes :+ "h1")
  def size2: Tachyons = new Tachyons(classes :+ "h2")
  def size3: Tachyons = new Tachyons(classes :+ "h3")
  def size4: Tachyons = new Tachyons(classes :+ "h4")
  def size5: Tachyons = new Tachyons(classes :+ "h5")

  def percent100: Tachyons = new Tachyons(classes :+ "h-100")
  def screenPercent100: Tachyons = new Tachyons(classes :+ "vh-100")
  def minScreenHeight100: Tachyons = new Tachyons(classes :+ "min-vh-100")

  def auto: Tachyons = new Tachyons(classes :+ "h-auto")
  def inherit: Tachyons = new Tachyons(classes :+ "h-inherit")
}
