// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.immutable

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("immutable", "List")
@js.native
class ImmutableList[T <: js.Object] extends js.Object {
  def toArray(): js.Array[T] = js.native
}

object ImmutableList {
  implicit def toList[T <: js.Object](immutableList: ImmutableList[T]): List[T] = immutableList.toArray().toList
}
