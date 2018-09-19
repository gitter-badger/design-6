// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.truncate

import scala.scalajs.js
import scala.scalajs.js.|

import japgolly.scalajs.react.raw.React.Element

import anduin.scalajs.reacttruncatemarkup.ReactTruncateMarkup

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object TruncateMarkup {

  private type EllipsisType = String | Element | js.Function1[Element, String]

  private type LineHeightType = Double | String

  final class Props(
    val lines: Int = 1,
    val ellipsis: EllipsisType,
    val lineHeight: LineHeightType
  ) extends js.Object

  private val component = JsComponent[Props, Children.Varargs, Null](ReactTruncateMarkup.RawComponent)

  // See https://www.npmjs.com/package/react-truncate-markup
  def apply(
    lines: Int,
    ellipsis: EllipsisType = "...",
    lineHeight: LineHeightType = "auto-detected"
  )(children: VdomNode*): VdomElement = {
    component(
      new Props(
        lines = lines,
        ellipsis = ellipsis,
        lineHeight = lineHeight
      )
    )(children: _*)
  }
}
