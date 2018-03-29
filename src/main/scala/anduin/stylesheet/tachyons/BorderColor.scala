// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_border-colors.css
// scalastyle:off number.of.methods
private[tachyons] final case class BorderColor(classes: List[String] = List.empty) {

  def darkGrayDarken: Tachyons = new Tachyons(classes :+ "b--dark-gray-darken")
  def darkGrayLighten: Tachyons = new Tachyons(classes :+ "b--dark-gray-lighten")
  def darkGray: Tachyons = new Tachyons(classes :+ "b--dark-gray")
  def grayDarken: Tachyons = new Tachyons(classes :+ "b--gray-darken")
  def grayLighten: Tachyons = new Tachyons(classes :+ "b--gray-lighten")
  def gray: Tachyons = new Tachyons(classes :+ "b--gray")
  def lightGrayDarken: Tachyons = new Tachyons(classes :+ "b--light-gray-darken")
  def lightGrayLighten: Tachyons = new Tachyons(classes :+ "b--light-gray-lighten")
  def lightGray: Tachyons = new Tachyons(classes :+ "b--light-gray")
  def white: Tachyons = new Tachyons(classes :+ "b--white")

  def primary: Tachyons = new Tachyons(classes :+ "b--primary")
  def primaryDarken: Tachyons = new Tachyons(classes :+ "b--primary-darken")
  def primaryLighten1: Tachyons = new Tachyons(classes :+ "b--primary-lighten-1")
  def primaryLighten2: Tachyons = new Tachyons(classes :+ "b--primary-lighten-2")
  def primaryLighten3: Tachyons = new Tachyons(classes :+ "b--primary-lighten-3")
  def success: Tachyons = new Tachyons(classes :+ "b--success")
  def successDarken: Tachyons = new Tachyons(classes :+ "b--success-darken")
  def successLighten1: Tachyons = new Tachyons(classes :+ "b--success-lighten-1")
  def successLighten2: Tachyons = new Tachyons(classes :+ "b--success-lighten-2")
  def successLighten3: Tachyons = new Tachyons(classes :+ "b--success-lighten-3")
  def warning: Tachyons = new Tachyons(classes :+ "b--warning")
  def warningDarken: Tachyons = new Tachyons(classes :+ "b--warning-darken")
  def warningLighten1: Tachyons = new Tachyons(classes :+ "b--warning-lighten-1")
  def warningLighten2: Tachyons = new Tachyons(classes :+ "b--warning-lighten-2")
  def warningLighten3: Tachyons = new Tachyons(classes :+ "b--warning-lighten-3")
  def danger: Tachyons = new Tachyons(classes :+ "b--danger")
  def dangerDarken: Tachyons = new Tachyons(classes :+ "b--danger-darken")
  def dangerLighten1: Tachyons = new Tachyons(classes :+ "b--danger-lighten-1")
  def dangerLighten2: Tachyons = new Tachyons(classes :+ "b--danger-lighten-2")
  def dangerLighten3: Tachyons = new Tachyons(classes :+ "b--danger-lighten-3")

  def transparent: Tachyons = new Tachyons(classes :+ "b--transparent")
  def inherit: Tachyons = new Tachyons(classes :+ "b--inherit")
}
// scalastyle:on number.of.methods
