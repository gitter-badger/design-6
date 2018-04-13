// Copyright (C) 2014-2018 Anduin Transactions Inc.
package anduin.style

private[style] final case class BorderRadius(classes: List[String] = List.empty) {
  def px0: Style = new Style(classes :+ "br0")
  def px2: Style = new Style(classes :+ "br1")
  def px4: Style = new Style(classes :+ "br2")
  def px8: Style = new Style(classes :+ "br3")
  def px16: Style = new Style(classes :+ "br4")

  def pc100: Style = new Style(classes :+ "br-100")
  def pill: Style = new Style(classes :+ "br-pill")

  def bottom: Style = new Style(classes :+ "br--bottom")
  def top: Style = new Style(classes :+ "br--top")
  def right: Style = new Style(classes :+ "br--right")
  def left: Style = new Style(classes :+ "br--left")
}
