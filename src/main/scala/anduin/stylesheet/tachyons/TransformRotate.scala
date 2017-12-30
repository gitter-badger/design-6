// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_rotations.css
private[tachyons] final case class TransformRotate(classes: List[String] = List.empty) {

  def rotate45: Tachyons = new Tachyons(classes :+ "rotate-45")
  def rotate90: Tachyons = new Tachyons(classes :+ "rotate-90")
  def rotate135: Tachyons = new Tachyons(classes :+ "rotate-135")
  def rotate180: Tachyons = new Tachyons(classes :+ "rotate-180")
  def rotate225: Tachyons = new Tachyons(classes :+ "rotate-225")
  def rotate270: Tachyons = new Tachyons(classes :+ "rotate-270")
  def rotate315: Tachyons = new Tachyons(classes :+ "rotate-315")
}
