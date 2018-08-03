// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// scalastyle:off number.of.methods
private[style] final case class BorderColor(classes: List[String] = List.empty) {

  def gray9: Style = new Style(classes :+ "b--gray-9")
  def gray8: Style = new Style(classes :+ "b--gray-8")
  def gray7: Style = new Style(classes :+ "b--gray-7")
  def gray6: Style = new Style(classes :+ "b--gray-6")
  def gray5: Style = new Style(classes :+ "b--gray-5")
  def gray4: Style = new Style(classes :+ "b--gray-4")
  def gray3: Style = new Style(classes :+ "b--gray-3")
  def gray2: Style = new Style(classes :+ "b--gray-2")
  def gray1: Style = new Style(classes :+ "b--gray-1")

  def white: Style = new Style(classes :+ "b--white")

  def primary5: Style = new Style(classes :+ "b--primary-5")
  def primary4: Style = new Style(classes :+ "b--primary-4")
  def primary3: Style = new Style(classes :+ "b--primary-3")
  def primary2: Style = new Style(classes :+ "b--primary-2")
  def primary1: Style = new Style(classes :+ "b--primary-1")

  def success5: Style = new Style(classes :+ "b--success-5")
  def success4: Style = new Style(classes :+ "b--success-4")
  def success3: Style = new Style(classes :+ "b--success-3")
  def success2: Style = new Style(classes :+ "b--success-2")
  def success1: Style = new Style(classes :+ "b--success-1")

  def warning5: Style = new Style(classes :+ "b--warning-5")
  def warning4: Style = new Style(classes :+ "b--warning-4")
  def warning3: Style = new Style(classes :+ "b--warning-3")
  def warning2: Style = new Style(classes :+ "b--warning-2")
  def warning1: Style = new Style(classes :+ "b--warning-1")

  def danger5: Style = new Style(classes :+ "b--danger-5")
  def danger4: Style = new Style(classes :+ "b--danger-4")
  def danger3: Style = new Style(classes :+ "b--danger-3")
  def danger2: Style = new Style(classes :+ "b--danger-2")
  def danger1: Style = new Style(classes :+ "b--danger-1")

  def purple5: Style = new Style(classes :+ "b--purple-5")
  def purple4: Style = new Style(classes :+ "b--purple-4")
  def purple3: Style = new Style(classes :+ "b--purple-3")
  def purple2: Style = new Style(classes :+ "b--purple-2")
  def purple1: Style = new Style(classes :+ "b--purple-1")

  def transparent: Style = new Style(classes :+ "b--transparent")
  def inherit: Style = new Style(classes :+ "b--inherit")
}
// scalastyle:on number.of.methods
