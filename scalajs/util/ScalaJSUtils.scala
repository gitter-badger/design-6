// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.util

import scala.collection.mutable
import scala.scalajs.js

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object ScalaJSUtils {

  def jsPropsToTagMod(props: mutable.Map[String, js.Any]): TagMod = {
    // Although "props" comes directly from JS land, ScalaJS sometimes add
    // some internal attributes (like `"bitmap$init$0$2":true`). We need to get
    // rid of them before converting this "props" to a TagMod
    // See also: https://github.com/scala-js/scala-js/issues/2227
    val filtered = props.filterNot(_._1.contains("bitmap$"))
    filtered.map(prop => VdomAttr[Any](prop._1) := prop._2).toTagMod
  }

  // Dealing with null in scala-js
  // - Note that this is different than wrapping the value inside Option's
  //   apply, which returns `Option[A | Null]`. This instead returns
  //   `Option[A]`, allows easier usage
  // - ref: https://github.com/scala-js/scala-js/issues/2344
  @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf")) // scalastyle:ignore
  def jsNullToOption[A](value: js.|[A, Null]): Option[A] = {
    if (value == null) None else Option(value.asInstanceOf[A])
  }
}
