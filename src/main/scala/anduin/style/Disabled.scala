// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class Disabled(classes: List[String] = List.empty) {
  // Color
  def colorGray6: Style = new Style(classes :+ "disabled-c--gray-6")
  // Background Color
  def backgroundGray2: Style = new Style(classes :+ "disabled-bg--gray-2")
  // Border Color
  def borderGray4: Style = new Style(classes :+ "disabled-b--gray-4")
  // Shadow
  def shadowBorderGray4: Style = new Style(classes :+ "disabled-s-b--gray-4")
}
