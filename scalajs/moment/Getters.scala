// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.scalajs.moment

// https://github.com/widok/scala-js-momentjs

import scala.scalajs.js

@js.native
trait Getters extends js.Object {
  def milliseconds(): Int = js.native
  def seconds(): Double = js.native
  def minutes(): Double = js.native
  def hours(): Double = js.native
  def day(): Double = js.native
  def date(): Double = js.native
  def month(): Double = js.native
  def year(): Double = js.native
  def unix(): Double = js.native
}
