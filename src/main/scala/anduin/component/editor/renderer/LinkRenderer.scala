// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor.renderer

import scala.scalajs.js

import japgolly.scalajs.react.{PropsChildren, raw}

import anduin.component.editor.{DataUtil, StyleParser}
import anduin.component.portal.{PositionBottom, Tooltip}
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._

import anduin.scalajs.slate.Slate._
// scalastyle:on underscore.import

private[editor] object LinkRenderer {

  def apply(data: Data, children: js.Object): raw.React.Element = {
    val href = DataUtil.value(data, "href")
    val style = StyleParser.getStyleTagMod(data)

    Tooltip(
      position = PositionBottom,
      targetTag = <.span,
      trigger = (t, open, _, _) => {
        TagMod(
          ^.onClick --> open,
          t
        )
      },
      renderTarget = <.a(style, ^.href := href, PropsChildren.fromRawProps(js.Dynamic.literal(children = children))),
      renderContent = () => {
        <.a(
          ^.cls := "link",
          Style.color.white,
          ^.href := href,
          ^.target.blank,
          "Open link"
        )
      }
    )().rawElement
  }
}
