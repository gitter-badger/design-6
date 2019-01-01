// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

// scalastyle:off number.of.methods
private[style] final case class Margin(classes: List[String] = List.empty) {

  def all0: Style = new Style(classes :+ "ma0")
  def all4: Style = new Style(classes :+ "ma1")
  def all8: Style = new Style(classes :+ "ma2")
  def all12: Style = new Style(classes :+ "ma3")
  def all16: Style = new Style(classes :+ "ma4")
  def all20: Style = new Style(classes :+ "ma5")
  def all24: Style = new Style(classes :+ "ma6")
  def all32: Style = new Style(classes :+ "ma7")
  def allAuto: Style = new Style(classes :+ "m-aa")

  def left0: Style = new Style(classes :+ "ml0")
  def left4: Style = new Style(classes :+ "ml1")
  def left8: Style = new Style(classes :+ "ml2")
  def left12: Style = new Style(classes :+ "ml3")
  def left16: Style = new Style(classes :+ "ml4")
  def left20: Style = new Style(classes :+ "ml5")
  def left24: Style = new Style(classes :+ "ml6")
  def left32: Style = new Style(classes :+ "ml7")
  def leftAuto: Style = new Style(classes :+ "m-la")

  def right0: Style = new Style(classes :+ "mr0")
  def right4: Style = new Style(classes :+ "mr1")
  def right8: Style = new Style(classes :+ "mr2")
  def right12: Style = new Style(classes :+ "mr3")
  def right16: Style = new Style(classes :+ "mr4")
  def right20: Style = new Style(classes :+ "mr5")
  def right24: Style = new Style(classes :+ "mr6")
  def right32: Style = new Style(classes :+ "mr7")
  def rightAuto: Style = new Style(classes :+ "m-ra")

  def bottom0: Style = new Style(classes :+ "mb0")
  def bottom4: Style = new Style(classes :+ "mb1")
  def bottom8: Style = new Style(classes :+ "mb2")
  def bottom12: Style = new Style(classes :+ "mb3")
  def bottom16: Style = new Style(classes :+ "mb4")
  def bottom20: Style = new Style(classes :+ "mb5")
  def bottom24: Style = new Style(classes :+ "mb6")
  def bottom32: Style = new Style(classes :+ "mb7")

  def top0: Style = new Style(classes :+ "mt0")
  def top4: Style = new Style(classes :+ "mt1")
  def top8: Style = new Style(classes :+ "mt2")
  def top12: Style = new Style(classes :+ "mt3")
  def top16: Style = new Style(classes :+ "mt4")
  def top20: Style = new Style(classes :+ "mt5")
  def top24: Style = new Style(classes :+ "mt6")
  def top32: Style = new Style(classes :+ "mt7")

  def ver0: Style = new Style(classes :+ "mv0")
  def ver4: Style = new Style(classes :+ "mv1")
  def ver8: Style = new Style(classes :+ "mv2")
  def ver12: Style = new Style(classes :+ "mv3")
  def ver16: Style = new Style(classes :+ "mv4")
  def ver20: Style = new Style(classes :+ "mv5")
  def ver24: Style = new Style(classes :+ "mv6")
  def ver32: Style = new Style(classes :+ "mv7")
  def verAuto: Style = new Style(classes :+ "m-va")

  def hor0: Style = new Style(classes :+ "mh0")
  def hor4: Style = new Style(classes :+ "mh1")
  def hor8: Style = new Style(classes :+ "mh2")
  def hor12: Style = new Style(classes :+ "mh3")
  def hor16: Style = new Style(classes :+ "mh4")
  def hor20: Style = new Style(classes :+ "mh5")
  def hor24: Style = new Style(classes :+ "mh6")
  def hor32: Style = new Style(classes :+ "mh7")
  def horAuto: Style = new Style(classes :+ "m-ha")

}
// scalastyle:on number.of.methods
