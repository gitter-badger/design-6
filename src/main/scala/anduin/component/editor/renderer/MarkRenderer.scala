// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.renderer

import scala.scalajs.js

import japgolly.scalajs.react.{PropsChildren, raw}

import anduin.scalajs.slate.Slate.Mark

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._

import anduin.component.editor._
// scalastyle:on underscore.import

private[editor] object MarkRenderer {

  def apply(markType: String, children: js.Object): raw.React.Element = {
    val childrenEle = PropsChildren.fromRawProps(js.Dynamic.literal(children = children))
    markType match {
      case BoldNode.nodeType          => <.strong(childrenEle).rawElement
      case ItalicNode.nodeType        => <.em(childrenEle).rawElement
      case UnderlineNode.nodeType     => <.u(childrenEle).rawElement
      case StrikeThroughNode.nodeType => <.del(childrenEle).rawElement
    }
  }

  def apply(mark: Mark, children: js.Object): raw.React.Element = apply(mark.markType, children)
}
