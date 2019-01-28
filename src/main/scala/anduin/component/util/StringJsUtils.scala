// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.util

import anduin.component.tooltip.Tooltip
import anduin.component.portal.{Position, PositionTopCenter}
import anduin.style.Style
import japgolly.scalajs.react.React
import japgolly.scalajs.react.vdom.TagMod

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

  def truncateWithTooltip(string: String, maxLength: Int = 40, position: Position = PositionTopCenter): VdomElement = {
    if (string.length > maxLength) {
      <.div(
        Tooltip(
          position = position,
          renderTarget = <.span(Style.color.gray8, StringJsUtils.truncate(string, maxLength)),
          renderContent = () => <.div(Style.maxWidth.px512.wordBreak.break, string)
        )()
      )
    } else {
      React.Fragment(string)
    }
  }

  // Check if a given `string` is empty or only consists of whitespace (including new line, tab characters)
  def isEmptyOrWhitespace(string: String): Boolean = {
    string.isEmpty || string.forall { char =>
      Character.isWhitespace(char)
    }
  }
}
