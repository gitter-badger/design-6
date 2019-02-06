// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

// scalastyle:off number.of.methods
private[style] final case class Hover(classes: List[String] = List.empty) {


  // shadow
  def shadow1Light: Style = new Style(classes :+ "hv-s1l")
  def shadow1Dark: Style = new Style(classes :+ "hv-s1d")

  // Opacity
  def opacity100: Style = new Style(classes :+ "hv-o100")
}
// scalastyle:on number.of.methods
