// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_skins.css
// scalastyle:off number.of.methods
private[tachyons] final case class Color(classes: List[String] = List.empty) {

  def black90: Tachyons = new Tachyons(classes :+ "black-90")
  def black80: Tachyons = new Tachyons(classes :+ "black-80")
  def black70: Tachyons = new Tachyons(classes :+ "black-70")
  def black60: Tachyons = new Tachyons(classes :+ "black-60")
  def black50: Tachyons = new Tachyons(classes :+ "black-50")
  def black40: Tachyons = new Tachyons(classes :+ "black-40")
  def black30: Tachyons = new Tachyons(classes :+ "black-30")
  def black20: Tachyons = new Tachyons(classes :+ "black-20")
  def black10: Tachyons = new Tachyons(classes :+ "black-10")
  def black05: Tachyons = new Tachyons(classes :+ "black-05")

  def white90: Tachyons = new Tachyons(classes :+ "white-90")
  def white80: Tachyons = new Tachyons(classes :+ "white-80")
  def white70: Tachyons = new Tachyons(classes :+ "white-70")
  def white60: Tachyons = new Tachyons(classes :+ "white-60")
  def white50: Tachyons = new Tachyons(classes :+ "white-50")
  def white40: Tachyons = new Tachyons(classes :+ "white-40")
  def white30: Tachyons = new Tachyons(classes :+ "white-30")
  def white20: Tachyons = new Tachyons(classes :+ "white-20")
  def white10: Tachyons = new Tachyons(classes :+ "white-10")

  def black: Tachyons = new Tachyons(classes :+ "black")
  def nearBlack: Tachyons = new Tachyons(classes :+ "near-black")
  def darkGray: Tachyons = new Tachyons(classes :+ "dark-gray")
  def midGray: Tachyons = new Tachyons(classes :+ "mid-gray")
  def gray: Tachyons = new Tachyons(classes :+ "gray")
  def silver: Tachyons = new Tachyons(classes :+ "silver")
  def lightSilver: Tachyons = new Tachyons(classes :+ "light-silver")
  def moonGray: Tachyons = new Tachyons(classes :+ "moon-gray")
  def lightGray: Tachyons = new Tachyons(classes :+ "light-gray")
  def nearWhite: Tachyons = new Tachyons(classes :+ "near-white")
  def white: Tachyons = new Tachyons(classes :+ "white")

  def darkRed: Tachyons = new Tachyons(classes :+ "dark-red")
  def red: Tachyons = new Tachyons(classes :+ "red")
  def lightRed: Tachyons = new Tachyons(classes :+ "light-red")
  def orange: Tachyons = new Tachyons(classes :+ "orange")
  def gold: Tachyons = new Tachyons(classes :+ "gold")
  def yellow: Tachyons = new Tachyons(classes :+ "yellow")
  def lightYellow: Tachyons = new Tachyons(classes :+ "light-yellow")
  def purple: Tachyons = new Tachyons(classes :+ "purple")
  def lightPurple: Tachyons = new Tachyons(classes :+ "light-purple")
  def darkPink: Tachyons = new Tachyons(classes :+ "dark-pink")
  def hotPink: Tachyons = new Tachyons(classes :+ "hot-pink")
  def pink: Tachyons = new Tachyons(classes :+ "pink")
  def lightPink: Tachyons = new Tachyons(classes :+ "light-pink")
  def darkGreen: Tachyons = new Tachyons(classes :+ "dark-green")
  def green: Tachyons = new Tachyons(classes :+ "green")
  def lightGreen: Tachyons = new Tachyons(classes :+ "light-green")
  def navy: Tachyons = new Tachyons(classes :+ "navy")
  def darkBlue: Tachyons = new Tachyons(classes :+ "dark-blue")
  def blue: Tachyons = new Tachyons(classes :+ "blue")
  def lightBlue: Tachyons = new Tachyons(classes :+ "light-blue")
  def lightestBlue: Tachyons = new Tachyons(classes :+ "lightest-blue")
  def washedBlue: Tachyons = new Tachyons(classes :+ "washed-blue")
  def washedGreen: Tachyons = new Tachyons(classes :+ "washed-green")
  def washedYellow: Tachyons = new Tachyons(classes :+ "washed-yellow")
  def washedRed: Tachyons = new Tachyons(classes :+ "washed-red")

  def inherit: Tachyons = new Tachyons(classes :+ "color-inherit")
}
