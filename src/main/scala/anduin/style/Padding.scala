// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

// scalastyle:off number.of.methods
private[style] final case class Padding(classes: List[String] = List.empty) {

  def all0: Style = new Style(classes :+ "pa0")
  def all4: Style = new Style(classes :+ "pa1")
  def all8: Style = new Style(classes :+ "pa2")
  def all12: Style = new Style(classes :+ "pa3")
  def all16: Style = new Style(classes :+ "pa4")
  def all20: Style = new Style(classes :+ "pa5")
  def all24: Style = new Style(classes :+ "pa6")
  def all32: Style = new Style(classes :+ "pa7")

  def left0: Style = new Style(classes :+ "pl0")
  def left4: Style = new Style(classes :+ "pl1")
  def left8: Style = new Style(classes :+ "pl2")
  def left12: Style = new Style(classes :+ "pl3")
  def left16: Style = new Style(classes :+ "pl4")
  def left20: Style = new Style(classes :+ "pl5")
  def left24: Style = new Style(classes :+ "pl6")
  def left32: Style = new Style(classes :+ "pl7")

  def right0: Style = new Style(classes :+ "pr0")
  def right4: Style = new Style(classes :+ "pr1")
  def right8: Style = new Style(classes :+ "pr2")
  def right12: Style = new Style(classes :+ "pr3")
  def right16: Style = new Style(classes :+ "pr4")
  def right20: Style = new Style(classes :+ "pr5")
  def right24: Style = new Style(classes :+ "pr6")
  def right32: Style = new Style(classes :+ "pr7")

  def bottom0: Style = new Style(classes :+ "pb0")
  def bottom4: Style = new Style(classes :+ "pb1")
  def bottom8: Style = new Style(classes :+ "pb2")
  def bottom12: Style = new Style(classes :+ "pb3")
  def bottom16: Style = new Style(classes :+ "pb4")
  def bottom20: Style = new Style(classes :+ "pb5")
  def bottom24: Style = new Style(classes :+ "pb6")
  def bottom32: Style = new Style(classes :+ "pb7")

  def top0: Style = new Style(classes :+ "pt0")
  def top4: Style = new Style(classes :+ "pt1")
  def top8: Style = new Style(classes :+ "pt2")
  def top12: Style = new Style(classes :+ "pt3")
  def top16: Style = new Style(classes :+ "pt4")
  def top20: Style = new Style(classes :+ "pt5")
  def top24: Style = new Style(classes :+ "pt6")
  def top32: Style = new Style(classes :+ "pt7")

  def ver0: Style = new Style(classes :+ "pv0")
  def ver4: Style = new Style(classes :+ "pv1")
  def ver8: Style = new Style(classes :+ "pv2")
  def ver12: Style = new Style(classes :+ "pv3")
  def ver16: Style = new Style(classes :+ "pv4")
  def ver20: Style = new Style(classes :+ "pv5")
  def ver24: Style = new Style(classes :+ "pv6")
  def ver32: Style = new Style(classes :+ "pv7")

  def hor0: Style = new Style(classes :+ "ph0")
  def hor4: Style = new Style(classes :+ "ph1")
  def hor8: Style = new Style(classes :+ "ph2")
  def hor12: Style = new Style(classes :+ "ph3")
  def hor16: Style = new Style(classes :+ "ph4")
  def hor20: Style = new Style(classes :+ "ph5")
  def hor24: Style = new Style(classes :+ "ph6")
  def hor32: Style = new Style(classes :+ "ph7")
}
// scalastyle:on number.of.methods
