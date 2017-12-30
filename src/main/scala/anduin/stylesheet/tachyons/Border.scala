// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_borders.css
private[tachyons] final case class Border(classes: List[String] = List.empty) {

  def all: Tachyons = new Tachyons(classes :+ "ba")
  def top: Tachyons = new Tachyons(classes :+ "bt")
  def right: Tachyons = new Tachyons(classes :+ "br")
  def bottom: Tachyons = new Tachyons(classes :+ "bb")
  def left: Tachyons = new Tachyons(classes :+ "bl")
  def none: Tachyons = new Tachyons(classes :+ "bn")
}
