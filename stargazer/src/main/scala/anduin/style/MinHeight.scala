// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class MinHeight(classes: List[String] = List.empty) {
  def pc100: Style = new Style(classes :+ "min-h-pc100")
  def vh100: Style = new Style(classes :+ "min-h-vh100")
}
