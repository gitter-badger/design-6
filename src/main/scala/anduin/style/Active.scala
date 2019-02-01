// Copyright (C) 2014-2019 Anduin Transactions Inc.

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

  def colorPurple5: Style = new Style(classes :+ "active-c--purple-5")
  def colorPurple4: Style = new Style(classes :+ "active-c--purple-4")
  def colorPurple3: Style = new Style(classes :+ "active-c--purple-3")
  def colorPurple2: Style = new Style(classes :+ "active-c--purple-2")
  def colorPurple1: Style = new Style(classes :+ "active-c--purple-1")

  // shadow
  def shadow1Light: Style = new Style(classes :+ "a-s1l")
  def shadow1Dark: Style = new Style(classes :+ "a-s1d")
}
// scalastyle:on number.of.methods
