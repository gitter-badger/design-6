// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_spacing.css
// scalastyle:off number.of.methods
private[tachyons] final case class Padding(classes: List[String] = List.empty) {

  def all0: Tachyons = new Tachyons(classes :+ "pa0")
  def all1: Tachyons = new Tachyons(classes :+ "pa1")
  def all2: Tachyons = new Tachyons(classes :+ "pa2")
  def all3: Tachyons = new Tachyons(classes :+ "pa3")
  def all4: Tachyons = new Tachyons(classes :+ "pa4")
  def all5: Tachyons = new Tachyons(classes :+ "pa5")
  def all6: Tachyons = new Tachyons(classes :+ "pa6")
  def all7: Tachyons = new Tachyons(classes :+ "pa7")

  def left0: Tachyons = new Tachyons(classes :+ "pl0")
  def left1: Tachyons = new Tachyons(classes :+ "pl1")
  def left2: Tachyons = new Tachyons(classes :+ "pl2")
  def left3: Tachyons = new Tachyons(classes :+ "pl3")
  def left4: Tachyons = new Tachyons(classes :+ "pl4")
  def left5: Tachyons = new Tachyons(classes :+ "pl5")
  def left6: Tachyons = new Tachyons(classes :+ "pl6")
  def left7: Tachyons = new Tachyons(classes :+ "pl7")

  def right0: Tachyons = new Tachyons(classes :+ "pr0")
  def right1: Tachyons = new Tachyons(classes :+ "pr1")
  def right2: Tachyons = new Tachyons(classes :+ "pr2")
  def right3: Tachyons = new Tachyons(classes :+ "pr3")
  def right4: Tachyons = new Tachyons(classes :+ "pr4")
  def right5: Tachyons = new Tachyons(classes :+ "pr5")
  def right6: Tachyons = new Tachyons(classes :+ "pr6")
  def right7: Tachyons = new Tachyons(classes :+ "pr7")

  def bottom0: Tachyons = new Tachyons(classes :+ "pb0")
  def bottom1: Tachyons = new Tachyons(classes :+ "pb1")
  def bottom2: Tachyons = new Tachyons(classes :+ "pb2")
  def bottom3: Tachyons = new Tachyons(classes :+ "pb3")
  def bottom4: Tachyons = new Tachyons(classes :+ "pb4")
  def bottom5: Tachyons = new Tachyons(classes :+ "pb5")
  def bottom6: Tachyons = new Tachyons(classes :+ "pb6")
  def bottom7: Tachyons = new Tachyons(classes :+ "pb7")

  def top0: Tachyons = new Tachyons(classes :+ "pt0")
  def top1: Tachyons = new Tachyons(classes :+ "pt1")
  def top2: Tachyons = new Tachyons(classes :+ "pt2")
  def top3: Tachyons = new Tachyons(classes :+ "pt3")
  def top4: Tachyons = new Tachyons(classes :+ "pt4")
  def top5: Tachyons = new Tachyons(classes :+ "pt5")
  def top6: Tachyons = new Tachyons(classes :+ "pt6")
  def top7: Tachyons = new Tachyons(classes :+ "pt7")

  def vertical0: Tachyons = new Tachyons(classes :+ "pv0")
  def vertical1: Tachyons = new Tachyons(classes :+ "pv1")
  def vertical2: Tachyons = new Tachyons(classes :+ "pv2")
  def vertical3: Tachyons = new Tachyons(classes :+ "pv3")
  def vertical4: Tachyons = new Tachyons(classes :+ "pv4")
  def vertical5: Tachyons = new Tachyons(classes :+ "pv5")
  def vertical6: Tachyons = new Tachyons(classes :+ "pv6")
  def vertical7: Tachyons = new Tachyons(classes :+ "pv7")

  def horizontal0: Tachyons = new Tachyons(classes :+ "ph0")
  def horizontal1: Tachyons = new Tachyons(classes :+ "ph1")
  def horizontal2: Tachyons = new Tachyons(classes :+ "ph2")
  def horizontal3: Tachyons = new Tachyons(classes :+ "ph3")
  def horizontal4: Tachyons = new Tachyons(classes :+ "ph4")
  def horizontal5: Tachyons = new Tachyons(classes :+ "ph5")
  def horizontal6: Tachyons = new Tachyons(classes :+ "ph6")
  def horizontal7: Tachyons = new Tachyons(classes :+ "ph7")
}
// scalastyle:on number.of.methods
