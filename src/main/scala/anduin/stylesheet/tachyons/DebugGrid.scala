// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_debug-grid.css
private[tachyons] final case class DebugGrid(classes: List[String] = List.empty) {

  def grid: Tachyons = new Tachyons(classes :+ "debug-grid")
  def grid16: Tachyons = new Tachyons(classes :+ "debug-grid-16")
  def gridSolid8: Tachyons = new Tachyons(classes :+ "debug-grid-8-solid")
  def gridSolid16: Tachyons = new Tachyons(classes :+ "debug-grid-16-solid")
}
