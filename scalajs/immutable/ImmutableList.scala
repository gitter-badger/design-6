// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.immutable

import scala.scalajs.js

@js.native
trait ImmutableList[T <: js.Object] extends js.Object {
  def some(predicate: js.Function1[T, Boolean]) // linter:ignore UnusedParameter
    : Boolean = js.native

  def filter(predicate: js.Function1[T, Boolean]) // linter:ignore UnusedParameter
    : ImmutableList[T] = js.native

  def first(): T = js.native

  def toArray(): js.Array[T] = js.native
}
