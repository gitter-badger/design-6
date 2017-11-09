// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.popover

import scala.scalajs.js.Date

import japgolly.scalajs.react.extra.{EventListener, OnUnmount}
import japgolly.scalajs.react.vdom.Exports.VdomTagOf
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html.Div
import org.scalajs.dom.raw.{HTMLElement, MouseEvent}

import anduin.component.icon.Icon
import anduin.component.util.{EventUtils, JavaScriptUtils}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Popover(
  toggler: TagMod,
  disableToggler: Boolean = false,
  hasCloseButton: Boolean = false,
  // Set it to `true` if you want to calculate the popover position dynamically when it's shown.
  // It's used to fix the problem where the absolute positioning popover is displayed under some layers
  dynamicPosition: Boolean = false,
  verticalOffset: Double = 0,
  horizontalOffset: Double = 0,
  placement: Popover.Placement = Popover.Placement.Default,
  size: Popover.Size = Popover.Size.Default,
  classes: String = "",
  popoverBodyClasses: String = "",
  key: String = "",
  trigger: Popover.Trigger = Popover.Trigger.Click,
  hideWhenClickInside: Boolean = true,
  //set to false if you don't want to hide popover on click event (both outside and inside)
  //this is a workaround when inside the popover have a dropdown that content exceeds the popover body area
  hideWhenClick: Boolean = true,
  status: Popover.Status = Popover.Status.Hidden,
  unmountWhenHide: Boolean = false,
  onStatusChange: Popover.Status => Callback = _ => Callback.empty
)(
  val children: Popover.RenderProps => TagMod // display state => component
) {
  def apply(): ScalaComponent.Unmounted[_, _, _] = Popover.component.withKey(key)(this)
}

// scalastyle:off number.of.types
object Popover {

  /**
    * Define the popover size
    */
  sealed trait Size {
    def className: String
  }

  case object Size {
    case object Default extends Size { val className = "" }
    case object Medium extends Size { val className = "-medium" }
    case object Large extends Size { val className = "-large" }
    case object XLarge extends Size { val className = "-xlarge" }
  }

  /**
    * Define the position of arrow
    */
  sealed trait Placement {
    def className: String
  }

  case object Placement {
    case object Default extends Placement { val className = "" }
    case object Top extends Placement { val className = "-top" }
    case object Right extends Placement { val className = "-right" }
    case object Bottom extends Placement { val className = "-bottom" }
    case object Left extends Placement { val className = "-left" }
    case object TopLeft extends Placement { val className = "-top-left" }
    case object TopRight extends Placement { val className = "-top-right" }
    case object BottomLeft extends Placement { val className = "-bottom-left" }
    case object BottomRight extends Placement { val className = "-bottom-right" }
    case object LeftTop extends Placement { val className = "-left-top" }
    case object LeftBottom extends Placement { val className = "-left-bottom" }
    case object RightTop extends Placement { val className = "-right-top" }
    case object RightBottom extends Placement { val className = "-right-bottom" }
  }

  sealed trait Trigger
  object Trigger {
    case object Hover extends Trigger
    case object Click extends Trigger
  }

  private val ComponentName = this.getClass.getSimpleName

  // Hide the popover if the time counted from the moment user move mouse out of toggler
  // exceed this number of milliseconds.
  // We need it to support using multiple popovers on the same page
  private final val AutoHideDurationMs = 60

  sealed trait Status
  object Status {
    case object Displayed extends Status
    case object Hidden extends Status // mounted but not displayed
    case object Unmounted extends Status
  }

  case class RenderProps(displaying: Boolean, changeStatus: Status => Callback) {
    def hide: Callback = changeStatus(Status.Hidden)
    def display: Callback = changeStatus(Status.Displayed)
    def unmount: Callback = changeStatus(Status.Unmounted)
  }

  case class State(status: Status, leaveTogglerTimeOpt: Option[Double])

