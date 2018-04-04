// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// scalastyle:off number.of.methods
private[tachyons] final case class Hover(classes: List[String] = List.empty) {

  // Color

  def colorDarkGrayDarken: Tachyons = new Tachyons(classes :+ "hover-c-dark-gray-darken")
  def colorDarkGrayLighten: Tachyons = new Tachyons(classes :+ "hover-c-dark-gray-lighten")
  def colorDarkGray: Tachyons = new Tachyons(classes :+ "hover-c-dark-gray")
  def colorGrayDarken: Tachyons = new Tachyons(classes :+ "hover-c-gray-darken")
  def colorGrayLighten: Tachyons = new Tachyons(classes :+ "hover-c-gray-lighten")
  def colorGray: Tachyons = new Tachyons(classes :+ "hover-c-gray")
  def colorLightGrayDarken: Tachyons = new Tachyons(classes :+ "hover-c-light-gray-darken")
  def colorLightGrayLighten: Tachyons = new Tachyons(classes :+ "hover-c-light-gray-lighten")
  def colorLightGray: Tachyons = new Tachyons(classes :+ "hover-c-light-gray")
  def colorWhite: Tachyons = new Tachyons(classes :+ "hover-c-white")

  def colorPrimary: Tachyons = new Tachyons(classes :+ "hover-c-primary")
  def colorPrimaryDarken: Tachyons = new Tachyons(classes :+ "hover-c-primary-darken")
  def colorPrimaryLighten1: Tachyons = new Tachyons(classes :+ "hover-c-primary-lighten-1")
  def colorPrimaryLighten2: Tachyons = new Tachyons(classes :+ "hover-c-primary-lighten-2")
  def colorPrimaryLighten3: Tachyons = new Tachyons(classes :+ "hover-c-primary-lighten-3")
  def colorSuccess: Tachyons = new Tachyons(classes :+ "hover-c-success")
  def colorSuccessDarken: Tachyons = new Tachyons(classes :+ "hover-c-success-darken")
  def colorSuccessLighten1: Tachyons = new Tachyons(classes :+ "hover-c-success-lighten-1")
  def colorSuccessLighten2: Tachyons = new Tachyons(classes :+ "hover-c-success-lighten-2")
  def colorSuccessLighten3: Tachyons = new Tachyons(classes :+ "hover-c-success-lighten-3")
  def colorWarning: Tachyons = new Tachyons(classes :+ "hover-c-warning")
  def colorWarningDarken: Tachyons = new Tachyons(classes :+ "hover-c-warning-darken")
  def colorWarningLighten1: Tachyons = new Tachyons(classes :+ "hover-c-warning-lighten-1")
  def colorWarningLighten2: Tachyons = new Tachyons(classes :+ "hover-c-warning-lighten-2")
  def colorWarningLighten3: Tachyons = new Tachyons(classes :+ "hover-c-warning-lighten-3")
  def colorDanger: Tachyons = new Tachyons(classes :+ "hover-c-danger")
  def colorDangerDarken: Tachyons = new Tachyons(classes :+ "hover-c-danger-darken")
  def colorDangerLighten1: Tachyons = new Tachyons(classes :+ "hover-c-danger-lighten-1")
  def colorDangerLighten2: Tachyons = new Tachyons(classes :+ "hover-c-danger-lighten-2")
  def colorDangerLighten3: Tachyons = new Tachyons(classes :+ "hover-c-danger-lighten-3")

  // Background Color

  def backgroundDarkGrayDarken: Tachyons = new Tachyons(classes :+ "hover-bg-dark-gray-darken")
  def backgroundDarkGrayLighten: Tachyons = new Tachyons(classes :+ "hover-bg-dark-gray-lighten")
  def backgroundDarkGray: Tachyons = new Tachyons(classes :+ "hover-bg-dark-gray")
  def backgroundGrayDarken: Tachyons = new Tachyons(classes :+ "hover-bg-gray-darken")
  def backgroundGrayLighten: Tachyons = new Tachyons(classes :+ "hover-bg-gray-lighten")
  def backgroundGray: Tachyons = new Tachyons(classes :+ "hover-bg-gray")
  def backgroundLightGrayDarken: Tachyons = new Tachyons(classes :+ "hover-bg-light-gray-darken")
  def backgroundLightGrayLighten: Tachyons = new Tachyons(classes :+ "hover-bg-light-gray-lighten")
  def backgroundLightGray: Tachyons = new Tachyons(classes :+ "hover-bg-light-gray")
  def backgroundWhite: Tachyons = new Tachyons(classes :+ "hover-bg-white")

  def backgroundPrimary: Tachyons = new Tachyons(classes :+ "hover-bg-primary")
  def backgroundPrimaryDarken: Tachyons = new Tachyons(classes :+ "hover-bg-primary-darken")
  def backgroundPrimaryLighten1: Tachyons = new Tachyons(classes :+ "hover-bg-primary-lighten-1")
  def backgroundPrimaryLighten2: Tachyons = new Tachyons(classes :+ "hover-bg-primary-lighten-2")
  def backgroundPrimaryLighten3: Tachyons = new Tachyons(classes :+ "hover-bg-primary-lighten-3")
  def backgroundSuccess: Tachyons = new Tachyons(classes :+ "hover-bg-success")
  def backgroundSuccessDarken: Tachyons = new Tachyons(classes :+ "hover-bg-success-darken")
  def backgroundSuccessLighten1: Tachyons = new Tachyons(classes :+ "hover-bg-success-lighten-1")
  def backgroundSuccessLighten2: Tachyons = new Tachyons(classes :+ "hover-bg-success-lighten-2")
  def backgroundSuccessLighten3: Tachyons = new Tachyons(classes :+ "hover-bg-success-lighten-3")
  def backgroundWarning: Tachyons = new Tachyons(classes :+ "hover-bg-warning")
  def backgroundWarningDarken: Tachyons = new Tachyons(classes :+ "hover-bg-warning-darken")
  def backgroundWarningLighten1: Tachyons = new Tachyons(classes :+ "hover-bg-warning-lighten-1")
  def backgroundWarningLighten2: Tachyons = new Tachyons(classes :+ "hover-bg-warning-lighten-2")
  def backgroundWarningLighten3: Tachyons = new Tachyons(classes :+ "hover-bg-warning-lighten-3")
  def backgroundDanger: Tachyons = new Tachyons(classes :+ "hover-bg-danger")
  def backgroundDangerDarken: Tachyons = new Tachyons(classes :+ "hover-bg-danger-darken")
  def backgroundDangerLighten1: Tachyons = new Tachyons(classes :+ "hover-bg-danger-lighten-1")
  def backgroundDangerLighten2: Tachyons = new Tachyons(classes :+ "hover-bg-danger-lighten-2")
  def backgroundDangerLighten3: Tachyons = new Tachyons(classes :+ "hover-bg-danger-lighten-3")

  // Border Color

  def borderDarkGrayDarken: Tachyons = new Tachyons(classes :+ "hover-b-dark-gray-darken")
  def borderDarkGrayLighten: Tachyons = new Tachyons(classes :+ "hover-b-dark-gray-lighten")
  def borderDarkGray: Tachyons = new Tachyons(classes :+ "hover-b-dark-gray")
  def borderGrayDarken: Tachyons = new Tachyons(classes :+ "hover-b-gray-darken")
  def borderGrayLighten: Tachyons = new Tachyons(classes :+ "hover-b-gray-lighten")
  def borderGray: Tachyons = new Tachyons(classes :+ "hover-b-gray")
  def borderLightGrayDarken: Tachyons = new Tachyons(classes :+ "hover-b-light-gray-darken")
  def borderLightGrayLighten: Tachyons = new Tachyons(classes :+ "hover-b-light-gray-lighten")
  def borderLightGray: Tachyons = new Tachyons(classes :+ "hover-b-light-gray")
  def borderWhite: Tachyons = new Tachyons(classes :+ "hover-b-white")

  def borderPrimary: Tachyons = new Tachyons(classes :+ "hover-b-primary")
  def borderPrimaryDarken: Tachyons = new Tachyons(classes :+ "hover-b-primary-darken")
  def borderPrimaryLighten1: Tachyons = new Tachyons(classes :+ "hover-b-primary-lighten-1")
  def borderPrimaryLighten2: Tachyons = new Tachyons(classes :+ "hover-b-primary-lighten-2")
  def borderPrimaryLighten3: Tachyons = new Tachyons(classes :+ "hover-b-primary-lighten-3")
  def borderSuccess: Tachyons = new Tachyons(classes :+ "hover-b-success")
  def borderSuccessDarken: Tachyons = new Tachyons(classes :+ "hover-b-success-darken")
  def borderSuccessLighten1: Tachyons = new Tachyons(classes :+ "hover-b-success-lighten-1")
  def borderSuccessLighten2: Tachyons = new Tachyons(classes :+ "hover-b-success-lighten-2")
  def borderSuccessLighten3: Tachyons = new Tachyons(classes :+ "hover-b-success-lighten-3")
  def borderWarning: Tachyons = new Tachyons(classes :+ "hover-b-warning")
  def borderWarningDarken: Tachyons = new Tachyons(classes :+ "hover-b-warning-darken")
  def borderWarningLighten1: Tachyons = new Tachyons(classes :+ "hover-b-warning-lighten-1")
  def borderWarningLighten2: Tachyons = new Tachyons(classes :+ "hover-b-warning-lighten-2")
  def borderWarningLighten3: Tachyons = new Tachyons(classes :+ "hover-b-warning-lighten-3")
  def borderDanger: Tachyons = new Tachyons(classes :+ "hover-b-danger")
  def borderDangerDarken: Tachyons = new Tachyons(classes :+ "hover-b-danger-darken")
  def borderDangerLighten1: Tachyons = new Tachyons(classes :+ "hover-b-danger-lighten-1")
  def borderDangerLighten2: Tachyons = new Tachyons(classes :+ "hover-b-danger-lighten-2")
  def borderDangerLighten3: Tachyons = new Tachyons(classes :+ "hover-b-danger-lighten-3")

  // Shadow

  def shadowPrimaryLighten1: Tachyons = new Tachyons(classes :+ "hover-shadow-primary-lighten-1")
}
// scalastyle:on number.of.methods
