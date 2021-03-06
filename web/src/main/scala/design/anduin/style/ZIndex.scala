// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.style

private[style] final case class ZIndex(classes: List[String] = List.empty) {
  def idx0: Style = new Style(classes :+ "z-0")
  def idx1: Style = new Style(classes :+ "z-1")
  def idx2: Style = new Style(classes :+ "z-2")
  def idx3: Style = new Style(classes :+ "z-3")
  def idx4: Style = new Style(classes :+ "z-4")
  def idx5: Style = new Style(classes :+ "z-5")
  def idx999: Style = new Style(classes :+ "z-999")
  def idx9999: Style = new Style(classes :+ "z-9999")
}
