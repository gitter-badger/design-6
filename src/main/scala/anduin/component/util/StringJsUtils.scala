// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.util

import japgolly.scalajs.react.vdom.TagMod

import anduin.component.portal.{Position, PositionTop, Tooltip}
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object StringJsUtils {

  def newLineToBr(lines: List[String]): Array[TagMod] = {
    lines.foldRight(Array.empty[TagMod])((l, acc) => if (acc.isEmpty) Array(l) else Array[TagMod](l, <.br) ++ acc)
  }

  def truncate(name: String, maxLength: Int = 40): String = {
    if (name.length > maxLength) s"${name.substring(0, maxLength)}…" else name
  }

  def splitBySpaces(s: String): Array[String] = {
    s.split("\\s+")
  }

  def splitWords(s: String): List[String] = {
    splitBySpaces(s).map(_.trim).filter(_.nonEmpty).toList
  }

  def truncateWordBased(s: String, maxNumWord: Int = 15): String = {
    splitWords(s).slice(0, maxNumWord).mkString(" ")
  }

  def truncateWithTooltip(string: String, maxLength: Int = 40, position: Position = PositionTop): VdomElement = {
    if (string.length > maxLength) {
      <.div(
        Tooltip(
          position = position,
          renderTarget = <.span(Style.color.gray8, StringJsUtils.truncate(string, maxLength)),
          renderContent = () => <.div(Style.maxWidth.px512.wordBreak.break, string)
        )()
      )
    } else {
      ReactFragment(string)
    }
  }
}
