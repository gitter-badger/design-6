// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_text-decoration.css
private[tachyons] final case class TextDecoration(classes: List[String] = List.empty) {

  def strike: Tachyons = new Tachyons(classes :+ "strike")
  def underline: Tachyons = new Tachyons(classes :+ "underline")
  def noUnderline: Tachyons = new Tachyons(classes :+ "no-underline")
}
