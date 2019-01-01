// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.date

import java.time.Instant
import scala.concurrent.duration
import scala.concurrent.duration.FiniteDuration
import scala.scalajs.js.Date

import japgolly.scalajs.react.extra.TimerSupport

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.scalajs.datefns._
// scalastyle:on underscore.import

final case class TimeAgo(instant: Instant, autoUpdate: Boolean = true) {

  def apply(): VdomElement = TimeAgo.component(this)

  val getDate: Date = {
    new Date(instant.toEpochMilli.toDouble)
  }
}

object TimeAgo {

  private type Props = TimeAgo

  private val suffixToRemove = "about "

  private def calculateFromNow(props: Props) = {
    val str = DistanceInWordsToNow(props.getDate, DistanceInWordsOptions(addSuffixParam = true))
    if (str.startsWith(suffixToRemove)) {
      str.substring(suffixToRemove.length)
    } else {
      str
    }
  }

  private case class State(fromNow: String, start: Int = 0)

  private case class Backend(scope: BackendScope[Props, State]) extends TimerSupport {

    private def tick(props: Props) = {
      scope.modState(_.copy(fromNow = calculateFromNow(props)))
    }

    def startTimer(): Callback = {
      for {
        props <- scope.props
        _ <- Callback.when(props.autoUpdate) {
          val seconds = DifferenceInSeconds(Date.now(), props.getDate)
          val durationTime = if (seconds < 60) {
            10
          } else if (seconds < 60 * 60) {
            60 * 5
          } else if (seconds < 60 * 60 * 24) {
            60 * 60
          } else {
            -1
          }

          Callback.when(durationTime > 0) {
            setInterval(tick(props), FiniteDuration(durationTime.toLong, duration.SECONDS))
          }
        }
      } yield ()
    }

    def render(props: Props, state: State): VdomElement = {
      // Use the HTML5 <time> element
      // https://developer.mozilla.org/en-US/docs/Web/HTML/Element/time
      <.time(^.dateTime := Format(props.getDate), state.fromNow)
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialStateFromProps { props =>
      State(fromNow = calculateFromNow(props))
    }
    .renderBackend[Backend]
    .componentDidMount(_.backend.startTimer())
    .configure(TimerSupport.install)
    .build
}
