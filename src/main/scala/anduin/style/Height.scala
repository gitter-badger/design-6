// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_heights.css
private[style] final case class Height(classes: List[String] = List.empty) {

  def size1: Style = new Style(classes :+ "h1")
  def size2: Style = new Style(classes :+ "h2")
  def size3: Style = new Style(classes :+ "h3")
  def size4: Style = new Style(classes :+ "h4")
  def size5: Style = new Style(classes :+ "h5")

  def percent100: Style = new Style(classes :+ "h-100")
  def screenPercent100: Style = new Style(classes :+ "vh-100")
  def minScreenHeight100: Style = new Style(classes :+ "min-vh-100")

  def auto: Style = new Style(classes :+ "h-auto")
  def inherit: Style = new Style(classes :+ "h-inherit")
}
