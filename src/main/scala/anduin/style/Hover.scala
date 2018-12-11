// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// scalastyle:off number.of.methods
private[style] final case class Hover(classes: List[String] = List.empty) {

  // Color

  def colorGray9: Style = new Style(classes :+ "hover-c--gray-9")
  def colorGray8: Style = new Style(classes :+ "hover-c--gray-8")
  def colorGray7: Style = new Style(classes :+ "hover-c--gray-7")
  def colorGray6: Style = new Style(classes :+ "hover-c--gray-6")
  def colorGray5: Style = new Style(classes :+ "hover-c--gray-5")
  def colorGray4: Style = new Style(classes :+ "hover-c--gray-4")
  def colorGray3: Style = new Style(classes :+ "hover-c--gray-3")
  def colorGray2: Style = new Style(classes :+ "hover-c--gray-2")
  def colorGray1: Style = new Style(classes :+ "hover-c--gray-1")

  def colorWhite: Style = new Style(classes :+ "hover-c--white")

  def colorPrimary5: Style = new Style(classes :+ "hover-c--primary-5")
  def colorPrimary4: Style = new Style(classes :+ "hover-c--primary-4")
  def colorPrimary3: Style = new Style(classes :+ "hover-c--primary-3")
  def colorPrimary2: Style = new Style(classes :+ "hover-c--primary-2")
  def colorPrimary1: Style = new Style(classes :+ "hover-c--primary-1")

  def colorSuccess5: Style = new Style(classes :+ "hover-c--success-5")
  def colorSuccess4: Style = new Style(classes :+ "hover-c--success-4")
  def colorSuccess3: Style = new Style(classes :+ "hover-c--success-3")
  def colorSuccess2: Style = new Style(classes :+ "hover-c--success-2")
  def colorSuccess1: Style = new Style(classes :+ "hover-c--success-1")

  def colorWarning5: Style = new Style(classes :+ "hover-c--warning-5")
  def colorWarning4: Style = new Style(classes :+ "hover-c--warning-4")
  def colorWarning3: Style = new Style(classes :+ "hover-c--warning-3")
  def colorWarning2: Style = new Style(classes :+ "hover-c--warning-2")
  def colorWarning1: Style = new Style(classes :+ "hover-c--warning-1")

  def colorDanger5: Style = new Style(classes :+ "hover-c--danger-5")
  def colorDanger4: Style = new Style(classes :+ "hover-c--danger-4")
  def colorDanger3: Style = new Style(classes :+ "hover-c--danger-3")
  def colorDanger2: Style = new Style(classes :+ "hover-c--danger-2")
  def colorDanger1: Style = new Style(classes :+ "hover-c--danger-1")

  def colorPurple5: Style = new Style(classes :+ "hover-c--purple-5")
  def colorPurple4: Style = new Style(classes :+ "hover-c--purple-4")
  def colorPurple3: Style = new Style(classes :+ "hover-c--purple-3")
  def colorPurple2: Style = new Style(classes :+ "hover-c--purple-2")
  def colorPurple1: Style = new Style(classes :+ "hover-c--purple-1")

  // Background Color

  def backgroundGray9: Style = new Style(classes :+ "hover-bg--gray-9")
  def backgroundGray8: Style = new Style(classes :+ "hover-bg--gray-8")
  def backgroundGray7: Style = new Style(classes :+ "hover-bg--gray-7")
  def backgroundGray6: Style = new Style(classes :+ "hover-bg--gray-6")
  def backgroundGray5: Style = new Style(classes :+ "hover-bg--gray-5")
  def backgroundGray4: Style = new Style(classes :+ "hover-bg--gray-4")
  def backgroundGray3: Style = new Style(classes :+ "hover-bg--gray-3")
  def backgroundGray2: Style = new Style(classes :+ "hover-bg--gray-2")
  def backgroundGray1: Style = new Style(classes :+ "hover-bg--gray-1")

  def backgroundWhite: Style = new Style(classes :+ "hover-bg--white")

  def backgroundPrimary5: Style = new Style(classes :+ "hover-bg--primary-5")
  def backgroundPrimary4: Style = new Style(classes :+ "hover-bg--primary-4")
  def backgroundPrimary3: Style = new Style(classes :+ "hover-bg--primary-3")
  def backgroundPrimary2: Style = new Style(classes :+ "hover-bg--primary-2")
  def backgroundPrimary1: Style = new Style(classes :+ "hover-bg--primary-1")

  def backgroundSuccess5: Style = new Style(classes :+ "hover-bg--success-5")
  def backgroundSuccess4: Style = new Style(classes :+ "hover-bg--success-4")
  def backgroundSuccess3: Style = new Style(classes :+ "hover-bg--success-3")
  def backgroundSuccess2: Style = new Style(classes :+ "hover-bg--success-2")
  def backgroundSuccess1: Style = new Style(classes :+ "hover-bg--success-1")

  def backgroundWarning5: Style = new Style(classes :+ "hover-bg--warning-5")
  def backgroundWarning4: Style = new Style(classes :+ "hover-bg--warning-4")
  def backgroundWarning3: Style = new Style(classes :+ "hover-bg--warning-3")
  def backgroundWarning2: Style = new Style(classes :+ "hover-bg--warning-2")
  def backgroundWarning1: Style = new Style(classes :+ "hover-bg--warning-1")

  def backgroundDanger5: Style = new Style(classes :+ "hover-bg--danger-5")
  def backgroundDanger4: Style = new Style(classes :+ "hover-bg--danger-4")
  def backgroundDanger3: Style = new Style(classes :+ "hover-bg--danger-3")
  def backgroundDanger2: Style = new Style(classes :+ "hover-bg--danger-2")
  def backgroundDanger1: Style = new Style(classes :+ "hover-bg--danger-1")

  def backgroundPurple5: Style = new Style(classes :+ "hover-bg--purple-5")
  def backgroundPurple4: Style = new Style(classes :+ "hover-bg--purple-4")
  def backgroundPurple3: Style = new Style(classes :+ "hover-bg--purple-3")
  def backgroundPurple2: Style = new Style(classes :+ "hover-bg--purple-2")
  def backgroundPurple1: Style = new Style(classes :+ "hover-bg--purple-1")

  // border
  def borderAll: Style = new Style(classes :+ "hover-ba")
  def borderGray3: Style = new Style(classes :+ "hover-bc--gray-3")
  def borderGray4: Style = new Style(classes :+ "hover-bc--gray-4")
  def borderGray5: Style = new Style(classes :+ "hover-bc--gray-5")
  def borderPrimary5: Style = new Style(classes :+ "hover-bc--primary-5")
  def borderPrimary3: Style = new Style(classes :+ "hover-bc--primary-3")
  def borderPrimary2: Style = new Style(classes :+ "hover-bc--primary-2")
  def borderSuccess5: Style = new Style(classes :+ "hover-bc--success-5")
  def borderWarning5: Style = new Style(classes :+ "hover-bc--warning-5")
  def borderDanger5: Style = new Style(classes :+ "hover-bc--danger-5")
  def borderPurple5: Style = new Style(classes :+ "hover-bc--purple-5")

  // shadow
  def shadow1Light: Style = new Style(classes :+ "hv-s1l")
  def shadow1Dark: Style = new Style(classes :+ "hv-s1d")

  // Text Decoration
  def underlineNone: Style = new Style(classes :+ "non-underline-hover")
  def underline: Style = new Style(classes :+ "underline-hover")

  // Opacity
  def opacity100: Style = new Style(classes :+ "hv-o100")
}
// scalastyle:on number.of.methods
