// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.d3

import scala.scalajs.js

@js.native
trait Scale[T] extends js.Object {
  def domain(input: js.Array[T]): Scale[T] = js.native // linter:ignore UnusedParameter
  def range(input: js.Array[Double]): Scale[T] = js.native // linter:ignore UnusedParameter
  def apply(p: T): Double = js.native // linter:ignore UnusedParameter
}
