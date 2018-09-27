// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.util

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
