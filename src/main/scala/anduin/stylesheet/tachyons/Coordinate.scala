// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_coordinates.css
private[tachyons] final case class Coordinate(classes: List[String] = List.empty) {

  def top0: Tachyons = new Tachyons(classes :+ "top-0")
  def top1: Tachyons = new Tachyons(classes :+ "top-1")
  def top2: Tachyons = new Tachyons(classes :+ "top-2")
  def negativeTop1: Tachyons = new Tachyons(classes :+ "top--1")
  def negativeTop2: Tachyons = new Tachyons(classes :+ "top--2")

  def right0: Tachyons = new Tachyons(classes :+ "right-0")
  def right1: Tachyons = new Tachyons(classes :+ "right-1")
  def right2: Tachyons = new Tachyons(classes :+ "right-2")
  def negativeRight1: Tachyons = new Tachyons(classes :+ "right--1")
  def negativeRight2: Tachyons = new Tachyons(classes :+ "right--2")

  def bottom0: Tachyons = new Tachyons(classes :+ "bottom-0")
  def bottom1: Tachyons = new Tachyons(classes :+ "bottom-1")
  def bottom2: Tachyons = new Tachyons(classes :+ "bottom-2")
  def negativeBottom1: Tachyons = new Tachyons(classes :+ "bottom--1")
  def negativeBottom2: Tachyons = new Tachyons(classes :+ "bottom--2")

  def left0: Tachyons = new Tachyons(classes :+ "left-0")
  def left1: Tachyons = new Tachyons(classes :+ "left-1")
  def left2: Tachyons = new Tachyons(classes :+ "left-2")
  def negativeLeft1: Tachyons = new Tachyons(classes :+ "left--1")
  def negativeLeft2: Tachyons = new Tachyons(classes :+ "left--2")

  def absoluteFill: Tachyons = new Tachyons(classes :+ "absolute--fill")
}
