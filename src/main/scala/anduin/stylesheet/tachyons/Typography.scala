// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_typography.css
private[tachyons] final case class Typography(classes: List[String] = List.empty) {

  def measure: Tachyons = new Tachyons(classes :+ "measure")
  def measureWide: Tachyons = new Tachyons(classes :+ "measure-wide")
  def measureNarrow: Tachyons = new Tachyons(classes :+ "measure-narrow")
  def indent: Tachyons = new Tachyons(classes :+ "indent")
  def smallCaps: Tachyons = new Tachyons(classes :+ "small-caps")
  def truncate: Tachyons = new Tachyons(classes :+ "truncate")
}
