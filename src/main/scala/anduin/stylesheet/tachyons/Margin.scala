// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// See https://github.com/tachyons-css/tachyons/blob/master/src/_spacing.css
// scalastyle:off number.of.methods
private[tachyons] final case class Margin(classes: List[String] = List.empty) {

  def all0: Tachyons = new Tachyons(classes :+ "ma0")
  def all1: Tachyons = new Tachyons(classes :+ "ma1")
  def all2: Tachyons = new Tachyons(classes :+ "ma2")
  def all4: Tachyons = new Tachyons(classes :+ "ma4")
  def all6: Tachyons = new Tachyons(classes :+ "ma6")
  def all7: Tachyons = new Tachyons(classes :+ "ma7")

  def left0: Tachyons = new Tachyons(classes :+ "ml0")
  def left1: Tachyons = new Tachyons(classes :+ "ml1")
  def left2: Tachyons = new Tachyons(classes :+ "ml2")
  def left4: Tachyons = new Tachyons(classes :+ "ml4")
  def left6: Tachyons = new Tachyons(classes :+ "ml6")
  def left7: Tachyons = new Tachyons(classes :+ "ml7")

  def right0: Tachyons = new Tachyons(classes :+ "mr0")
  def right1: Tachyons = new Tachyons(classes :+ "mr1")
  def right2: Tachyons = new Tachyons(classes :+ "mr2")
  def right4: Tachyons = new Tachyons(classes :+ "mr4")
  def right6: Tachyons = new Tachyons(classes :+ "mr6")
  def right7: Tachyons = new Tachyons(classes :+ "mr7")

  def bottom0: Tachyons = new Tachyons(classes :+ "mb0")
  def bottom1: Tachyons = new Tachyons(classes :+ "mb1")
  def bottom2: Tachyons = new Tachyons(classes :+ "mb2")
  def bottom4: Tachyons = new Tachyons(classes :+ "mb4")
  def bottom6: Tachyons = new Tachyons(classes :+ "mb6")
  def bottom7: Tachyons = new Tachyons(classes :+ "mb7")

  def top0: Tachyons = new Tachyons(classes :+ "mt0")
  def top1: Tachyons = new Tachyons(classes :+ "mt1")
  def top2: Tachyons = new Tachyons(classes :+ "mt2")
  def top4: Tachyons = new Tachyons(classes :+ "mt4")
  def top6: Tachyons = new Tachyons(classes :+ "mt6")
  def top7: Tachyons = new Tachyons(classes :+ "mt7")

  def vertical0: Tachyons = new Tachyons(classes :+ "mv0")
  def vertical1: Tachyons = new Tachyons(classes :+ "mv1")
  def vertical2: Tachyons = new Tachyons(classes :+ "mv2")
  def vertical4: Tachyons = new Tachyons(classes :+ "mv4")
  def vertical6: Tachyons = new Tachyons(classes :+ "mv6")
  def vertical7: Tachyons = new Tachyons(classes :+ "mv7")

  def horizontal0: Tachyons = new Tachyons(classes :+ "mh0")
  def horizontal1: Tachyons = new Tachyons(classes :+ "mh1")
  def horizontal2: Tachyons = new Tachyons(classes :+ "mh2")
  def horizontal4: Tachyons = new Tachyons(classes :+ "mh4")
  def horizontal6: Tachyons = new Tachyons(classes :+ "mh6")
  def horizontal7: Tachyons = new Tachyons(classes :+ "mh7")
}
// scalastyle:on number.of.methods
