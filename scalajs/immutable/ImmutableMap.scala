// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.immutable

import scala.scalajs.js

@js.native
trait ImmutableMap[K <: Any, V <: Any] extends js.Object {
  def toArray(): js.Array[(K, V)] = js.native
}

object ImmutableMap {
  implicit def toMap[K, V](immutableMap: ImmutableMap[K, V]): Map[K, V] = {
    immutableMap.toArray().toMap
  }
}
