// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class Disabled(classes: List[String] = List.empty) {
  // Color
  def colorGray6: Style = new Style(classes :+ "d-cg6")
  // Background Color
  def backgroundGray2: Style = new Style(classes :+ "d-bgg2")
  def backgroundNone: Style = new Style(classes :+ "d-bgn")
  // Border Color
  def borderGray4: Style = new Style(classes :+ "d-bcg4")
  // Shadow
  def shadowNone: Style = new Style(classes :+ "d-sn")
}
