// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See /stargazer/gondor/webResources/src/main/assets/stylesheets/vendors/tachyons-4.9.1/_type-scale.css
private[tachyons] final case class FontSize(classes: List[String] = List.empty) {
  def f1: Tachyons = new Tachyons(classes :+ "f1")
  def f2: Tachyons = new Tachyons(classes :+ "f2")
  def f3: Tachyons = new Tachyons(classes :+ "f3")
  def f4: Tachyons = new Tachyons(classes :+ "f4")
  def f5: Tachyons = new Tachyons(classes :+ "f5")
  def f6: Tachyons = new Tachyons(classes :+ "f6")
  def f7: Tachyons = new Tachyons(classes :+ "f7")
}
