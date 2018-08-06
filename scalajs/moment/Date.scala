// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.moment

// https://github.com/widok/scala-js-momentjs

import scala.scalajs.js

@js.native
trait Date extends js.Object with Getters with Setters[Date] {
  def fromNow(): String = js.native
  def fromNow(withoutSuffix: Boolean): String = js.native
  def isValid(): Boolean = js.native
  def format(): String = js.native
  def format(pattern: String): String = js.native
  def startOf(unit: String): Date = js.native
  def endOf(unit: String): Date = js.native
  def calendar(): String = js.native
  def isBefore(date: Date): Boolean = js.native
  def isBefore(date: Date, unit: String): Boolean = js.native
  def isAfter(date: Date): Boolean = js.native
  def isAfter(date: Date, unit: String): Boolean = js.native

  def diff(date: Date, unit: String): Int = js.native
  def diff(date: Long, unit: String): Int = js.native

  def toDate(): js.Date = js.native

  def isSame(that: Date, unit: String): Boolean = js.native

  def tz(): String = js.native
}
