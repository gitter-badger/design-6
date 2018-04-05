// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_font-weight.css
private[tachyons] final case class FontWeight(classes: List[String] = List.empty) {

  def size100: Tachyons = new Tachyons(classes :+ "fw1")
  def size200: Tachyons = new Tachyons(classes :+ "fw2")
  def size300: Tachyons = new Tachyons(classes :+ "fw3")
  def size400: Tachyons = new Tachyons(classes :+ "fw4")
  def size500: Tachyons = new Tachyons(classes :+ "fw5")
  def size600: Tachyons = new Tachyons(classes :+ "fw6")
  def size700: Tachyons = new Tachyons(classes :+ "fw7")
}
