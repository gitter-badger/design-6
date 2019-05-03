// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.utils

import anduin.component.editor.LinkNode
import anduin.scalajs.immutable.ImmutableList
import anduin.scalajs.slate.Slate.Value

private[editor] object SlateUtil {

  def hasUndo(value: Value): Boolean = {
    value.data.get("undos").fold(false) {
      case list: ImmutableList[_] => list.nonEmpty
      case _                      => false
    }
  }

  def hasRedo(value: Value): Boolean = {
    value.data.get("redos").fold(false) {
      case list: ImmutableList[_] => list.nonEmpty
      case _                      => false
    }
  }

  def hasLinks(value: Value): Boolean = {
    value.inlines.exists(_.inlineType == LinkNode.nodeType)
  }
}
