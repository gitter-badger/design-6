// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.immutable

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("immutable", "Set")
@js.native
class ImmutableSet[T <: js.Object] extends js.Object {
  def toArray(): js.Array[T] = js.native
}

object ImmutableSet {
  implicit def toSet[T <: js.Object](immutableSet: ImmutableSet[T]): Set[T] = immutableSet.toArray().toSet
}
