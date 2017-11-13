// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_negative-margins.css
// scalastyle:off number.of.methods
private[tachyons] final case class NegativeMargin(classes: List[String] = List.empty) {

  def all1: Tachyons = new Tachyons(classes :+ "na1")
  def all2: Tachyons = new Tachyons(classes :+ "na2")
  def all3: Tachyons = new Tachyons(classes :+ "na3")
  def all4: Tachyons = new Tachyons(classes :+ "na4")
  def all5: Tachyons = new Tachyons(classes :+ "na5")
  def all6: Tachyons = new Tachyons(classes :+ "na6")
  def all7: Tachyons = new Tachyons(classes :+ "na7")

  def left1: Tachyons = new Tachyons(classes :+ "nl1")
  def left2: Tachyons = new Tachyons(classes :+ "nl2")
  def left3: Tachyons = new Tachyons(classes :+ "nl3")
  def left4: Tachyons = new Tachyons(classes :+ "nl4")
  def left5: Tachyons = new Tachyons(classes :+ "nl5")
  def left6: Tachyons = new Tachyons(classes :+ "nl6")
  def left7: Tachyons = new Tachyons(classes :+ "nl7")

  def right1: Tachyons = new Tachyons(classes :+ "nr1")
  def right2: Tachyons = new Tachyons(classes :+ "nr2")
  def right3: Tachyons = new Tachyons(classes :+ "nr3")
  def right4: Tachyons = new Tachyons(classes :+ "nr4")
  def right5: Tachyons = new Tachyons(classes :+ "nr5")
  def right6: Tachyons = new Tachyons(classes :+ "nr6")
  def right7: Tachyons = new Tachyons(classes :+ "nr7")

  def bottom1: Tachyons = new Tachyons(classes :+ "nb1")
  def bottom2: Tachyons = new Tachyons(classes :+ "nb2")
  def bottom3: Tachyons = new Tachyons(classes :+ "nb3")
  def bottom4: Tachyons = new Tachyons(classes :+ "nb4")
  def bottom5: Tachyons = new Tachyons(classes :+ "nb5")
  def bottom6: Tachyons = new Tachyons(classes :+ "nb6")
  def bottom7: Tachyons = new Tachyons(classes :+ "nb7")

  def top1: Tachyons = new Tachyons(classes :+ "nt1")
  def top2: Tachyons = new Tachyons(classes :+ "nt2")
  def top3: Tachyons = new Tachyons(classes :+ "nt3")
  def top4: Tachyons = new Tachyons(classes :+ "nt4")
  def top5: Tachyons = new Tachyons(classes :+ "nt5")
  def top6: Tachyons = new Tachyons(classes :+ "nt6")
  def top7: Tachyons = new Tachyons(classes :+ "nt7")
}
// scalastyle:on number.of.methods
