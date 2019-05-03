// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

private[style] final case class WhiteSpace(classes: List[String] = List.empty) {
  def normal: Style = new Style(classes :+ "whitespace-normal")
  def noWrap: Style = new Style(classes :+ "whitespace-no-wrap")
  def pre: Style = new Style(classes :+ "whitespace-pre")
  def preWrap: Style = new Style(classes :+ "whitespace-pre-wrap")
  def preLine: Style = new Style(classes :+ "whitespace-pre-line")
}
