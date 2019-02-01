// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

// scalastyle:off number.of.methods
private[style] final case class BorderColor(classes: List[String] = List.empty) {
  // normal
  def transparent: Style = new Style(classes :+ "border-transparent")
  def gray9: Style = new Style(classes :+ "border-gray-9")
  def gray8: Style = new Style(classes :+ "border-gray-8")
  def gray7: Style = new Style(classes :+ "border-gray-7")
  def gray6: Style = new Style(classes :+ "border-gray-6")
  def gray5: Style = new Style(classes :+ "border-gray-5")
  def gray4: Style = new Style(classes :+ "border-gray-4")
  def gray3: Style = new Style(classes :+ "border-gray-3")
  def gray2: Style = new Style(classes :+ "border-gray-2")
  def gray1: Style = new Style(classes :+ "border-gray-1")
  def white: Style = new Style(classes :+ "border-white")
  def primary5: Style = new Style(classes :+ "border-blue-5")
  def primary4: Style = new Style(classes :+ "border-blue-4")
  def primary3: Style = new Style(classes :+ "border-blue-3")
  def primary2: Style = new Style(classes :+ "border-blue-2")
  def primary1: Style = new Style(classes :+ "border-blue-1")
  def success5: Style = new Style(classes :+ "border-green-5")
  def success4: Style = new Style(classes :+ "border-green-4")
  def success3: Style = new Style(classes :+ "border-green-3")
  def success2: Style = new Style(classes :+ "border-green-2")
  def success1: Style = new Style(classes :+ "border-green-1")
  def warning5: Style = new Style(classes :+ "border-orange-5")
  def warning4: Style = new Style(classes :+ "border-orange-4")
  def warning3: Style = new Style(classes :+ "border-orange-3")
  def warning2: Style = new Style(classes :+ "border-orange-2")
  def warning1: Style = new Style(classes :+ "border-orange-1")
  def danger5: Style = new Style(classes :+ "border-red-5")
  def danger4: Style = new Style(classes :+ "border-red-4")
  def danger3: Style = new Style(classes :+ "border-red-3")
  def danger2: Style = new Style(classes :+ "border-red-2")
  def danger1: Style = new Style(classes :+ "border-red-1")
}
// scalastyle:on number.of.methods
