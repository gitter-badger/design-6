// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

private[tachyons] final case class Typography(classes: List[String] = List.empty) {
  def truncate: Tachyons = new Tachyons(classes :+ "truncate")
}
