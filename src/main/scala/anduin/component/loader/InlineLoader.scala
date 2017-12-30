// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.loader

import japgolly.scalajs.react.vdom.Exports.VdomTagOf

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html._
// scalastyle:on underscore.import

// TODO: replace all manual usages
object InlineLoader {

  // scalastyle:off multiple.string.literals
  def apply(additionalClasses: String*): VdomTagOf[Span] = {
    <.span(
      ^.cls := s"loader-inline ${additionalClasses.mkString(" ")}",
      <.span(^.cls := "item"),
      <.span(^.cls := "item"),
      <.span(^.cls := "item")
    )
  }
  // scalastyle:off multiple.string.literals
}
