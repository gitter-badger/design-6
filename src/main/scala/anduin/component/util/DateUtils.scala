// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.util

import java.time.ZonedDateTime
import scala.scalajs.js.Date

object DateUtils {

  def rollToNextMondayIfWeekend(date: ZonedDateTime): ZonedDateTime = {
    val jsDate = new Date(date.toInstant.toEpochMilli.toDouble)
    // Note: `getDay()` returns (0-6) which is Sunday -> Saturday
    jsDate.getDay() match {
      case 0 => date.plusDays(1) // Sunday roll to Monday
      case 6 => date.plusDays(2) // Saturday roll to Monday
      case _ => date
    }
  }
}
