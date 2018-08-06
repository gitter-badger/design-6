// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.moment

// https://github.com/widok/scala-js-momentjs

import scala.scalajs.js

@js.native
trait Setters[T] extends js.Object {
  def add(time: Double, unit: String): T = js.native
  def add(millis: Int): T = js.native
  def add(duration: Duration): T = js.native

  def subtract(time: Double, unit: String): T = js.native
  def subtract(millis: Int): T = js.native
  def subtract(duration: Duration): T = js.native
}
