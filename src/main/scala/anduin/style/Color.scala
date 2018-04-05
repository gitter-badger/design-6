// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_skins.css
// scalastyle:off number.of.methods
private[style] final case class Color(classes: List[String] = List.empty) {

  def darkGrayDarken: Style = new Style(classes :+ "c-dark-gray-darken")
  def darkGrayLighten: Style = new Style(classes :+ "c-dark-gray-lighten")
  def darkGray: Style = new Style(classes :+ "c-dark-gray")
  def grayDarken: Style = new Style(classes :+ "c-gray-darken")
  def grayLighten: Style = new Style(classes :+ "c-gray-lighten")
  def gray: Style = new Style(classes :+ "c-gray")
  def lightGrayDarken: Style = new Style(classes :+ "c-light-gray-darken")
  def lightGrayLighten: Style = new Style(classes :+ "c-light-gray-lighten")
  def lightGray: Style = new Style(classes :+ "c-light-gray")
  def white: Style = new Style(classes :+ "c-white")

  def primary: Style = new Style(classes :+ "c-primary")
  def primaryDarken: Style = new Style(classes :+ "c-primary-darken")
  def primaryLighten1: Style = new Style(classes :+ "c-primary-lighten-1")
  def primaryLighten2: Style = new Style(classes :+ "c-primary-lighten-2")
  def primaryLighten3: Style = new Style(classes :+ "c-primary-lighten-3")
  def success: Style = new Style(classes :+ "c-success")
  def successDarken: Style = new Style(classes :+ "c-success-darken")
  def successLighten1: Style = new Style(classes :+ "c-success-lighten-1")
  def successLighten2: Style = new Style(classes :+ "c-success-lighten-2")
  def successLighten3: Style = new Style(classes :+ "c-success-lighten-3")
  def warning: Style = new Style(classes :+ "c-warning")
  def warningDarken: Style = new Style(classes :+ "c-warning-darken")
  def warningLighten1: Style = new Style(classes :+ "c-warning-lighten-1")
  def warningLighten2: Style = new Style(classes :+ "c-warning-lighten-2")
  def warningLighten3: Style = new Style(classes :+ "c-warning-lighten-3")
  def danger: Style = new Style(classes :+ "c-danger")
  def dangerDarken: Style = new Style(classes :+ "c-danger-darken")
  def dangerLighten1: Style = new Style(classes :+ "c-danger-lighten-1")
  def dangerLighten2: Style = new Style(classes :+ "c-danger-lighten-2")
  def dangerLighten3: Style = new Style(classes :+ "c-danger-lighten-3")

  def inherit: Style = new Style(classes :+ "c-inherit")
}
