// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.util

import scala.scalajs.js.{Date => JsDate}

import java.time.{Duration, Instant, LocalDate, ZonedDateTime}

import anduin.scalajs.moment.{Date, Moment}

object DateUtils {

  def toJsDate(date: ZonedDateTime): JsDate = {
    new JsDate(date.toInstant.toEpochMilli.toDouble)
  }

  def toJsDate(date: LocalDate): JsDate = {
    new JsDate(Duration.ofDays(date.toEpochDay).toMillis.toDouble)
  }

  def rollToNextMondayIfWeekend(date: ZonedDateTime): ZonedDateTime = {
    val jsDate = toJsDate(date)
    // Note: `getDay()` returns (0-6) which is Sunday -> Saturday
    jsDate.getDay() match {
      case 0 => date.plusDays(1) // Sunday roll to Monday
      case 6 => date.plusDays(2) // Saturday roll to Monday
      case _ => date
    }
  }

  implicit def toMomentDate(date: ZonedDateTime): Date = {
    Moment(date.toInstant.toEpochMilli.toDouble)
  }

  implicit def toMomentDate(instant: Instant): Date = {
    Moment(instant.toEpochMilli.toDouble)
  }

  val Day = "day"

  val Month = "month"

  val Year = "year"
}
