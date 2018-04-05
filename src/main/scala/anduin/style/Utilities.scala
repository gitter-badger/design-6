// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_utilities.css
private[style] final case class Utilities(classes: List[String] = List.empty) {

  def overflowContainer: Style = new Style(classes :+ "overflow-container")
  def center: Style = new Style(classes :+ "center")
  def marginLeftAuto: Style = new Style(classes :+ "ml-auto")
  def marginRightAuto: Style = new Style(classes :+ "mr-auto")
}
