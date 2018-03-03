// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.util

import java.time.{Instant, LocalDate, LocalDateTime, ZonedDateTime}

import anduin.scalajs.moment.Moment

// scalastyle:off underscore.import
import anduin.component.util.DateUtils._
// scalastyle:on underscore.import

/**
  * Date formatter on JS side. We should replace this with `CoreFormatterUtils` when scala-java-time supports time zone
  * Formats can be looked up at https://momentjs.com
  */
object JsDateFormatterUtils {

  final val DefaultPattern = "" // 2016-11-01T15:33:50+07:00
  final val CreatedAtDatePattern = "hh:mm A" // 07:40 AM
  final val LongDatePattern = "MMM DD YYYY" // Dec 12 2015
  final val SuperLongDatePattern = "YYYY-MM-DD hh:mm:ss" // 2016-10-19 12:40:30
  final val LongDatePattern1 = "MMM D, YYYY" // May 1, 2016

  def format(date: ZonedDateTime, pattern: String): String = date.format(pattern)
  def format(instant: Instant, pattern: String): String = instant.format(pattern)

  private final val FourDigitZeroPaddingFormatter = "%04d" // return "1992" or "0082"
  private final val TwoDigitZeroPaddingFormatter = "%02d" // return "11" or "02"
  private final val LocalDatePattern = "YYYY-MM-DD"
  private final val LocalDateTimePattern = "YYYY-MM-DD hh:mm:ss"

  def format(date: LocalDate, pattern: String): String = {
    val yearStr = FourDigitZeroPaddingFormatter.format(date.getYear)
    val monthStr = TwoDigitZeroPaddingFormatter.format(date.getMonthValue)
    val dayStr = TwoDigitZeroPaddingFormatter.format(date.getDayOfMonth)

    val dateStr = s"$yearStr-$monthStr-$dayStr" // e.g. "2015-12-25"
    Moment(dateStr, LocalDatePattern).format(pattern)
  }

  def format(date: LocalDateTime, pattern: String): String = {
    val yearStr = FourDigitZeroPaddingFormatter.format(date.getYear)
    val monthStr = TwoDigitZeroPaddingFormatter.format(date.getMonthValue)
    val dayStr = TwoDigitZeroPaddingFormatter.format(date.getDayOfMonth)
    val hourStr = TwoDigitZeroPaddingFormatter.format(date.getHour)
    val minuteStr = TwoDigitZeroPaddingFormatter.format(date.getMinute)
    val secondStr = TwoDigitZeroPaddingFormatter.format(date.getSecond)

    val dateStr = s"$yearStr-$monthStr-$dayStr $hourStr:$minuteStr:$secondStr" // e.g. "2015-12-25 02:35:03"
    Moment(dateStr, LocalDateTimePattern).format(pattern)
  }
}
