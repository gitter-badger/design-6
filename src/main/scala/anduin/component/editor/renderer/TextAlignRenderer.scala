// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.renderer

import scala.scalajs.js

import japgolly.scalajs.react.{PropsChildren, raw}

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._

import anduin.component.editor._
import anduin.scalajs.slate.Slate._
// scalastyle:on underscore.import

private[editor] object TextAlignRenderer {

  def apply(data: Data, children: js.Object): raw.React.Element = {
    val childrenEle = PropsChildren.fromRawProps(js.Dynamic.literal(children = children))
    val textAlign = DataUtil.value(data, "textAlign")
    val style = StyleParser.getStyleTagMod(data)
    val textAlignAttr = TagMod.when(textAlign.nonEmpty)(^.textAlign := textAlign)

    // Keep the original node type if it supports text alignment
    val originalType = DataUtil.value(data, "originalType")
    val ele = originalType match {
      case ParagraphNode.nodeType     => <.p(style, textAlignAttr, childrenEle)
      case OrderedListNode.nodeType   => <.ol(style, textAlignAttr, childrenEle)
      case UnorderedListNode.nodeType => <.ul(style, textAlignAttr, childrenEle)
      case ListItemNode.nodeType      => <.li(style, textAlignAttr, childrenEle)
      case _                          => <.div(style, textAlignAttr, childrenEle)
    }
    ele.rawElement
  }
}
