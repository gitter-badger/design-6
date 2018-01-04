// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor.renderer

import scala.scalajs.js

import japgolly.scalajs.react.{PropsChildren, raw}

import anduin.component.editor.DataUtil

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._

import anduin.scalajs.slate.Slate._
// scalastyle:on underscore.import

private[editor] object LinkRenderer {

  def apply(data: Data, children: js.Object): raw.ReactElement = {
    val href = DataUtil.value(data, "href")
    <.a(^.href := href, PropsChildren.fromRawProps(js.Dynamic.literal(children = children))).rawElement
  }
}
