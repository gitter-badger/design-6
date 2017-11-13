// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_background-size.css
private[tachyons] final case class BackgroundSize(classes: List[String] = List.empty) {

  def cover: Tachyons = new Tachyons(classes :+ "cover")
  def contain: Tachyons = new Tachyons(classes :+ "contain")
}
