// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

// scalastyle:off number.of.methods
private[style] final case class BorderColor(classes: List[String] = List.empty) {
  // normal
  def transparent: Style = new Style(classes :+ "border-transparent")
  def currentColor: Style = new Style(classes :+ "border-current")
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
  def blue5: Style = new Style(classes :+ "border-blue-5")
  def blue4: Style = new Style(classes :+ "border-blue-4")
  def blue3: Style = new Style(classes :+ "border-blue-3")
  def blue2: Style = new Style(classes :+ "border-blue-2")
  def blue1: Style = new Style(classes :+ "border-blue-1")
  def green5: Style = new Style(classes :+ "border-green-5")
  def green4: Style = new Style(classes :+ "border-green-4")
  def green3: Style = new Style(classes :+ "border-green-3")
  def green2: Style = new Style(classes :+ "border-green-2")
  def green1: Style = new Style(classes :+ "border-green-1")
  def orange5: Style = new Style(classes :+ "border-orange-5")
  def orange4: Style = new Style(classes :+ "border-orange-4")
  def orange3: Style = new Style(classes :+ "border-orange-3")
  def orange2: Style = new Style(classes :+ "border-orange-2")
  def orange1: Style = new Style(classes :+ "border-orange-1")
  def red5: Style = new Style(classes :+ "border-red-5")
  def red4: Style = new Style(classes :+ "border-red-4")
  def red3: Style = new Style(classes :+ "border-red-3")
  def red2: Style = new Style(classes :+ "border-red-2")
  def red1: Style = new Style(classes :+ "border-red-1")
  // hover
  def hoverGray3: Style = new Style(classes :+ "hover:border-gray-3")
  def hoverGray4: Style = new Style(classes :+ "hover:border-gray-4")
  def hoverGray5: Style = new Style(classes :+ "hover:border-gray-5")
  def hoverBlue5: Style = new Style(classes :+ "hover:border-blue-5")
  def hoverBlue3: Style = new Style(classes :+ "hover:border-blue-3")
  def hoverBlue2: Style = new Style(classes :+ "hover:border-blue-2")
  def hoverGreen5: Style = new Style(classes :+ "hover:border-green-5")
  def hoverOrange5: Style = new Style(classes :+ "hover:border-orange-5")
  def hoverRed5: Style = new Style(classes :+ "hover:border-red-5")
  // active
  def activeGray4: Style = new Style(classes :+ "active:border-gray-4")
  def activeGray5: Style = new Style(classes :+ "active:border-gray-5")
  def activeGray7: Style = new Style(classes :+ "active:border-gray-7")
  def activeBlue5: Style = new Style(classes :+ "active:border-blue-5")
  def activeGreen5: Style = new Style(classes :+ "active:border-green-5")
  def activeOrange5: Style = new Style(classes :+ "active:border-orange-5")
  def activeRed5: Style = new Style(classes :+ "active:border-red-5")
  // focus
  def focusBlue4: Style = new Style(classes :+ "focus:border-blue-4")
}
// scalastyle:on number.of.methods
