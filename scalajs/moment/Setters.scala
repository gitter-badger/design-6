// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.moment

// https://github.com/widok/scala-js-momentjs

import scala.scalajs.js

@js.native
trait Setters[T] extends js.Object {
  def add(time: Double, unit: String): T = js.native // linter:ignore UnusedParameter
  def add(millis: Int): T = js.native // linter:ignore UnusedParameter
  def add(duration: Duration): T = js.native // linter:ignore UnusedParameter

  def subtract(time: Double, unit: String): T = js.native // linter:ignore UnusedParameter
  def subtract(millis: Int): T = js.native // linter:ignore UnusedParameter
  def subtract(duration: Duration): T = js.native // linter:ignore UnusedParameter
}
