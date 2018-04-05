// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.style

// See https://github.com/tachyons-css/tachyons/blob/master/src/_spacing.css
// scalastyle:off number.of.methods
private[style] final case class Margin(classes: List[String] = List.empty) {

  def all0: Style = new Style(classes :+ "ma0")
  def all1: Style = new Style(classes :+ "ma1")
  def all2: Style = new Style(classes :+ "ma2")
  def all3: Style = new Style(classes :+ "ma3")
  def all4: Style = new Style(classes :+ "ma4")
  def all5: Style = new Style(classes :+ "ma5")
  def all6: Style = new Style(classes :+ "ma6")
  def all7: Style = new Style(classes :+ "ma7")

  def left0: Style = new Style(classes :+ "ml0")
  def left1: Style = new Style(classes :+ "ml1")
  def left2: Style = new Style(classes :+ "ml2")
  def left3: Style = new Style(classes :+ "ml3")
  def left4: Style = new Style(classes :+ "ml4")
  def left5: Style = new Style(classes :+ "ml5")
  def left6: Style = new Style(classes :+ "ml6")
  def left7: Style = new Style(classes :+ "ml7")

  def right0: Style = new Style(classes :+ "mr0")
  def right1: Style = new Style(classes :+ "mr1")
  def right2: Style = new Style(classes :+ "mr2")
  def right3: Style = new Style(classes :+ "mr3")
  def right4: Style = new Style(classes :+ "mr4")
  def right5: Style = new Style(classes :+ "mr5")
  def right6: Style = new Style(classes :+ "mr6")
  def right7: Style = new Style(classes :+ "mr7")

  def bottom0: Style = new Style(classes :+ "mb0")
  def bottom1: Style = new Style(classes :+ "mb1")
  def bottom2: Style = new Style(classes :+ "mb2")
  def bottom3: Style = new Style(classes :+ "mb3")
  def bottom4: Style = new Style(classes :+ "mb4")
  def bottom5: Style = new Style(classes :+ "mb5")
  def bottom6: Style = new Style(classes :+ "mb6")
  def bottom7: Style = new Style(classes :+ "mb7")

  def top0: Style = new Style(classes :+ "mt0")
  def top1: Style = new Style(classes :+ "mt1")
  def top2: Style = new Style(classes :+ "mt2")
  def top3: Style = new Style(classes :+ "mt3")
  def top4: Style = new Style(classes :+ "mt4")
  def top5: Style = new Style(classes :+ "mt5")
  def top6: Style = new Style(classes :+ "mt6")
  def top7: Style = new Style(classes :+ "mt7")

  def vertical0: Style = new Style(classes :+ "mv0")
  def vertical1: Style = new Style(classes :+ "mv1")
  def vertical2: Style = new Style(classes :+ "mv2")
  def vertical3: Style = new Style(classes :+ "mv3")
  def vertical4: Style = new Style(classes :+ "mv4")
  def vertical5: Style = new Style(classes :+ "mv5")
  def vertical6: Style = new Style(classes :+ "mv6")
  def vertical7: Style = new Style(classes :+ "mv7")

  def horizontal0: Style = new Style(classes :+ "mh0")
  def horizontal1: Style = new Style(classes :+ "mh1")
  def horizontal2: Style = new Style(classes :+ "mh2")
  def horizontal3: Style = new Style(classes :+ "mh3")
  def horizontal4: Style = new Style(classes :+ "mh4")
  def horizontal5: Style = new Style(classes :+ "mh5")
  def horizontal6: Style = new Style(classes :+ "mh6")
  def horizontal7: Style = new Style(classes :+ "mh7")
}
// scalastyle:on number.of.methods
