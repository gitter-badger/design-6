// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See /stargazer/gondor/webResources/src/main/assets/stylesheets/vendors/tachyons-4.9.1/_line-height.css
private[style] final case class LineHeight(classes: List[String] = List.empty) {
  def size1: Style = new Style(classes :+ "lh-1")
  def size2: Style = new Style(classes :+ "lh-2")
  def size3: Style = new Style(classes :+ "lh-3")
  def copy: Style = new Style(classes :+ "lh-copy")
}
