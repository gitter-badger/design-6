// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// scalastyle:off number.of.methods
private[style] final case class Hover(classes: List[String] = List.empty) {

  // Color

  def colorGray9: Style = new Style(classes :+ "hover-c--gray9")
  def colorGray8: Style = new Style(classes :+ "hover-c--gray8")
  def colorGray7: Style = new Style(classes :+ "hover-c--gray7")
  def colorGray6: Style = new Style(classes :+ "hover-c--gray6")
  def colorGray5: Style = new Style(classes :+ "hover-c--gray5")
  def colorGray4: Style = new Style(classes :+ "hover-c--gray4")
  def colorGray3: Style = new Style(classes :+ "hover-c--gray3")
  def colorGray2: Style = new Style(classes :+ "hover-c--gray2")
  def colorGray1: Style = new Style(classes :+ "hover-c--gray1")

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

  // Border Color

  def borderGray9: Style = new Style(classes :+ "hover-b--gray-9")
  def borderGray8: Style = new Style(classes :+ "hover-b--gray-8")
  def borderGray7: Style = new Style(classes :+ "hover-b--gray-7")
  def borderGray6: Style = new Style(classes :+ "hover-b--gray-6")
  def borderGray5: Style = new Style(classes :+ "hover-b--gray-5")
  def borderGray4: Style = new Style(classes :+ "hover-b--gray-4")
  def borderGray3: Style = new Style(classes :+ "hover-b--gray-3")
  def borderGray2: Style = new Style(classes :+ "hover-b--gray-2")
  def borderGray1: Style = new Style(classes :+ "hover-b--gray-1")

  def borderWhite: Style = new Style(classes :+ "hover-b--white")

  def borderPrimary5: Style = new Style(classes :+ "hover-b--primary-5")
  def borderPrimary4: Style = new Style(classes :+ "hover-b--primary-4")
  def borderPrimary3: Style = new Style(classes :+ "hover-b--primary-3")
  def borderPrimary2: Style = new Style(classes :+ "hover-b--primary-2")
  def borderPrimary1: Style = new Style(classes :+ "hover-b--primary-1")

  def borderSuccess5: Style = new Style(classes :+ "hover-b--success-5")
  def borderSuccess4: Style = new Style(classes :+ "hover-b--success-4")
  def borderSuccess3: Style = new Style(classes :+ "hover-b--success-3")
  def borderSuccess2: Style = new Style(classes :+ "hover-b--success-2")
  def borderSuccess1: Style = new Style(classes :+ "hover-b--success-1")

  def borderWarning5: Style = new Style(classes :+ "hover-b--warning-5")
  def borderWarning4: Style = new Style(classes :+ "hover-b--warning-4")
  def borderWarning3: Style = new Style(classes :+ "hover-b--warning-3")
  def borderWarning2: Style = new Style(classes :+ "hover-b--warning-2")
  def borderWarning1: Style = new Style(classes :+ "hover-b--warning-1")

  def borderDanger5: Style = new Style(classes :+ "hover-b--danger-5")
  def borderDanger4: Style = new Style(classes :+ "hover-b--danger-4")
  def borderDanger3: Style = new Style(classes :+ "hover-b--danger-3")
  def borderDanger2: Style = new Style(classes :+ "hover-b--danger-2")
  def borderDanger1: Style = new Style(classes :+ "hover-b--danger-1")
}
// scalastyle:on number.of.methods
