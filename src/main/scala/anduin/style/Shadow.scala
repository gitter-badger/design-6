// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

private[style] final case class Shadow(classes: List[String] = List.empty) {
  def blur1Light: Style = new Style(classes :+ "s-1")
  def blur1Dark: Style = new Style(classes :+ "s-2")
  def blur8: Style = new Style(classes :+ "s-3")
  def blur12: Style = new Style(classes :+ "s-4")

  def borderGray4s: Style = new Style(classes :+ "s-bg4s")
  def borderPrimary5s: Style = new Style(classes :+ "s-bp5s")
  def borderSuccess5s: Style = new Style(classes :+ "s-bs5s")
  def borderWarning5s: Style = new Style(classes :+ "s-bw5s")
  def borderDanger5s: Style = new Style(classes :+ "s-bd5s")
}
