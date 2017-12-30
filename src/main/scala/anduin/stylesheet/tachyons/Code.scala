// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_code.css
private[tachyons] final case class Code(classes: List[String] = List.empty) {

  def pre: Tachyons = new Tachyons(classes :+ "pre")
}
