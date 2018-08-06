// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.moment

// https://github.com/widok/scala-js-momentjs

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
trait Tz extends js.Object {
  // Returned the best-guess of the time zone of the current browser. We could use this to feed to `threeten.bp.ZoneId`
  // when scala-java-time supports time zone.
  def guess(): String = js.native

  def names(): js.Array[String] = js.native

  def apply(zone: String): Date = js.native

  def apply(milis: Double, zone: String): Date = js.native
}

@JSImport("moment-timezone", JSImport.Namespace, "Moment")
@js.native
object Moment extends js.Object {
  def apply(): Date = js.native

  /* Long has different semantics than JavaScript's numbers, therefore Double
   * must be used.
   */
  def apply(millis: Double): Date = js.native

  // moment("20111031", "YYYYMMDD").fromNow(); // 3 years ago
  def apply(value: String, format: String): Date = js.native
  def apply(value: String, format: String, strict: Boolean)
    : Date = js.native
  def apply(value: js.Date): Date = js.native

  def locale(s: String): Unit = js.native
  def locale(): String = js.native

  def utc(): Date = js.native
  def utc(millis: Double): Date = js.native
  def utc(numbers: js.Array[Int]): Date = js.native
  def utc(string: String, format: String): Date = js.native

  def duration(millis: Int): Duration = js.native
  def duration(time: Int, unit: String): Duration = js.native
  def duration(time: String): Duration = js.native

  def tz: Tz = js.native
}
