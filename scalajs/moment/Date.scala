// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.moment

// https://github.com/widok/scala-js-momentjs

import scala.scalajs.js

@js.native
trait Date extends js.Object {
  def fromNow(): String = js.native
  def format(): String = js.native
  def format(pattern: String): String = js.native
  def diff(date: Date, unit: String): Int = js.native
  def toDate(): js.Date = js.native
  def year(): Int = js.native
  def month(): Int = js.native
  def date(): Int = js.native
}
