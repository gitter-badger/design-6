// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// scalastyle:off number.of.methods
private[style] final case class Active(classes: List[String] = List.empty) {

  // Color

  def colorGray9: Style = new Style(classes :+ "active-c--gray-9")
  def colorGray8: Style = new Style(classes :+ "active-c--gray-8")
  def colorGray7: Style = new Style(classes :+ "active-c--gray-7")
  def colorGray6: Style = new Style(classes :+ "active-c--gray-6")
  def colorGray5: Style = new Style(classes :+ "active-c--gray-5")
  def colorGray4: Style = new Style(classes :+ "active-c--gray-4")
  def colorGray3: Style = new Style(classes :+ "active-c--gray-3")
  def colorGray2: Style = new Style(classes :+ "active-c--gray-2")
  def colorGray1: Style = new Style(classes :+ "active-c--gray-1")

  def colorWhite: Style = new Style(classes :+ "active-c--white")

  def colorPrimary5: Style = new Style(classes :+ "active-c--primary-5")
  def colorPrimary4: Style = new Style(classes :+ "active-c--primary-4")
  def colorPrimary3: Style = new Style(classes :+ "active-c--primary-3")
  def colorPrimary2: Style = new Style(classes :+ "active-c--primary-2")
  def colorPrimary1: Style = new Style(classes :+ "active-c--primary-1")

  def colorSuccess5: Style = new Style(classes :+ "active-c--success-5")
  def colorSuccess4: Style = new Style(classes :+ "active-c--success-4")
  def colorSuccess3: Style = new Style(classes :+ "active-c--success-3")
  def colorSuccess2: Style = new Style(classes :+ "active-c--success-2")
  def colorSuccess1: Style = new Style(classes :+ "active-c--success-1")

  def colorWarning5: Style = new Style(classes :+ "active-c--warning-5")
  def colorWarning4: Style = new Style(classes :+ "active-c--warning-4")
  def colorWarning3: Style = new Style(classes :+ "active-c--warning-3")
  def colorWarning2: Style = new Style(classes :+ "active-c--warning-2")
  def colorWarning1: Style = new Style(classes :+ "active-c--warning-1")

  def colorDanger5: Style = new Style(classes :+ "active-c--danger-5")
  def colorDanger4: Style = new Style(classes :+ "active-c--danger-4")
  def colorDanger3: Style = new Style(classes :+ "active-c--danger-3")
  def colorDanger2: Style = new Style(classes :+ "active-c--danger-2")
  def colorDanger1: Style = new Style(classes :+ "active-c--danger-1")

  // Background Color

  def backgroundGray9: Style = new Style(classes :+ "active-bg--gray-9")
  def backgroundGray8: Style = new Style(classes :+ "active-bg--gray-8")
  def backgroundGray7: Style = new Style(classes :+ "active-bg--gray-7")
  def backgroundGray6: Style = new Style(classes :+ "active-bg--gray-6")
  def backgroundGray5: Style = new Style(classes :+ "active-bg--gray-5")
  def backgroundGray4: Style = new Style(classes :+ "active-bg--gray-4")
  def backgroundGray3: Style = new Style(classes :+ "active-bg--gray-3")
  def backgroundGray2: Style = new Style(classes :+ "active-bg--gray-2")
  def backgroundGray1: Style = new Style(classes :+ "active-bg--gray-1")

  def backgroundWhite: Style = new Style(classes :+ "active-bg--white")

  def backgroundPrimary5: Style = new Style(classes :+ "active-bg--primary-5")
  def backgroundPrimary4: Style = new Style(classes :+ "active-bg--primary-4")
  def backgroundPrimary3: Style = new Style(classes :+ "active-bg--primary-3")
  def backgroundPrimary2: Style = new Style(classes :+ "active-bg--primary-2")
  def backgroundPrimary1: Style = new Style(classes :+ "active-bg--primary-1")

  def backgroundSuccess5: Style = new Style(classes :+ "active-bg--success-5")
  def backgroundSuccess4: Style = new Style(classes :+ "active-bg--success-4")
  def backgroundSuccess3: Style = new Style(classes :+ "active-bg--success-3")
  def backgroundSuccess2: Style = new Style(classes :+ "active-bg--success-2")
  def backgroundSuccess1: Style = new Style(classes :+ "active-bg--success-1")

  def backgroundWarning5: Style = new Style(classes :+ "active-bg--warning-5")
  def backgroundWarning4: Style = new Style(classes :+ "active-bg--warning-4")
  def backgroundWarning3: Style = new Style(classes :+ "active-bg--warning-3")
  def backgroundWarning2: Style = new Style(classes :+ "active-bg--warning-2")
  def backgroundWarning1: Style = new Style(classes :+ "active-bg--warning-1")

  def backgroundDanger5: Style = new Style(classes :+ "active-bg--danger-5")
  def backgroundDanger4: Style = new Style(classes :+ "active-bg--danger-4")
  def backgroundDanger3: Style = new Style(classes :+ "active-bg--danger-3")
  def backgroundDanger2: Style = new Style(classes :+ "active-bg--danger-2")
  def backgroundDanger1: Style = new Style(classes :+ "active-bg--danger-1")

  /* Shadow */

  def shadowBorderGray4: Style = new Style(classes :+ "active-s-b--gray-4s")
  def shadowBorderPrimary5s: Style = new Style(classes :+ "active-s-b--primary-5s")
  def shadowBorderSuccess5s: Style = new Style(classes :+ "active-s-b--success-5s")
  def shadowBorderWarning5s: Style = new Style(classes :+ "active-s-b--warning-5s")
  def shadowBorderDanger5s: Style = new Style(classes :+ "active-s-b--danger-5s")
}
// scalastyle:on number.of.methods
