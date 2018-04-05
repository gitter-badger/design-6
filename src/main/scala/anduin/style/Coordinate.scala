// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_coordinates.css
private[style] final case class Coordinate(classes: List[String] = List.empty) {

  def top0: Style = new Style(classes :+ "top-0")
  def top1: Style = new Style(classes :+ "top-1")
  def top2: Style = new Style(classes :+ "top-2")
  def negativeTop1: Style = new Style(classes :+ "top--1")
  def negativeTop2: Style = new Style(classes :+ "top--2")

  def right0: Style = new Style(classes :+ "right-0")
  def right1: Style = new Style(classes :+ "right-1")
  def right2: Style = new Style(classes :+ "right-2")
  def negativeRight1: Style = new Style(classes :+ "right--1")
  def negativeRight2: Style = new Style(classes :+ "right--2")

  def bottom0: Style = new Style(classes :+ "bottom-0")
  def bottom1: Style = new Style(classes :+ "bottom-1")
  def bottom2: Style = new Style(classes :+ "bottom-2")
  def negativeBottom1: Style = new Style(classes :+ "bottom--1")
  def negativeBottom2: Style = new Style(classes :+ "bottom--2")

  def left0: Style = new Style(classes :+ "left-0")
  def left1: Style = new Style(classes :+ "left-1")
  def left2: Style = new Style(classes :+ "left-2")
  def negativeLeft1: Style = new Style(classes :+ "left--1")
  def negativeLeft2: Style = new Style(classes :+ "left--2")

  def absoluteFill: Style = new Style(classes :+ "absolute--fill")
}
