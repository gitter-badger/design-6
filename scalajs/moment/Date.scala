// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.scalajs.moment

// https://github.com/widok/scala-js-momentjs

import scala.scalajs.js

@js.native
trait Date extends js.Object with Getters with Setters[Date] {
  def fromNow(): String = js.native
  def fromNow(withoutSuffix: Boolean): String = js.native // linter:ignore UnusedParameter
  def isValid(): Boolean = js.native
  def format(): String = js.native
  def format(pattern: String): String = js.native // linter:ignore UnusedParameter
  def startOf(unit: String): Date = js.native // linter:ignore UnusedParameter
  def endOf(unit: String): Date = js.native // linter:ignore UnusedParameter
  def calendar(): String = js.native
  def isBefore(date: Date): Boolean = js.native // linter:ignore UnusedParameter
  def isBefore(date: Date, unit: String): Boolean = js.native // linter:ignore UnusedParameter
  def isAfter(date: Date): Boolean = js.native // linter:ignore UnusedParameter
  def isAfter(date: Date, unit: String): Boolean = js.native // linter:ignore UnusedParameter

  def diff(date: Date, unit: String): Int = js.native // linter:ignore UnusedParameter
  def diff(date: Long, unit: String): Int = js.native // linter:ignore UnusedParameter

  def toDate(): js.Date = js.native

  def isSame(that: Date, unit: String): Boolean = js.native // linter:ignore UnusedParameter

  def tz(): String = js.native // linter:ignore UnusedParameter
}