  case class Backend(scope: BackendScope[Popover, State]) extends OnUnmount {

    private var bodyRef: HTMLElement = _ // scalastyle:ignore
    private var togglerRef: HTMLElement = _ // scalastyle:ignore
    private var wrapperRef: HTMLElement = _ // scalastyle:ignore

    /**
      * Hide the popover if user click outside of its body
      */
    def onWindowClick(e: MouseEvent): Callback = {
      for {
        props <- scope.props
        state <- scope.state
        // Check if first to save some calculations/checks performed in `isInside`
        _ <- Callback.when(state.status == Status.Displayed) {
          val insideToggler = EventUtils.clickInside(e, togglerRef)
          val insideBody = EventUtils.clickInside(e, bodyRef)

          // Hide the popover if
          Callback.when(
            props.hideWhenClick && (
              (!insideToggler && !insideBody) || // user click outside of both toggler and popover's body
                (insideBody && props.hideWhenClickInside) // or user click inside the popover's body
              )
          )(hide)
        }
      } yield ()
    }

    def onWindowMouseMove(e: MouseEvent): Callback = {
      for {
        state <- scope.state
        props <- scope.props
        _ <- Callback.when(
          props.trigger == Trigger.Hover &&
            state.leaveTogglerTimeOpt.nonEmpty &&
            !EventUtils.clickInside(e, togglerRef) &&
            !EventUtils.clickInside(e, bodyRef)
        ) {
          Callback.traverseOption(state.leaveTogglerTimeOpt) { leaveTime =>
            val duration = Date.now() - leaveTime
            Callback.when(duration > AutoHideDurationMs)(hide)
          }
        }
      } yield ()
    }

    def onScroll: MouseEvent => Callback = { _ =>
      repositionBody
    }

    private def onClickToggler(e: ReactEventFromInput) = {
      for {
        _ <- e.stopPropagationCB
        props <- scope.props
        state <- scope.state
        _ <- Callback.when(props.trigger == Trigger.Click && !props.disableToggler) {
          if (state.status == Status.Displayed) {
            hide
          } else {
            show
          }
        }
      } yield ()
    }

    // scalastyle:off cyclomatic.complexity multiple.string.literals
    private def show = {
      for {
        state <- scope.state
        _ <- Callback.when(state.status != Status.Displayed) {
          for {
            _ <- repositionBody
            _ <- scope.modState(_.copy(status = Status.Displayed))
          } yield ()
        }
      } yield ()
    }
    // scalastyle:on cyclomatic.complexity multiple.string.literals

    def hide: Callback = {
      for {
        props <- scope.props
        state <- scope.state
        _ <- Callback.when(state.status == Status.Displayed)(scope.modState(_.copy(
          status = if (props.unmountWhenHide) Status.Unmounted else Status.Hidden,
          leaveTogglerTimeOpt = None
        )))
      } yield ()
    }

    // scalastyle:off cyclomatic.complexity multiple.string.literals
    def repositionBody: Callback = {
      for {
        props <- scope.props
        _ <- Callback.when(props.dynamicPosition) {
          Callback {
            val rect = wrapperRef.getBoundingClientRect()
            val (top, left) = props.placement match {
              case Placement.TopLeft => (rect.top - bodyRef.clientHeight, rect.left + rect.width - bodyRef.clientWidth)
              case Placement.Top => (rect.top - bodyRef.clientHeight, rect.left + 0.5 * rect.width - 0.5 * bodyRef.clientWidth)
              case Placement.TopRight => (rect.top - bodyRef.clientHeight, rect.left)

              case Placement.RightTop => (rect.top + rect.height - bodyRef.clientHeight, rect.left + rect.width)
              case Placement.Right => (rect.top + 0.5 * rect.height - 0.5 * bodyRef.clientHeight, rect.left + rect.width)
              case Placement.RightBottom => (rect.top, rect.left + rect.width)

              case Placement.BottomRight => (rect.top + rect.height, rect.left)
              case Placement.Bottom | Placement.Default =>
                (rect.top + rect.height, rect.left + 0.5 * rect.width - 0.5 * bodyRef.clientWidth)
              case Placement.BottomLeft => (rect.top + rect.height, rect.left + rect.width - bodyRef.clientWidth)

              case Placement.LeftTop => (rect.top + rect.height - bodyRef.clientHeight, rect.left - bodyRef.clientWidth)
              case Placement.Left =>
                (rect.top + 0.5 * rect.height - 0.5 * bodyRef.clientHeight, rect.left - bodyRef.clientWidth)
              case Placement.LeftBottom => (rect.top, rect.left - bodyRef.clientWidth)
            }
            val offsetTop = top + props.verticalOffset + document.body.scrollTop
            val offsetLeft = left + props.horizontalOffset + document.body.scrollLeft

            bodyRef.setAttribute("style", s"top: ${offsetTop}px; left: ${offsetLeft}px;")
          }
        }
      } yield ()
    }
    // scalastyle:on cyclomatic.complexity multiple.string.literals

    private def changeStatus(status: Status): Callback = {
      scope.modState(_.copy(status = status))
    }

    private def onMouseLeaveToggler = {
      scope.modState(_.copy(leaveTogglerTimeOpt = Some(Date.now())))
    }

    private def onMouseOverToggler = {
      for {
        _ <- show
        _ <- scope.modState(_.copy(leaveTogglerTimeOpt = None))
      } yield ()
    }

    def render(props: Popover, state: State): VdomTagOf[Div] = {
      <.div.ref(wrapperRef = _)(
        ^.classSet(
          "popover-wrapper" -> true,
          "-dynamic" -> props.dynamicPosition,
          props.classes -> props.classes.nonEmpty
        ),
        // Element for toggling the popover
        <.div.ref(togglerRef = _)(
          props.toggler,
          // Show or hide the popover when clicking or moving the mouse over on the toggle element
          ^.onClick ==> onClickToggler,
          TagMod.when(props.trigger == Trigger.Hover) {
            TagMod(
              ^.onMouseLeave --> onMouseLeaveToggler,
              ^.onMouseOver --> onMouseOverToggler
            )
          }
        ),
        // Popover's body
        TagMod.when(state.status != Status.Unmounted) {
          <.div.ref(bodyRef = _)(
            ^.classSet(
              "popover" -> true,
              "popover-in" -> (state.status == Status.Displayed),
              "popover-out" -> (state.status == Status.Hidden),
              props.size.className -> true,
              "-dynamic" -> props.dynamicPosition,
              props.placement.className -> true,
              props.popoverBodyClasses -> true
            ),
            TagMod.when(props.trigger == Trigger.Hover)(^.onMouseLeave --> hide),
            TagMod.when(props.hasCloseButton)(<.div(
              ^.cls := "close-button-align",
              <.a(
                ^.href := JavaScriptUtils.VoidMethod,
                ^.onClick --> hide,
                Icon.cross("close-button", isSolid = true)
              )
            )),
            props.children(RenderProps(state.status == Status.Displayed, changeStatus))
          )
        }
      )
    }
  }

  val component = ScalaComponent.builder[Popover](ComponentName)
    .initialStateFromProps { props =>
      State(
        status = props.status,
        leaveTogglerTimeOpt = None
      )
    }
    .renderBackend[Backend]
    .componentDidMount(_.backend.repositionBody)
    .componentDidUpdate { scope =>
      Callback.when(scope.prevState.status != scope.currentState.status) {
        scope.currentProps.onStatusChange(scope.currentState.status)
      }
    }
    .configure(
      // `useCapture = true` means that the event will be handled from the document down to the event target
      // before the bubbling phase
      EventListener[MouseEvent].install("click", _.backend.onWindowClick, _ => dom.window, useCapture = true),
      EventListener[MouseEvent].install("mousemove", _.backend.onWindowMouseMove, _ => dom.window),
      EventListener[MouseEvent].install("scroll", _.backend.onScroll, _ => dom.window)
    )
    .build
}
// scalastyle:on number.of.types
