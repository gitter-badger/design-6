// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_visibility.css
private[tachyons] final case class Visibility(classes: List[String] = List.empty) {

  def clip: Tachyons = new Tachyons(classes :+ "clip")
}
