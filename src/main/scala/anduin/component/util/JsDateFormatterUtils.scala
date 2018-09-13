// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.util

import java.time.{Instant, LocalDate, LocalDateTime, ZonedDateTime}

import anduin.scalajs.moment.Moment

/**
  * Date formatter on JS side. We should replace this with `CoreFormatterUtils` when scala-java-time supports time zone
  * Formats can be looked up at https://momentjs.com
  */
object JsDateFormatterUtils {

  val DefaultPattern = "" // 2016-11-01T15:33:50+07:00
  val CreatedAtDatePattern = "hh:mm A" // 07:40 AM
  val LongDatePattern = "MMM DD YYYY" // Dec 12 2015
  val SuperLongDatePattern = "YYYY-MM-DD HH:mm:ss" // 2016-10-19 12:40:30
  val LongDatePattern1 = "MMM D, YYYY" // May 1, 2016
  val ShortLocalDatePattern = "MMM D"

  def format(date: ZonedDateTime, pattern: String): String = {
    Moment(date.toInstant.toEpochMilli.toDouble).format(pattern)
  }

  def format(instant: Instant, pattern: String): String = {
    Moment(instant.toEpochMilli.toDouble).format(pattern)
  }

  private val FourDigitZeroPaddingFormatter = "%04d" // return "1992" or "0082"
  private val TwoDigitZeroPaddingFormatter = "%02d" // return "11" or "02"
  private val LocalDatePattern = "YYYY-MM-DD"
  private val LocalDateTimePattern = "YYYY-MM-DD hh:mm:ss"

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
