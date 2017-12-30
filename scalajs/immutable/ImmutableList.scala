// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.immutable

import scala.scalajs.js

@js.native
trait ImmutableList[T <: js.Object] extends js.Object {
  def some(predicate: js.Function1[T, Boolean]): Boolean = js.native // linter:ignore UnusedParameter
  def toArray(): js.Array[T] = js.native
}
