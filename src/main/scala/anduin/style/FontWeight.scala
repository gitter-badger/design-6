// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_font-weight.css
private[style] final case class FontWeight(classes: List[String] = List.empty) {

  def size100: Style = new Style(classes :+ "fw1")
  def size200: Style = new Style(classes :+ "fw2")
  def size300: Style = new Style(classes :+ "fw3")
  def size400: Style = new Style(classes :+ "fw4")
  def size500: Style = new Style(classes :+ "fw5")
  def size600: Style = new Style(classes :+ "fw6")
  def size700: Style = new Style(classes :+ "fw7")
}
