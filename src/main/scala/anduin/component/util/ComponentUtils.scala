// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.util

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object ComponentUtils {

  val VoidHref = ^.href := "javascript: void(0);"

  def name(obj: Object): String = {
    obj.getClass.getSimpleName.split('$').last
  }
}
