// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.immutable

import scala.scalajs.js

@js.native
trait ImmutableList[T <: js.Object] extends js.Object {
  def some(predicate: js.Function1[T, Boolean]) // linter:ignore UnusedParameter
    : Boolean = js.native

  def filter(predicate: js.Function1[T, Boolean]) // linter:ignore UnusedParameter
    : ImmutableList[T] = js.native

  // See https://facebook.github.io/immutable-js/docs/#/List/first
  def first(): js.UndefOr[T] = js.native

  def toArray(): js.Array[T] = js.native
}
