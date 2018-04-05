// Copyright (C) 2014-2018 Anduin Transactions Inc.
package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_border-radius.css
private[style] final case class BorderRadius(classes: List[String] = List.empty) {

  def r0: Style = new Style(classes :+ "br0")
  def r1: Style = new Style(classes :+ "br1")
  def r2: Style = new Style(classes :+ "br2")
  def r3: Style = new Style(classes :+ "br3")
  def r4: Style = new Style(classes :+ "br4")
  def r100: Style = new Style(classes :+ "br-100")
  def pill: Style = new Style(classes :+ "br-pill")
  def bottom: Style = new Style(classes :+ "br--bottom")
  def top: Style = new Style(classes :+ "br--top")
  def right: Style = new Style(classes :+ "br--right")
  def left: Style = new Style(classes :+ "br--left")
}
