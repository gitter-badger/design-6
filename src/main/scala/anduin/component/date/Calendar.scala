// Copyright (C) 2014-2018 Anduin Transactions Inc.
package com.anduin.stargazer.client.component.date

import java.util.Locale
import scala.scalajs.js.{Date => JsDate}

import japgolly.scalajs.react.extra.{EventListener, OnUnmount}
import org.scalajs.dom
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.{KeyboardEvent, MouseEvent}
import java.time.format.TextStyle
import java.time.{LocalDate, Month, ZoneOffset, ZonedDateTime}

import org.scalajs.dom.raw.HTMLElement

import anduin.component.button.Button

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object Calendar {

  private val ComponentName = this.getClass.getSimpleName

  private val DaysPerWeek = 7 // The number of days in every week

  /**
    * Short name of week days
    */
  private val ShortDayNames = Array("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

  case class Props(
    date: LocalDate,
    minDate: Option[LocalDate],
    maxDate: Option[LocalDate],
    yearRange: Int,
    shown: Boolean,
    onDayChange: LocalDate => Callback,
    onHideCalendar: LocalDate => Callback // final date => Callback
  )

  case class State(date: LocalDate)

  case class Backend(scope: BackendScope[Props, State]) extends OnUnmount {
    private val innerElementRef = Ref[HTMLElement]

    /**
      * Close the picker if user press the Escape key
      */
    def onKeyDown(e: KeyboardEvent): Callback = {
      for {
        props <- scope.props
        state <- scope.state
        res <- Callback.when(props.shown && e.keyCode == KeyCode.Escape) {
          // Hide the date picker
          props.onHideCalendar(state.date)
        }
      } yield res
    }

    /**
      * Close the picker if user click on outside area
      */
    def onMouseDown(e: MouseEvent): Callback = {
      // Determine if user click inside or outside the picker area
      for {
        rect <- innerElementRef.map(_.getBoundingClientRect()).get
        inside = (e.clientY >= rect.top && e.clientY <= rect.top + rect.height) &&
          (e.clientX >= rect.left && e.clientX <= rect.left + rect.width)
        props <- scope.props
        state <- scope.state
        res <- Callback.when(!inside && props.shown) {
          props.onHideCalendar(state.date)
        }
      } yield res
    }

    /**
      * Check if a given year is leap one or not
      *
      * @param year The given year
      * @return Boolean
      */
    private def isLeapYear(year: Int) = {
      (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

    /**
      * Retrieve the number of days in given month
      *
      * @param year  The given year
      * @param month The given month (zero-index)
      * @return Int
      */
    private def getDaysInMonth(year: Int, month: Month) = {
      val daysInFeb = if (isLeapYear(year)) 29 else 28
      month match {
        case Month.FEBRUARY => daysInFeb
        case Month.JANUARY | Month.MARCH | Month.MAY | Month.JULY | Month.AUGUST | Month.OCTOBER | Month.DECEMBER =>
          31
        case _ => 30
      }
    }

    /**
      * Go to a particular month
      */
    private def onMonthChange(e: ReactEventFromInput) = {
      for {
        _ <- e.stopPropagationCB
        state <- scope.state
        res <- e.extract(_.target.value.toInt) { value =>
          scope.modState(_.copy(date = state.date.withMonth(value)))
        }
      } yield res
    }

    /**
      * Go to a particular year
      */
    private def onYearChange(e: ReactEventFromInput) = {
      for {
        _ <- e.stopPropagationCB
        state <- scope.state
        res <- e.extract(_.target.value.toInt) { value =>
          scope.modState(_.copy(date = state.date.withYear(value)))
        }
      } yield res
    }

    /**
      * Choose a particular day
      */
    private def onDayChange(date: LocalDate)() = {
      for {
        props <- scope.props
        _ <- scope.modState(_.copy(date = date))
        _ <- props.onHideCalendar(date)
        res <- props.onDayChange(date)
      } yield res
    }

    /**
      * Go to the previous month
      */
    private def goToPrevMonth(e: ReactEvent) = {
      for {
        _ <- e.stopPropagationCB
        state <- scope.state
        res <- scope.modState(_.copy(date = state.date.minusMonths(1)))
      } yield res
    }

    /**
      * Go to the next month
      */
    private def goToNextMonth(e: ReactEvent) = {
      for {
        _ <- e.stopPropagationCB
        state <- scope.state
        res <- scope.modState(_.copy(date = state.date.plusMonths(1)))
      } yield res
    }

    /**
      * Render a select to choose a month
      */
    private def renderMonths(month: Month) = {
      <.div(
        ^.cls := "datepicker-label",
        month.getDisplayName(TextStyle.FULL, Locale.ENGLISH),
        <.select(
          ^.cls := "datepicker-select",
          ^.defaultValue := month.getDisplayName(TextStyle.FULL, Locale.ENGLISH),
          ^.onChange ==> onMonthChange,
          Month.values.toTagMod { month =>
            <.option(
              month.getDisplayName(TextStyle.FULL, Locale.ENGLISH),
              ^.key := s"month-$month",
              ^.value := month.getValue
            )
          }
        )
      )
    }

    /**
      * Render a select to choose a year
      */
    private def renderYears(year: Int, yearRange: Int) = {
      val years = Array.range(year - yearRange, year + yearRange + 1)

      <.div(
        ^.cls := "datepicker-label",
        year,
        <.select(
          ^.cls := "datepicker-select",
          ^.defaultValue := year,
          ^.onChange ==> onYearChange,
          years.toTagMod { y =>
            <.option(
              y,
              ^.key := s"year-$y",
              ^.value := y
            )
          }
        )
      )
    }

    /**
      * Render a calendar based on given month and year
      * month value is zero-index
      */
    // scalastyle:off multiple.string.literals
    private def renderCalendar(date: LocalDate, minDate: Option[LocalDate], maxDate: Option[LocalDate]) = {
      val daysInMonth = getDaysInMonth(date.getYear, date.getMonth)

      // Note: -1 for the month because JsDate is 0-based, and LocalDate is 1-based
      val before = new JsDate(date.getYear, date.getMonthValue - 1, 1).getDay()

      val after = (daysInMonth + before) % DaysPerWeek
      val numCells = daysInMonth + before + DaysPerWeek - after
      val numRows = numCells / DaysPerWeek

      val now = ZonedDateTime.now(ZoneOffset.UTC).toLocalDate

      <.table(
        ^.cls := "datepicker-table",
        // Show the day names
        <.thead(
          <.tr(
            ShortDayNames.toTagMod { dayName =>
              <.th(
                ^.key := s"date-$dayName",
                <.abbr(^.title := dayName, dayName)
              )
            }
          )
        ),
        // Show days in month
        <.tbody(
          Array.range(0, numRows).toTagMod { i =>
            <.tr(
              ^.key := s"week-$i",
              Array.range(0, DaysPerWeek).toTagMod {
                j =>
                  val d = (i * DaysPerWeek + j - before) + 1

                  val dayModel = if (d > 0 && d <= daysInMonth) {
                    date.withDayOfMonth(d)
                  } else if (d <= 0) {
                    // Belong to previous month
                    date
                      .withDayOfMonth(1)
                      .plusDays(d.toLong - 1) // use plus here because d is negative
                  } else {
                    // Belong to next month
                    date.withDayOfMonth(daysInMonth).plusDays(d.toLong - daysInMonth)
                  }
                  val dayInMonth = d > 0 && d <= daysInMonth
                  val isDisabled = (minDate.nonEmpty && dayModel.compareTo(minDate.getOrElse(now)) < 0) ||
                    (maxDate.nonEmpty && dayModel.compareTo(maxDate.getOrElse(now)) > 0)

                  <.td(
                    ^.key := s"day-$d",
                    ^.classSet(
                      "datepicker-day-empty" -> !dayInMonth,
                      "datepicker-day-selected" -> (d == date.getDayOfMonth),
                      "datepicker-day-today" -> (now == dayModel),
                      "datepicker-day-disabled" -> isDisabled
                    ),
                    TagMod.when(dayInMonth)(
                      Button(onClick = onDayChange(dayModel))(d)
                    )
                  )
              }
            )
          }
        )
      )
    }
    // scalastyle:on multiple.string.literals

    def render(props: Props, state: State): VdomTagOf[HTMLElement] = {
      <.div(
        ^.classSet(
          "datepicker-inner" -> true,
          "datepicker-hidden" -> !props.shown
        )
      ).withRef(innerElementRef)(
        <.div(
          ^.cls := "datepicker-calendar",
          <.div(
            ^.cls := "datepicker-title",
            // The select to choose a month
            renderMonths(state.date.getMonth),
            // The select to choose a year
            renderYears(state.date.getYear, props.yearRange),
            // The button to go to the previous month
            <.button("Previous month", ^.cls := "datepicker-prev", ^.tpe := "button", ^.onClick ==> goToPrevMonth),
            // The button to go to the next month
            <.button("Next month", ^.cls := "datepicker-next", ^.tpe := "button", ^.onClick ==> goToNextMonth)
          ),
          renderCalendar(state.date, props.minDate, props.maxDate)
        )
      )
    }
  }

  val component = ScalaComponent
    .builder[Props](ComponentName)
    .initialStateFromProps { props =>
      State(props.date)
    }
    .renderBackend[Backend]
    .componentWillReceiveProps { scope =>
      Callback.when(scope.nextProps.date != scope.currentProps.date) {
        scope.modState(_.copy(date = scope.nextProps.date))
      }
    }
    .configure(
      EventListener[KeyboardEvent].install("keydown", _.backend.onKeyDown, _ => dom.document),
      EventListener[MouseEvent].install("mousedown", _.backend.onMouseDown, _ => dom.document)
    )
    .build

  def apply(
    date: LocalDate = ZonedDateTime.now(ZoneOffset.UTC).toLocalDate,
    minDate: Option[LocalDate] = None,
    maxDate: Option[LocalDate] = None,
    yearRange: Int = 10,
    shown: Boolean = false,
    onDayChange: LocalDate => Callback = _ => Callback.empty,
    onHideCalendar: LocalDate => Callback = _ => Callback.empty
  ): ScalaComponent.Unmounted[_, _, _] = component(
    Props(
      date = date,
      minDate = minDate,
      maxDate = maxDate,
      yearRange = yearRange,
      shown = shown,
      onDayChange = onDayChange,
      onHideCalendar = onHideCalendar
    )
  )
}
