// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.util

import scala.collection.mutable
import scala.scalajs.js

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object Util {

  // This works like `undefOr.toOption` but convert `null` to `None` instead of
  // Some(null). To be specific:
  // A            => Some(A)
  // null         => None
  // js.undefined => None
  def safelyToOption[A](a: js.UndefOr[A]): Option[A] =
    a.toOption.filter(_ != null)

  def getModsFromProps(props: mutable.Map[String, js.Any]): TagMod = {
    props.map(prop => VdomAttr(prop._1) := prop._2).toTagMod
  }
}
