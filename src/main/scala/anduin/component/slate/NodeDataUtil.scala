// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.slate

import anduin.scalajs.slate.Slate.Node

private[slate] object NodeDataUtil {

  def value(node: Node, key: String, default: String = ""): String = {
    node.data.get(key).toOption.map(_.asInstanceOf[String]).getOrElse(default)
  }
}
