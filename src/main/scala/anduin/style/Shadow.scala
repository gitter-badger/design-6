// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See /vendors/tachyons-4.9.1/_shadow.css
private[style] final case class Shadow(classes: List[String] = List.empty) {
  def s1: Style = new Style(classes :+ "shadow-1")
  def s2: Style = new Style(classes :+ "shadow-2")
  def s3: Style = new Style(classes :+ "shadow-3")
  def s4: Style = new Style(classes :+ "shadow-4")
}
