// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.editor

import scala.scalajs.js

object DecoratorUtils {

  // Try to get the attribute value of an entity data which is returned by `DraftEntityInstance.getData()`
  def getAttribute(data: js.Dynamic, attr: String, defaultValue: String = ""): String = {
    // Because `data.selectDynamic(attr)` can return `null`, we wrap it in an `Option`
    Option(data.selectDynamic(attr))
      .flatMap(_.asInstanceOf[js.UndefOr[String]].toOption)
      .getOrElse(defaultValue)
  }
}
