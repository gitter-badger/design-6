// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class Overflow(classes: List[String] = List.empty) {
  def visible: Style = new Style(classes :+ "overflow-visible")
  def hidden: Style = new Style(classes :+ "overflow-hidden")
  def scroll: Style = new Style(classes :+ "overflow-scroll")
  def auto: Style = new Style(classes :+ "overflow-auto")

  def visibleX: Style = new Style(classes :+ "overflow-x-visible")
  def hiddenX: Style = new Style(classes :+ "overflow-x-hidden")
  def scrollX: Style = new Style(classes :+ "overflow-x-scroll")
  def autoX: Style = new Style(classes :+ "overflow-x-auto")

  def visibleY: Style = new Style(classes :+ "overflow-y-visible")
  def hiddenY: Style = new Style(classes :+ "overflow-y-hidden")
  def scrollY: Style = new Style(classes :+ "overflow-y-scroll")
  def autoY: Style = new Style(classes :+ "overflow-y-auto")
}
