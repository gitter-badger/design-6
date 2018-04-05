// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_font-style.css
private[style] final case class FontStyle(classes: List[String] = List.empty) {

  def italic: Style = new Style(classes :+ "i")
  def normal: Style = new Style(classes :+ "fs-normal")
}
