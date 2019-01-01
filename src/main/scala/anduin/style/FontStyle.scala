// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class FontStyle(classes: List[String] = List.empty) {
  def italic: Style = new Style(classes :+ "i")
  def normal: Style = new Style(classes :+ "fs-normal")
}
