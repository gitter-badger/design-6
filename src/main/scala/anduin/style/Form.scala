// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_forms.css
private[style] final case class Form(classes: List[String] = List.empty) {

  def inputReset: Style = new Style(classes :+ "input-reset")
  def buttonReset: Style = new Style(classes :+ "button-reset")
}
