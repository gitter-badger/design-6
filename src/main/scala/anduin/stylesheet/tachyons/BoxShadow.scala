// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_box-shadow.css
private[tachyons] final case class BoxShadow(classes: List[String] = List.empty) {

  def shadow1: Tachyons = new Tachyons(classes :+ "shadow-1")
  def shadow2: Tachyons = new Tachyons(classes :+ "shadow-2")
  def shadow3: Tachyons = new Tachyons(classes :+ "shadow-3")
  def shadow4: Tachyons = new Tachyons(classes :+ "shadow-4")
  def shadow5: Tachyons = new Tachyons(classes :+ "shadow-5")
}
