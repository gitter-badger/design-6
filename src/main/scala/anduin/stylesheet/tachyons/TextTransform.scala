// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_text-transform.css
private[tachyons] final case class TextTransform(classes: List[String] = List.empty) {

  def capitalize: Tachyons = new Tachyons(classes :+ "ttc")
  def lowercase: Tachyons = new Tachyons(classes :+ "ttl")
  def uppercase: Tachyons = new Tachyons(classes :+ "ttu")
  def none: Tachyons = new Tachyons(classes :+ "ttn")
}
