// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.downshift

import scala.scalajs.js

object DownshiftUtils {

  private type Changes[A] = DownshiftStateChanges[A]

  // Because Scala.js does not support ES2015 yet and
  // we cannot "copy" a ScalaJS-defined class
  @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf")) // scalastyle:ignore
  def mergeChanges[A](prev: Changes[A], next: Changes[A]): Changes[A] = {
    val empty = js.Dynamic.literal()
    js.Dynamic.global.Object
      .assign(empty, prev, next)
      .asInstanceOf[Changes[A]]
  }
}
