// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.components.truncate

import scala.scalajs.js
import scala.scalajs.js.|

import japgolly.scalajs.react.raw.React.Element

import design.anduin.facades.reacttruncatemarkup.ReactTruncateMarkup

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object TruncateMarkup {

  private type EllipsisType = String | Element | js.Function1[Element, String]

  final class Props(
    val lines: Int,
    val ellipsis: EllipsisType
  ) extends js.Object

  private val component = JsComponent[Props, Children.Varargs, Null](ReactTruncateMarkup.RawComponent)

  // See https://www.npmjs.com/package/react-truncate-markup
  def apply(
    lines: Int = 1,
    ellipsis: EllipsisType = "...",
    targetTag: HtmlTag = <.div,
    renderTarget: VdomNode
  )(): VdomElement = {
    // react-truncate-markup only allows to use 1 element passed as children
    // so, we have to wrap inside another tag
    // Passing (children: VdomNode) doesn't work even it can be built successfully
    component(
      new Props(
        lines = lines,
        ellipsis = ellipsis
      )
    )(targetTag(renderTarget))
  }
}
