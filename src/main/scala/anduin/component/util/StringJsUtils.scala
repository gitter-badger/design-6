// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.util

import japgolly.scalajs.react.vdom.TagMod

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object StringJsUtils {

  def newLineToBr(lines: List[String]): Array[TagMod] = {
    lines.foldRight(Array.empty[TagMod])((l, acc) =>
      if (acc.isEmpty) Array(l) else Array[TagMod](l, <.br) ++ acc)
  }

  def truncate(name: String, maxLength: Int = 40): String = {
    if (name.length > maxLength) s"${name.substring(0, maxLength)} ..." else name
  }
}
