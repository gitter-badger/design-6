// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See /stargazer/gondor/webResources/src/main/assets/stylesheets/vendors/tachyons-4.9.1/_line-height.css
private[tachyons] final case class LineHeight(classes: List[String] = List.empty) {
  def l1: Tachyons = new Tachyons(classes :+ "lh-1")
  def l2: Tachyons = new Tachyons(classes :+ "lh-2")
  def l3: Tachyons = new Tachyons(classes :+ "lh-3")
  def copy: Tachyons = new Tachyons(classes :+ "lh-copy")
}
