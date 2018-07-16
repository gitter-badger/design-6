// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.immutable

import scala.scalajs.js

@js.native
trait ImmutableSet[T <: js.Object] extends js.Object {
  def toArray(): js.Array[T] = js.native
}

object ImmutableSet {
  implicit def toSet[T <: js.Object](immutableSet: ImmutableSet[T]): Set[T] = immutableSet.toArray().toSet
}
