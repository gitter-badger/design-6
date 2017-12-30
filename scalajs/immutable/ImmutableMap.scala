// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.immutable

import scala.scalajs.js

@js.native
trait ImmutableMap[K <: Any, V <: Any] extends js.Object {
  def get(key: K): js.UndefOr[V] = js.native // linter:ignore UnusedParameter
}
