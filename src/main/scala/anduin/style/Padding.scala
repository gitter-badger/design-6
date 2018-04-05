// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_spacing.css
// scalastyle:off number.of.methods
private[style] final case class Padding(classes: List[String] = List.empty) {

  def all0: Style = new Style(classes :+ "pa0")
  def all1: Style = new Style(classes :+ "pa1")
  def all2: Style = new Style(classes :+ "pa2")
  def all3: Style = new Style(classes :+ "pa3")
  def all4: Style = new Style(classes :+ "pa4")
  def all5: Style = new Style(classes :+ "pa5")
  def all6: Style = new Style(classes :+ "pa6")
  def all7: Style = new Style(classes :+ "pa7")

  def left0: Style = new Style(classes :+ "pl0")
  def left1: Style = new Style(classes :+ "pl1")
  def left2: Style = new Style(classes :+ "pl2")
  def left3: Style = new Style(classes :+ "pl3")
  def left4: Style = new Style(classes :+ "pl4")
  def left5: Style = new Style(classes :+ "pl5")
  def left6: Style = new Style(classes :+ "pl6")
  def left7: Style = new Style(classes :+ "pl7")

  def right0: Style = new Style(classes :+ "pr0")
  def right1: Style = new Style(classes :+ "pr1")
  def right2: Style = new Style(classes :+ "pr2")
  def right3: Style = new Style(classes :+ "pr3")
  def right4: Style = new Style(classes :+ "pr4")
  def right5: Style = new Style(classes :+ "pr5")
  def right6: Style = new Style(classes :+ "pr6")
  def right7: Style = new Style(classes :+ "pr7")

  def bottom0: Style = new Style(classes :+ "pb0")
  def bottom1: Style = new Style(classes :+ "pb1")
  def bottom2: Style = new Style(classes :+ "pb2")
  def bottom3: Style = new Style(classes :+ "pb3")
  def bottom4: Style = new Style(classes :+ "pb4")
  def bottom5: Style = new Style(classes :+ "pb5")
  def bottom6: Style = new Style(classes :+ "pb6")
  def bottom7: Style = new Style(classes :+ "pb7")

  def top0: Style = new Style(classes :+ "pt0")
  def top1: Style = new Style(classes :+ "pt1")
  def top2: Style = new Style(classes :+ "pt2")
  def top3: Style = new Style(classes :+ "pt3")
  def top4: Style = new Style(classes :+ "pt4")
  def top5: Style = new Style(classes :+ "pt5")
  def top6: Style = new Style(classes :+ "pt6")
  def top7: Style = new Style(classes :+ "pt7")

  def vertical0: Style = new Style(classes :+ "pv0")
  def vertical1: Style = new Style(classes :+ "pv1")
  def vertical2: Style = new Style(classes :+ "pv2")
  def vertical3: Style = new Style(classes :+ "pv3")
  def vertical4: Style = new Style(classes :+ "pv4")
  def vertical5: Style = new Style(classes :+ "pv5")
  def vertical6: Style = new Style(classes :+ "pv6")
  def vertical7: Style = new Style(classes :+ "pv7")

  def horizontal0: Style = new Style(classes :+ "ph0")
  def horizontal1: Style = new Style(classes :+ "ph1")
  def horizontal2: Style = new Style(classes :+ "ph2")
  def horizontal3: Style = new Style(classes :+ "ph3")
  def horizontal4: Style = new Style(classes :+ "ph4")
  def horizontal5: Style = new Style(classes :+ "ph5")
  def horizontal6: Style = new Style(classes :+ "ph6")
  def horizontal7: Style = new Style(classes :+ "ph7")
}
// scalastyle:on number.of.methods
