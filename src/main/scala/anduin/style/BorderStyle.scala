// Copyright (C) 2014-2018 Anduin Transactions Inc.
package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_border-style.css
private[style] final case class BorderStyle(classes: List[String] = List.empty) {

  def dotted: Style = new Style(classes :+ "b--dotted")
  def dashed: Style = new Style(classes :+ "b--dashed")
  def solid: Style = new Style(classes :+ "b--solid")
  def none: Style = new Style(classes :+ "b--none")
}
