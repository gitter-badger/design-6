// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

// scalastyle:off number.of.methods
private[style] final case class Active(classes: List[String] = List.empty) {

  // shadow
  def shadow1Light: Style = new Style(classes :+ "a-s1l")
  def shadow1Dark: Style = new Style(classes :+ "a-s1d")
}
// scalastyle:on number.of.methods
