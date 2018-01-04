// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor

import anduin.scalajs.slate.Slate.Data

private[editor] object DataUtil {

  def value(data: Data, key: String, default: String = ""): String = {
    data.get(key).toOption.map(_.asInstanceOf[String]).getOrElse(default)
  }
}
