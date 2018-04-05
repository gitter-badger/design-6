// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_z-index.css
private[style] final case class Zindex(classes: List[String] = List.empty) {

  def z0: Style = new Style(classes :+ "z-0")
  def z1: Style = new Style(classes :+ "z-1")
  def z2: Style = new Style(classes :+ "z-2")
  def z3: Style = new Style(classes :+ "z-3")
  def z4: Style = new Style(classes :+ "z-4")
  def z5: Style = new Style(classes :+ "z-5")
  def z999: Style = new Style(classes :+ "z-999")
  def z9999: Style = new Style(classes :+ "z-9999")
  def zMax: Style = new Style(classes :+ "z-max")
  def inherit: Style = new Style(classes :+ "z-inherit")
  def initial: Style = new Style(classes :+ "z-initial")
  def unset: Style = new Style(classes :+ "z-unset")
}
