// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_border-colors.css
// scalastyle:off number.of.methods
private[style] final case class BorderColor(classes: List[String] = List.empty) {

  def darkGrayDarken: Style = new Style(classes :+ "b--dark-gray-darken")
  def darkGrayLighten: Style = new Style(classes :+ "b--dark-gray-lighten")
  def darkGray: Style = new Style(classes :+ "b--dark-gray")
  def grayDarken: Style = new Style(classes :+ "b--gray-darken")
  def grayLighten: Style = new Style(classes :+ "b--gray-lighten")
  def gray: Style = new Style(classes :+ "b--gray")
  def lightGrayDarken: Style = new Style(classes :+ "b--light-gray-darken")
  def lightGrayLighten: Style = new Style(classes :+ "b--light-gray-lighten")
  def lightGray: Style = new Style(classes :+ "b--light-gray")
  def white: Style = new Style(classes :+ "b--white")

  def primary: Style = new Style(classes :+ "b--primary")
  def primaryDarken: Style = new Style(classes :+ "b--primary-darken")
  def primaryLighten1: Style = new Style(classes :+ "b--primary-lighten-1")
  def primaryLighten2: Style = new Style(classes :+ "b--primary-lighten-2")
  def primaryLighten3: Style = new Style(classes :+ "b--primary-lighten-3")
  def success: Style = new Style(classes :+ "b--success")
  def successDarken: Style = new Style(classes :+ "b--success-darken")
  def successLighten1: Style = new Style(classes :+ "b--success-lighten-1")
  def successLighten2: Style = new Style(classes :+ "b--success-lighten-2")
  def successLighten3: Style = new Style(classes :+ "b--success-lighten-3")
  def warning: Style = new Style(classes :+ "b--warning")
  def warningDarken: Style = new Style(classes :+ "b--warning-darken")
  def warningLighten1: Style = new Style(classes :+ "b--warning-lighten-1")
  def warningLighten2: Style = new Style(classes :+ "b--warning-lighten-2")
  def warningLighten3: Style = new Style(classes :+ "b--warning-lighten-3")
  def danger: Style = new Style(classes :+ "b--danger")
  def dangerDarken: Style = new Style(classes :+ "b--danger-darken")
  def dangerLighten1: Style = new Style(classes :+ "b--danger-lighten-1")
  def dangerLighten2: Style = new Style(classes :+ "b--danger-lighten-2")
  def dangerLighten3: Style = new Style(classes :+ "b--danger-lighten-3")

  def transparent: Style = new Style(classes :+ "b--transparent")
  def inherit: Style = new Style(classes :+ "b--inherit")
}
// scalastyle:on number.of.methods
