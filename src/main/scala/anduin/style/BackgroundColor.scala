// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_skins.css
// scalastyle:off number.of.methods
private[style] final case class BackgroundColor(classes: List[String] = List.empty) {

  def gray9: Style = new Style(classes :+ "bg-gray-9")
  def gray8: Style = new Style(classes :+ "bg-gray-8")
  def gray7: Style = new Style(classes :+ "bg-gray-7")
  def gray6: Style = new Style(classes :+ "bg-gray-6")
  def gray5: Style = new Style(classes :+ "bg-gray-5")
  def gray4: Style = new Style(classes :+ "bg-gray-4")
  def gray3: Style = new Style(classes :+ "bg-gray-3")
  def gray2: Style = new Style(classes :+ "bg-gray-2")
  def gray1: Style = new Style(classes :+ "bg-gray-1")

  def white: Style = new Style(classes :+ "bg-white")

  def blue5: Style = new Style(classes :+ "bg-blue-5")
  def blue4: Style = new Style(classes :+ "bg-blue-4")
  def blue3: Style = new Style(classes :+ "bg-blue-3")
  def blue2: Style = new Style(classes :+ "bg-blue-2")
  def blue1: Style = new Style(classes :+ "bg-blue-1")

  def green5: Style = new Style(classes :+ "bg-green-5")
  def green4: Style = new Style(classes :+ "bg-green-4")
  def green3: Style = new Style(classes :+ "bg-green-3")
  def green2: Style = new Style(classes :+ "bg-green-2")
  def green1: Style = new Style(classes :+ "bg-green-1")

  def orange5: Style = new Style(classes :+ "bg-orange-5")
  def orange4: Style = new Style(classes :+ "bg-orange-4")
  def orange3: Style = new Style(classes :+ "bg-orange-3")
  def orange2: Style = new Style(classes :+ "bg-orange-2")
  def orange1: Style = new Style(classes :+ "bg-orange-1")

  def red5: Style = new Style(classes :+ "bg-red-5")
  def red4: Style = new Style(classes :+ "bg-red-4")
  def red3: Style = new Style(classes :+ "bg-red-3")
  def red2: Style = new Style(classes :+ "bg-red-2")
  def red1: Style = new Style(classes :+ "bg-red-1")

  def transparent: Style = new Style(classes :+ "bg-transparent")
  def currentColor: Style = new Style(classes :+ "bg-current")
}
