// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// scalastyle:off number.of.methods
private[style] final case class Hover(classes: List[String] = List.empty) {

  // Color

  def colorDarkGrayDarken: Style = new Style(classes :+ "hover-c-dark-gray-darken")
  def colorDarkGrayLighten: Style = new Style(classes :+ "hover-c-dark-gray-lighten")
  def colorDarkGray: Style = new Style(classes :+ "hover-c-dark-gray")
  def colorGrayDarken: Style = new Style(classes :+ "hover-c-gray-darken")
  def colorGrayLighten: Style = new Style(classes :+ "hover-c-gray-lighten")
  def colorGray: Style = new Style(classes :+ "hover-c-gray")
  def colorLightGrayDarken: Style = new Style(classes :+ "hover-c-light-gray-darken")
  def colorLightGrayLighten: Style = new Style(classes :+ "hover-c-light-gray-lighten")
  def colorLightGray: Style = new Style(classes :+ "hover-c-light-gray")
  def colorWhite: Style = new Style(classes :+ "hover-c-white")

  def colorPrimary: Style = new Style(classes :+ "hover-c-primary")
  def colorPrimaryDarken: Style = new Style(classes :+ "hover-c-primary-darken")
  def colorPrimaryLighten1: Style = new Style(classes :+ "hover-c-primary-lighten-1")
  def colorPrimaryLighten2: Style = new Style(classes :+ "hover-c-primary-lighten-2")
  def colorPrimaryLighten3: Style = new Style(classes :+ "hover-c-primary-lighten-3")
  def colorSuccess: Style = new Style(classes :+ "hover-c-success")
  def colorSuccessDarken: Style = new Style(classes :+ "hover-c-success-darken")
  def colorSuccessLighten1: Style = new Style(classes :+ "hover-c-success-lighten-1")
  def colorSuccessLighten2: Style = new Style(classes :+ "hover-c-success-lighten-2")
  def colorSuccessLighten3: Style = new Style(classes :+ "hover-c-success-lighten-3")
  def colorWarning: Style = new Style(classes :+ "hover-c-warning")
  def colorWarningDarken: Style = new Style(classes :+ "hover-c-warning-darken")
  def colorWarningLighten1: Style = new Style(classes :+ "hover-c-warning-lighten-1")
  def colorWarningLighten2: Style = new Style(classes :+ "hover-c-warning-lighten-2")
  def colorWarningLighten3: Style = new Style(classes :+ "hover-c-warning-lighten-3")
  def colorDanger: Style = new Style(classes :+ "hover-c-danger")
  def colorDangerDarken: Style = new Style(classes :+ "hover-c-danger-darken")
  def colorDangerLighten1: Style = new Style(classes :+ "hover-c-danger-lighten-1")
  def colorDangerLighten2: Style = new Style(classes :+ "hover-c-danger-lighten-2")
  def colorDangerLighten3: Style = new Style(classes :+ "hover-c-danger-lighten-3")

  // Background Color

  def backgroundDarkGrayDarken: Style = new Style(classes :+ "hover-bg-dark-gray-darken")
  def backgroundDarkGrayLighten: Style = new Style(classes :+ "hover-bg-dark-gray-lighten")
  def backgroundDarkGray: Style = new Style(classes :+ "hover-bg-dark-gray")
  def backgroundGrayDarken: Style = new Style(classes :+ "hover-bg-gray-darken")
  def backgroundGrayLighten: Style = new Style(classes :+ "hover-bg-gray-lighten")
  def backgroundGray: Style = new Style(classes :+ "hover-bg-gray")
  def backgroundLightGrayDarken: Style = new Style(classes :+ "hover-bg-light-gray-darken")
  def backgroundLightGrayLighten: Style = new Style(classes :+ "hover-bg-light-gray-lighten")
  def backgroundLightGray: Style = new Style(classes :+ "hover-bg-light-gray")
  def backgroundWhite: Style = new Style(classes :+ "hover-bg-white")

  def backgroundPrimary: Style = new Style(classes :+ "hover-bg-primary")
  def backgroundPrimaryDarken: Style = new Style(classes :+ "hover-bg-primary-darken")
  def backgroundPrimaryLighten1: Style = new Style(classes :+ "hover-bg-primary-lighten-1")
  def backgroundPrimaryLighten2: Style = new Style(classes :+ "hover-bg-primary-lighten-2")
  def backgroundPrimaryLighten3: Style = new Style(classes :+ "hover-bg-primary-lighten-3")
  def backgroundSuccess: Style = new Style(classes :+ "hover-bg-success")
  def backgroundSuccessDarken: Style = new Style(classes :+ "hover-bg-success-darken")
  def backgroundSuccessLighten1: Style = new Style(classes :+ "hover-bg-success-lighten-1")
  def backgroundSuccessLighten2: Style = new Style(classes :+ "hover-bg-success-lighten-2")
  def backgroundSuccessLighten3: Style = new Style(classes :+ "hover-bg-success-lighten-3")
  def backgroundWarning: Style = new Style(classes :+ "hover-bg-warning")
  def backgroundWarningDarken: Style = new Style(classes :+ "hover-bg-warning-darken")
  def backgroundWarningLighten1: Style = new Style(classes :+ "hover-bg-warning-lighten-1")
  def backgroundWarningLighten2: Style = new Style(classes :+ "hover-bg-warning-lighten-2")
  def backgroundWarningLighten3: Style = new Style(classes :+ "hover-bg-warning-lighten-3")
  def backgroundDanger: Style = new Style(classes :+ "hover-bg-danger")
  def backgroundDangerDarken: Style = new Style(classes :+ "hover-bg-danger-darken")
  def backgroundDangerLighten1: Style = new Style(classes :+ "hover-bg-danger-lighten-1")
  def backgroundDangerLighten2: Style = new Style(classes :+ "hover-bg-danger-lighten-2")
  def backgroundDangerLighten3: Style = new Style(classes :+ "hover-bg-danger-lighten-3")

  // Border Color

  def borderDarkGrayDarken: Style = new Style(classes :+ "hover-b-dark-gray-darken")
  def borderDarkGrayLighten: Style = new Style(classes :+ "hover-b-dark-gray-lighten")
  def borderDarkGray: Style = new Style(classes :+ "hover-b-dark-gray")
  def borderGrayDarken: Style = new Style(classes :+ "hover-b-gray-darken")
  def borderGrayLighten: Style = new Style(classes :+ "hover-b-gray-lighten")
  def borderGray: Style = new Style(classes :+ "hover-b-gray")
  def borderLightGrayDarken: Style = new Style(classes :+ "hover-b-light-gray-darken")
  def borderLightGrayLighten: Style = new Style(classes :+ "hover-b-light-gray-lighten")
  def borderLightGray: Style = new Style(classes :+ "hover-b-light-gray")
  def borderWhite: Style = new Style(classes :+ "hover-b-white")

  def borderPrimary: Style = new Style(classes :+ "hover-b-primary")
  def borderPrimaryDarken: Style = new Style(classes :+ "hover-b-primary-darken")
  def borderPrimaryLighten1: Style = new Style(classes :+ "hover-b-primary-lighten-1")
  def borderPrimaryLighten2: Style = new Style(classes :+ "hover-b-primary-lighten-2")
  def borderPrimaryLighten3: Style = new Style(classes :+ "hover-b-primary-lighten-3")
  def borderSuccess: Style = new Style(classes :+ "hover-b-success")
  def borderSuccessDarken: Style = new Style(classes :+ "hover-b-success-darken")
  def borderSuccessLighten1: Style = new Style(classes :+ "hover-b-success-lighten-1")
  def borderSuccessLighten2: Style = new Style(classes :+ "hover-b-success-lighten-2")
  def borderSuccessLighten3: Style = new Style(classes :+ "hover-b-success-lighten-3")
  def borderWarning: Style = new Style(classes :+ "hover-b-warning")
  def borderWarningDarken: Style = new Style(classes :+ "hover-b-warning-darken")
  def borderWarningLighten1: Style = new Style(classes :+ "hover-b-warning-lighten-1")
  def borderWarningLighten2: Style = new Style(classes :+ "hover-b-warning-lighten-2")
  def borderWarningLighten3: Style = new Style(classes :+ "hover-b-warning-lighten-3")
  def borderDanger: Style = new Style(classes :+ "hover-b-danger")
  def borderDangerDarken: Style = new Style(classes :+ "hover-b-danger-darken")
  def borderDangerLighten1: Style = new Style(classes :+ "hover-b-danger-lighten-1")
  def borderDangerLighten2: Style = new Style(classes :+ "hover-b-danger-lighten-2")
  def borderDangerLighten3: Style = new Style(classes :+ "hover-b-danger-lighten-3")

  // Shadow

  def shadowPrimaryLighten1: Style = new Style(classes :+ "hover-shadow-primary-lighten-1")
}
// scalastyle:on number.of.methods
