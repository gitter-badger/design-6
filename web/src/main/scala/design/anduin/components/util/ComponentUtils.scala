// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.components.util

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object ComponentUtils {

  def testId(obj: Object, id: String): TagMod = {
    VdomAttr("data-test-id") := s"${obj.getClass.getSimpleName}-$id"
  }

  def name(obj: Object): String = {
    obj.getClass.getSimpleName.split('$').last
  }
}
