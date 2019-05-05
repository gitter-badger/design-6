// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.style

private[style] final case class Height(classes: List[String] = List.empty) {
  def px1: Style = new Style(classes :+ "h-px1")
  def px8: Style = new Style(classes :+ "h-px8")
  def px16: Style = new Style(classes :+ "h-px16")
  def px20: Style = new Style(classes :+ "h-px20")
  def px24: Style = new Style(classes :+ "h-px24")
  def px32: Style = new Style(classes :+ "h-px32")
  def px40: Style = new Style(classes :+ "h-px40")
  def px48: Style = new Style(classes :+ "h-px48")
  def px64: Style = new Style(classes :+ "h-px64")
  def px128: Style = new Style(classes :+ "h-px128")
  def px256: Style = new Style(classes :+ "h-px256")
  def pc100: Style = new Style(classes :+ "h-pc100")
  def vh100: Style = new Style(classes :+ "h-vh100")
  def auto: Style = new Style(classes :+ "h-auto")
  def minContent: Style = new Style(classes :+ "h-min")
  def maxContent: Style = new Style(classes :+ "h-max")
}
