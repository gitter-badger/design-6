// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_forms.css
private[tachyons] final case class Form(classes: List[String] = List.empty) {

  def inputReset: Tachyons = new Tachyons(classes :+ "input-reset")
  def buttonReset: Tachyons = new Tachyons(classes :+ "button-reset")
}
