// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_font-style.css
private[tachyons] final case class FontStyle(classes: List[String] = List.empty) {

  def italic: Tachyons = new Tachyons(classes :+ "i")
  def normal: Tachyons = new Tachyons(classes :+ "fs-normal")
}
