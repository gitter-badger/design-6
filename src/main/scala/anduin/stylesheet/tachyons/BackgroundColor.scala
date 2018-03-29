// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_skins.css
// scalastyle:off number.of.methods
private[tachyons] final case class BackgroundColor(classes: List[String] = List.empty) {

  def darkGrayDarken: Tachyons = new Tachyons(classes :+ "bg-dark-gray-darken")
  def darkGrayLighten: Tachyons = new Tachyons(classes :+ "bg-dark-gray-lighten")
  def darkGray: Tachyons = new Tachyons(classes :+ "bg-dark-gray")
  def grayDarken: Tachyons = new Tachyons(classes :+ "bg-gray-darken")
  def grayLighten: Tachyons = new Tachyons(classes :+ "bg-gray-lighten")
  def gray: Tachyons = new Tachyons(classes :+ "bg-gray")
  def lightGrayDarken: Tachyons = new Tachyons(classes :+ "bg-light-gray-darken")
  def lightGrayLighten: Tachyons = new Tachyons(classes :+ "bg-light-gray-lighten")
  def lightGray: Tachyons = new Tachyons(classes :+ "bg-light-gray")
  def white: Tachyons = new Tachyons(classes :+ "bg-white")

  def primary: Tachyons = new Tachyons(classes :+ "bg-primary")
  def primaryDarken: Tachyons = new Tachyons(classes :+ "bg-primary-darken")
  def primaryLighten1: Tachyons = new Tachyons(classes :+ "bg-primary-lighten-1")
  def primaryLighten2: Tachyons = new Tachyons(classes :+ "bg-primary-lighten-2")
  def primaryLighten3: Tachyons = new Tachyons(classes :+ "bg-primary-lighten-3")
  def success: Tachyons = new Tachyons(classes :+ "bg-success")
  def successDarken: Tachyons = new Tachyons(classes :+ "bg-success-darken")
  def successLighten1: Tachyons = new Tachyons(classes :+ "bg-success-lighten-1")
  def successLighten2: Tachyons = new Tachyons(classes :+ "bg-success-lighten-2")
  def successLighten3: Tachyons = new Tachyons(classes :+ "bg-success-lighten-3")
  def warning: Tachyons = new Tachyons(classes :+ "bg-warning")
  def warningDarken: Tachyons = new Tachyons(classes :+ "bg-warning-darken")
  def warningLighten1: Tachyons = new Tachyons(classes :+ "bg-warning-lighten-1")
  def warningLighten2: Tachyons = new Tachyons(classes :+ "bg-warning-lighten-2")
  def warningLighten3: Tachyons = new Tachyons(classes :+ "bg-warning-lighten-3")
  def danger: Tachyons = new Tachyons(classes :+ "bg-danger")
  def dangerDarken: Tachyons = new Tachyons(classes :+ "bg-danger-darken")
  def dangerLighten1: Tachyons = new Tachyons(classes :+ "bg-danger-lighten-1")
  def dangerLighten2: Tachyons = new Tachyons(classes :+ "bg-danger-lighten-2")
  def dangerLighten3: Tachyons = new Tachyons(classes :+ "bg-danger-lighten-3")

  def inherit: Tachyons = new Tachyons(classes :+ "bg-inherit")
}
