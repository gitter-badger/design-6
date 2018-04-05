// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_text-transform.css
private[style] final case class TextTransform(classes: List[String] = List.empty) {

  def capitalize: Style = new Style(classes :+ "ttc")
  def lowercase: Style = new Style(classes :+ "ttl")
  def uppercase: Style = new Style(classes :+ "ttu")
  def none: Style = new Style(classes :+ "ttn")
}
