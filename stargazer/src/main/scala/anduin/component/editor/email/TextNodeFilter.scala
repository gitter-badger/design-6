// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.email

import org.scalajs.dom.raw.{Node, NodeFilter}

import anduin.scalajs.dom.NodeFilterAlias

private[email] class TextNodeFilter extends NodeFilterAlias {
  override def acceptNode(node: Node): Int = {
    if (node.nodeType == Node.TEXT_NODE) {
      NodeFilter.FILTER_ACCEPT
    } else {
      NodeFilter.FILTER_SKIP
    }
  }
}
