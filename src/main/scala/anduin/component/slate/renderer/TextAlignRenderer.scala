// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.slate.renderer

import scala.scalajs.js

import japgolly.scalajs.react.{PropsChildren, raw}

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._

import anduin.component.slate._
import anduin.scalajs.slate.Slate._
// scalastyle:on underscore.import

private[slate] object TextAlignRenderer {

  def apply(data: Data, children: js.Object): raw.ReactElement = {
    val childrenEle = PropsChildren.fromRawProps(js.Dynamic.literal(children = children))
    val textAlign = DataUtil.value(data, "textAlign")
    val textAlignAttr = TagMod.when(textAlign.nonEmpty)(^.textAlign := textAlign)

    // Keep the original node type if it supports text alignment
    val originalType = DataUtil.value(data, "originalType")
    val ele = originalType match {
      case ParagraphNode.nodeType => <.p(textAlignAttr, childrenEle)
      case OrderedListNode.nodeType => <.ol(textAlignAttr, childrenEle)
      case UnorderedListNode.nodeType => <.ul(textAlignAttr, childrenEle)
      case ListItemNode.nodeType => <.li(textAlignAttr, childrenEle)
      case _ => <.div(textAlignAttr, childrenEle)
    }
    ele.rawElement
  }
}
