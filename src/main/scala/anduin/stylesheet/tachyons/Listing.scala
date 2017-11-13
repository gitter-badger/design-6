// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_lists.css
private[tachyons] final case class Listing(classes: List[String] = List.empty) {

  def list: Tachyons = new Tachyons(classes :+ "list")
}
