// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_max-widths.css
private[style] final case class MaxWidth(classes: List[String] = List.empty) {

  def mw1: Style = new Style(classes :+ "mw1")
  def mw2: Style = new Style(classes :+ "mw2")
  def mw3: Style = new Style(classes :+ "mw3")
  def mw4: Style = new Style(classes :+ "mw4")
  def mw5: Style = new Style(classes :+ "mw5")
  def mw6: Style = new Style(classes :+ "mw6")
  def mw7: Style = new Style(classes :+ "mw7")
  def mw8: Style = new Style(classes :+ "mw8")
  def mw9: Style = new Style(classes :+ "mw9")

  def percent100: Style = new Style(classes :+ "mw-100")
  def none: Style = new Style(classes :+ "mw-none")
}
