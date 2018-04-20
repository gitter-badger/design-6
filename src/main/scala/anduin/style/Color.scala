// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// scalastyle:off number.of.methods
private[style] final case class Color(classes: List[String] = List.empty) {

  def gray9: Style = new Style(classes :+ "c--gray-9")
  def gray8: Style = new Style(classes :+ "c--gray-8")
  def gray7: Style = new Style(classes :+ "c--gray-7")
  def gray6: Style = new Style(classes :+ "c--gray-6")
  def gray5: Style = new Style(classes :+ "c--gray-5")
  def gray4: Style = new Style(classes :+ "c--gray-4")
  def gray3: Style = new Style(classes :+ "c--gray-3")
  def gray2: Style = new Style(classes :+ "c--gray-2")
  def gray1: Style = new Style(classes :+ "c--gray-1")

  def white: Style = new Style(classes :+ "c--white")

  def primary5: Style = new Style(classes :+ "c--primary-5")
  def primary4: Style = new Style(classes :+ "c--primary-4")
  def primary3: Style = new Style(classes :+ "c--primary-3")
  def primary2: Style = new Style(classes :+ "c--primary-2")
  def primary1: Style = new Style(classes :+ "c--primary-1")

  def success5: Style = new Style(classes :+ "c--success-5")
  def success4: Style = new Style(classes :+ "c--success-4")
  def success3: Style = new Style(classes :+ "c--success-3")
  def success2: Style = new Style(classes :+ "c--success-2")
  def success1: Style = new Style(classes :+ "c--success-1")

  def warning5: Style = new Style(classes :+ "c--warning-5")
  def warning4: Style = new Style(classes :+ "c--warning-4")
  def warning3: Style = new Style(classes :+ "c--warning-3")
  def warning2: Style = new Style(classes :+ "c--warning-2")
  def warning1: Style = new Style(classes :+ "c--warning-1")

  def danger5: Style = new Style(classes :+ "c--danger-5")
  def danger4: Style = new Style(classes :+ "c--danger-4")
  def danger3: Style = new Style(classes :+ "c--danger-3")
  def danger2: Style = new Style(classes :+ "c--danger-2")
  def danger1: Style = new Style(classes :+ "c--danger-1")

  def inherit: Style = new Style(classes :+ "c--inherit")
}
