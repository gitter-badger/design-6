// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.text

import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalDateTime}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class DateTime(
  value: LocalDateTime,
  date: DateTime.Date = DateTime.DateShort,
  dateIsRelative: Boolean = false,
  time: DateTime.Time = DateTime.TimeHM12
) {
  def apply(): VdomElement = DateTime.component(this)
}

object DateTime {

  type Props = DateTime
  type DTF = DateTimeFormatter

  sealed trait Date { val dtf: DTF }
  case object DateShort extends Date { val dtf: DTF = DateTimeFormatter.ofPattern("MMM d") }
  case object DateLong extends Date { val dtf: DTF = DateTimeFormatter.ofPattern("MMMM d") }

  sealed trait Time { val dtf: DTF }
  case object TimeHM12 extends Time { val dtf: DTF = DateTimeFormatter.ofPattern("h:m a") }
  case object TimeNone extends Time { val dtf: DTF = DateTimeFormatter.ofPattern("") }

  private val today = LocalDate.now()
  private val yesterday = today.minusDays(1)

  private def getDate(props: Props): String = {
    val date = props.value.toLocalDate
    if (props.dateIsRelative && date == today) {
      "Today"
    } else if (props.dateIsRelative && date == yesterday) {
      "Yesterday"
    } else {
      props.value.format(props.date.dtf)
    }
  }

  private def getTime(props: Props): String = {
    props.time match {
      case TimeNone   => ""
      case time: Time => s", ${props.value.format(time.dtf)}"
    }
  }

  private def render(props: Props): VdomNode = {
    s"${getDate(props)}${getTime(props)}"
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
