// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor.renderer

import scala.scalajs.js

import japgolly.scalajs.react.{raw, PropsChildren}

import anduin.component.editor.{DataUtil, StyleParser}

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._

import anduin.scalajs.slate.Slate._
// scalastyle:on underscore.import

private[editor] object LinkRenderer {

  def apply(data: Data, children: js.Object): raw.React.Element = {
    val href = DataUtil.value(data, "href")
    val style = StyleParser.getStyleTagMod(data)
    <.a(style, ^.href := href, PropsChildren.fromRawProps(js.Dynamic.literal(children = children))).rawElement
  }
}
