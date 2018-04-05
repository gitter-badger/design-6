// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See /stargazer/gondor/webResources/src/main/assets/stylesheets/vendors/tachyons-4.9.1/_type-scale.css
private[style] final case class FontSize(classes: List[String] = List.empty) {
  def size1: Style = new Style(classes :+ "f1")
  def size2: Style = new Style(classes :+ "f2")
  def size3: Style = new Style(classes :+ "f3")
  def size4: Style = new Style(classes :+ "f4")
  def size5: Style = new Style(classes :+ "f5")
  def size6: Style = new Style(classes :+ "f6")
  def size7: Style = new Style(classes :+ "f7")
}
