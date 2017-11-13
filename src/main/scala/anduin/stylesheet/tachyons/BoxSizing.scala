// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_box-sizing.css
private[tachyons] final case class BoxSizing(classes: List[String] = List.empty) {

  def borderBox: Tachyons = new Tachyons(classes :+ "border-box")
}
