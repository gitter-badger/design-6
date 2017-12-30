// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_heights.css
private[tachyons] final case class Height(classes: List[String] = List.empty) {

  def h1: Tachyons = new Tachyons(classes :+ "h1")
  def h2: Tachyons = new Tachyons(classes :+ "h2")
  def h3: Tachyons = new Tachyons(classes :+ "h3")
  def h4: Tachyons = new Tachyons(classes :+ "h4")
  def h5: Tachyons = new Tachyons(classes :+ "h5")

  def percent25: Tachyons = new Tachyons(classes :+ "h-25")
  def percent50: Tachyons = new Tachyons(classes :+ "h-50")
  def percent75: Tachyons = new Tachyons(classes :+ "h-75")
  def percent100: Tachyons = new Tachyons(classes :+ "h-100")

  def minHeight100: Tachyons = new Tachyons(classes :+ "min-h-100")

  def screenPercent25: Tachyons = new Tachyons(classes :+ "vh-25")
  def screenPercent50: Tachyons = new Tachyons(classes :+ "vh-50")
  def screenPercent75: Tachyons = new Tachyons(classes :+ "vh-75")
  def screenPercent100: Tachyons = new Tachyons(classes :+ "vh-100")

  def minScreenHeight100: Tachyons = new Tachyons(classes :+ "min-vh-100")

  def auto: Tachyons = new Tachyons(classes :+ "h-auto")
  def inherit: Tachyons = new Tachyons(classes :+ "h-inherit")
}
