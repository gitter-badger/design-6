// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.slate.renderer

import scala.scalajs.js

import japgolly.scalajs.react.{PropsChildren, raw}

import anduin.component.slate.DataUtil

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._

import anduin.scalajs.slate.Slate._
// scalastyle:on underscore.import

private[slate] object LinkRenderer {

  def apply(data: Data, children: js.Object): raw.ReactElement = {
    val href = DataUtil.value(data, "href")
    <.a(^.href := href, PropsChildren.fromRawProps(js.Dynamic.literal(children = children))).rawElement
  }
}
