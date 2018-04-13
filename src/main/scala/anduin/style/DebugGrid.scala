// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class DebugGrid(classes: List[String] = List.empty) {
  def grid: Style = new Style(classes :+ "debug-grid")
  def grid16: Style = new Style(classes :+ "debug-grid-16")
  def gridSolid8: Style = new Style(classes :+ "debug-grid-8-solid")
  def gridSolid16: Style = new Style(classes :+ "debug-grid-16-solid")
}
