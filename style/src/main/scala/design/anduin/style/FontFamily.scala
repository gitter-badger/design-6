// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_font-family.css
private[style] final case class FontFamily(classes: List[String] = List.empty) {
  def sans: Style = new Style(classes :+ "font-sans")
  def mono: Style = new Style(classes :+ "font-mono")
}
