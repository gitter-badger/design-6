// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.util

import java.time.{Duration, Instant, LocalDate, ZonedDateTime}
import scala.scalajs.js.Date

import anduin.scalajs.datefns.Format

/**
  * Date formatter on JS side. We should replace this with `CoreFormatterUtils` when scala-java-time supports time zone
  * Formats can be looked up at https://momentjs.com
  */
object JsDateFormatterUtils {

  val CreatedAtDatePattern = "hh:mm A" // 07:40 AM
  val LongDatePattern = "MMM DD YYYY" // Dec 12 2015
  val SuperLongDatePattern = "YYYY-MM-DD HH:mm:ss" // 2016-10-19 12:40:30
  val LongDatePattern1 = "MMM D, YYYY" // May 1, 2016
  val ShortLocalDatePattern = "MMM D"

  def format(date: ZonedDateTime, pattern: String): String = {
    Format(new Date(date.toInstant.toEpochMilli.toDouble), pattern)
  }

  def format(instant: Instant, pattern: String): String = {
    Format(new Date(instant.toEpochMilli.toDouble), pattern)
  }

  def format(localDate: LocalDate, pattern: String): String = {
    Format(new Date(Duration.ofDays(localDate.toEpochDay).toMillis.toDouble), pattern)
  }
}
