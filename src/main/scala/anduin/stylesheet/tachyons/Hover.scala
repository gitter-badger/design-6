// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_hovers.css
private[tachyons] final case class Hover(classes: List[String] = List.empty) {

  def dim: Tachyons = new Tachyons(classes :+ "dim")
  def glow: Tachyons = new Tachyons(classes :+ "glow")
  def hideChild: Tachyons = new Tachyons(classes :+ "hide-child")
  def underline: Tachyons = new Tachyons(classes :+ "underline-hover")
  def grow: Tachyons = new Tachyons(classes :+ "grow")
  def growLarge: Tachyons = new Tachyons(classes :+ "grow-large")
  def pointer: Tachyons = new Tachyons(classes :+ "pointer")
  def shadow: Tachyons = new Tachyons(classes :+ "shadow-hover")
  def backgroundAnimate: Tachyons = new Tachyons(classes :+ "bg-animate")
}
