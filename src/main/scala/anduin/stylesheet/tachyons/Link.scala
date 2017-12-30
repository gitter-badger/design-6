// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_links.css
private[tachyons] final case class Link(classes: List[String] = List.empty) {

  def link: Tachyons = new Tachyons(classes :+ "link")
}
