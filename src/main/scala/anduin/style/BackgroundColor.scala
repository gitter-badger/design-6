// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_skins.css
// scalastyle:off number.of.methods
private[style] final case class BackgroundColor(classes: List[String] = List.empty) {

  def darkGrayDarken: Style = new Style(classes :+ "bg-dark-gray-darken")
  def darkGrayLighten: Style = new Style(classes :+ "bg-dark-gray-lighten")
  def darkGray: Style = new Style(classes :+ "bg-dark-gray")
  def grayDarken: Style = new Style(classes :+ "bg-gray-darken")
  def grayLighten: Style = new Style(classes :+ "bg-gray-lighten")
  def gray: Style = new Style(classes :+ "bg-gray")
  def lightGrayDarken: Style = new Style(classes :+ "bg-light-gray-darken")
  def lightGrayLighten: Style = new Style(classes :+ "bg-light-gray-lighten")
  def lightGray: Style = new Style(classes :+ "bg-light-gray")
  def white: Style = new Style(classes :+ "bg-white")

  def primary: Style = new Style(classes :+ "bg-primary")
  def primaryDarken: Style = new Style(classes :+ "bg-primary-darken")
  def primaryLighten1: Style = new Style(classes :+ "bg-primary-lighten-1")
  def primaryLighten2: Style = new Style(classes :+ "bg-primary-lighten-2")
  def primaryLighten3: Style = new Style(classes :+ "bg-primary-lighten-3")
  def success: Style = new Style(classes :+ "bg-success")
  def successDarken: Style = new Style(classes :+ "bg-success-darken")
  def successLighten1: Style = new Style(classes :+ "bg-success-lighten-1")
  def successLighten2: Style = new Style(classes :+ "bg-success-lighten-2")
  def successLighten3: Style = new Style(classes :+ "bg-success-lighten-3")
  def warning: Style = new Style(classes :+ "bg-warning")
  def warningDarken: Style = new Style(classes :+ "bg-warning-darken")
  def warningLighten1: Style = new Style(classes :+ "bg-warning-lighten-1")
  def warningLighten2: Style = new Style(classes :+ "bg-warning-lighten-2")
  def warningLighten3: Style = new Style(classes :+ "bg-warning-lighten-3")
  def danger: Style = new Style(classes :+ "bg-danger")
  def dangerDarken: Style = new Style(classes :+ "bg-danger-darken")
  def dangerLighten1: Style = new Style(classes :+ "bg-danger-lighten-1")
  def dangerLighten2: Style = new Style(classes :+ "bg-danger-lighten-2")
  def dangerLighten3: Style = new Style(classes :+ "bg-danger-lighten-3")

  def inherit: Style = new Style(classes :+ "bg-inherit")
}
