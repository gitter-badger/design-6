// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

private[tachyons] final case class Cursor(classes: List[String] = List.empty) {
  def pointer: Tachyons = new Tachyons(classes :+ "cursor-pointer")
  def help: Tachyons = new Tachyons(classes :+ "cursor-help")
}
