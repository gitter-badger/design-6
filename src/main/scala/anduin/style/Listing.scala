// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_lists.css
private[style] final case class Listing(classes: List[String] = List.empty) {

  def list: Style = new Style(classes :+ "list")
}
