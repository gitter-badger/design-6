// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor

import anduin.scalajs.slate.Slate.Data

private[editor] object DataUtil {

  def value(data: Data, key: String, default: String = ""): String = {
    data
      .get(key)
      .map { d =>
        val any: Any = d
        any match {
          case s: String => s
          case _         => default
        }
      }
      .getOrElse(default)
  }
}
