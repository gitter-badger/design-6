// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_white-space.css
private[tachyons] final case class WhiteSpace(classes: List[String] = List.empty) {

  def normal: Tachyons = new Tachyons(classes :+ "ws-normal")
  def noWrap: Tachyons = new Tachyons(classes :+ "nowrap")
  def pre: Tachyons = new Tachyons(classes :+ "pre")
}
