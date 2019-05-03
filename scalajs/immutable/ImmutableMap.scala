// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.immutable

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("immutable", "Map")
@js.native
class ImmutableMap[K, V <: js.Object] extends js.Object {
  def reduce[R](reducer: js.Function3[R, V, K, R], initialReduction: R): R = js.native
}

object ImmutableMap {
  implicit def toMap[K, V <: js.Object](immutableMap: ImmutableMap[K, V]): Map[K, V] = {
    immutableMap.reduce[Map[K, V]](
      (current, value, key) => {
        current.updated(key, value)
      },
      Map()
    )
  }
}
