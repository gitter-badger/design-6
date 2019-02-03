// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class MaxWidth(classes: List[String] = List.empty) {
  def px128: Style = new Style(classes :+ "max-w-px128")
  def px256: Style = new Style(classes :+ "max-w-px256")
  def px512: Style = new Style(classes :+ "max-w-px512")
  def px768: Style = new Style(classes :+ "max-w-px768")
  def px1024: Style = new Style(classes :+ "max-w-px1024")
  def pc100: Style = new Style(classes :+ "max-w-pc100")
}
