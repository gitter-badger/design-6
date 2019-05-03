// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

// scalastyle:off number.of.methods
private[style] final case class BorderColor(classes: List[String] = List.empty) {
  // normal
  def transparent: Style = new Style(classes :+ "border-transparent")
  def currentColor: Style = new Style(classes :+ "border-current")
  def gray9: Style = new Style(classes :+ "border-gray-9")
  def gray8: Style = new Style(classes :+ "border-gray-8")
  def gray7: Style = new Style(classes :+ "border-gray-7")
  def gray6: Style = new Style(classes :+ "border-gray-6")
  def gray5: Style = new Style(classes :+ "border-gray-5")
  def gray4: Style = new Style(classes :+ "border-gray-4")
  def gray3: Style = new Style(classes :+ "border-gray-3")
  def gray2: Style = new Style(classes :+ "border-gray-2")
  def gray1: Style = new Style(classes :+ "border-gray-1")
  def gray0: Style = new Style(classes :+ "border-gray-0")
  def primary5: Style = new Style(classes :+ "border-primary-5")
  def primary4: Style = new Style(classes :+ "border-primary-4")
  def primary3: Style = new Style(classes :+ "border-primary-3")
  def primary2: Style = new Style(classes :+ "border-primary-2")
  def primary1: Style = new Style(classes :+ "border-primary-1")
  def success5: Style = new Style(classes :+ "border-success-5")
  def success4: Style = new Style(classes :+ "border-success-4")
  def success3: Style = new Style(classes :+ "border-success-3")
  def success2: Style = new Style(classes :+ "border-success-2")
  def success1: Style = new Style(classes :+ "border-success-1")
  def warning5: Style = new Style(classes :+ "border-warning-5")
  def warning4: Style = new Style(classes :+ "border-warning-4")
  def warning3: Style = new Style(classes :+ "border-warning-3")
  def warning2: Style = new Style(classes :+ "border-warning-2")
  def warning1: Style = new Style(classes :+ "border-warning-1")
  def danger5: Style = new Style(classes :+ "border-danger-5")
  def danger4: Style = new Style(classes :+ "border-danger-4")
  def danger3: Style = new Style(classes :+ "border-danger-3")
  def danger2: Style = new Style(classes :+ "border-danger-2")
  def danger1: Style = new Style(classes :+ "border-danger-1")
  // hover
  def hoverGray3: Style = new Style(classes :+ "hover:border-gray-3")
  def hoverGray4: Style = new Style(classes :+ "hover:border-gray-4")
  def hoverGray5: Style = new Style(classes :+ "hover:border-gray-5")
  def hoverPrimary5: Style = new Style(classes :+ "hover:border-primary-5")
  def hoverPrimary3: Style = new Style(classes :+ "hover:border-primary-3")
  def hoverPrimary2: Style = new Style(classes :+ "hover:border-primary-2")
  def hoverSuccess5: Style = new Style(classes :+ "hover:border-success-5")
  def hoverWarning5: Style = new Style(classes :+ "hover:border-warning-5")
  def hoverDanger5: Style = new Style(classes :+ "hover:border-danger-5")
  // active
  def activeGray4: Style = new Style(classes :+ "active:border-gray-4")
  def activeGray5: Style = new Style(classes :+ "active:border-gray-5")
  def activeGray7: Style = new Style(classes :+ "active:border-gray-7")
  def activePrimary5: Style = new Style(classes :+ "active:border-primary-5")
  def activeSuccess5: Style = new Style(classes :+ "active:border-success-5")
  def activeWarning5: Style = new Style(classes :+ "active:border-warning-5")
  def activeDanger5: Style = new Style(classes :+ "active:border-danger-5")
  // focus
  def focusPrimary4: Style = new Style(classes :+ "focus:border-primary-4")
  def focusWithinPrimary4: Style = new Style(classes :+ "focus-within:border-primary-4")
}
// scalastyle:on number.of.methods
