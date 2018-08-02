// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.util

import japgolly.scalajs.react.vdom.html_<^.VdomAttr

object ComponentUtils {

  // QA test id
  val DataTestID: VdomAttr[String] = VdomAttr[String]("data-test-id")

  def name(obj: Object): String = {
    obj.getClass.getSimpleName.split('$').last
  }
}
