// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class Height(classes: List[String] = List.empty) {
  def px8: Style = new Style(classes :+ "h-8")
  def px16: Style = new Style(classes :+ "h-16")
  def px20: Style = new Style(classes :+ "h-20")
  def px24: Style = new Style(classes :+ "h-24")
  def px32: Style = new Style(classes :+ "h-32")
  def px40: Style = new Style(classes :+ "h-40")
  def px48: Style = new Style(classes :+ "h-48")
  def px64: Style = new Style(classes :+ "h-64")
  def px128: Style = new Style(classes :+ "h-128")
  def px256: Style = new Style(classes :+ "h-256")

  def pc100: Style = new Style(classes :+ "h-100")
  def minPc100: Style = new Style(classes :+ "min-h-100")
  def vh100: Style = new Style(classes :+ "vh-100")
  def minVh100: Style = new Style(classes :+ "min-vh-100")

  def auto: Style = new Style(classes :+ "h-auto")
  def inherit: Style = new Style(classes :+ "h-inherit")
}
