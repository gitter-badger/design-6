// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor.renderer

import anduin.component.button.{ButtonLink, ButtonStyle}
import anduin.component.editor.{DataUtil, StyleParser}
import anduin.component.portal.PositionBottomCenter
import anduin.component.portal.popover.Popover
import japgolly.scalajs.react.{PropsChildren, raw}

import scala.scalajs.js

// scalastyle:off underscore.import
import anduin.scalajs.slate.Slate._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[editor] object LinkRenderer {

  def apply(data: Data, children: js.Object, readOnly: Boolean): raw.React.Element = {
    val href = DataUtil.value(data, "href")
    val style = StyleParser.getStyleTagMod(data)
    val link = <.a(style, ^.href := href, PropsChildren.fromRawProps(js.Dynamic.literal(children = children)))

    if (readOnly) {
      link.rawElement
    } else {
      Popover(
        position = PositionBottomCenter,
        targetTag = <.span,
        renderTarget = (toggle, _) => {
          <.span(^.onClick --> toggle, link)
        },
        renderContent = _ => {
          ButtonLink(
            color = ButtonStyle.ColorBlue,
            style = ButtonStyle.StyleLink,
            href = href,
            target = ^.target.blank
          )("Open link")
        }
      )().rawElement
    }
  }
}
