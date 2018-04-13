// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class Height(classes: List[String] = List.empty) {
  def px16: Style = new Style(classes :+ "h1")
  def px32: Style = new Style(classes :+ "h2")
  def px64: Style = new Style(classes :+ "h3")
  def px128: Style = new Style(classes :+ "h4")
  def px256: Style = new Style(classes :+ "h5")

  def pc100: Style = new Style(classes :+ "h-100")
  def minPc100: Style = new Style(classes :+ "min-h-100")
  def vh100: Style = new Style(classes :+ "vh-100")
  def minVh100: Style = new Style(classes :+ "min-vh-100")

  def auto: Style = new Style(classes :+ "h-auto")
  def inherit: Style = new Style(classes :+ "h-inherit")
}
