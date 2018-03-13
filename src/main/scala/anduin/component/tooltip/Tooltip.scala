// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.tooltip

import japgolly.scalajs.react.extra.{EventListener, OnUnmount}
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.raw.HTMLElement

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Tooltip(
  tip: String,
  placement: Tooltip.Placement = Tooltip.Placement.Top,
  containerClasses: String = "",
  tipClasses: String = "",
  status: Tooltip.Status = Tooltip.Status.Default,
  offset: Double = 5,
  size: Tooltip.Size = Tooltip.Size.Default,
  keyOpt: Option[String] = None
) {
  def apply(children: VdomNode*): ScalaComponent.Unmounted[_, _, _] = {
    val component = Tooltip.component
    keyOpt.fold {
      component(this)(children: _*)
    } {
      component.withKey(_)(this)(children: _*)
    }
  }
}

object Tooltip {

  private val ComponentName = this.getClass.getSimpleName

  sealed trait Placement {
    def className: String
  }
  case object Placement {
    case object TopLeft extends Placement { val className = "-top-left" }
    case object Top extends Placement { val className = "-top" }
    case object TopRight extends Placement { val className = "-top-right" }

    case object Left extends Placement { val className = "-left" }
    case object Right extends Placement { val className = "-right" }

    case object BottomLeft extends Placement { val className = "-bottom-left" }
    case object Bottom extends Placement { val className = "-bottom" }
    case object BottomRight extends Placement { val className = "-bottom-right" }
  }

  sealed trait Status {
    def className: String
  }
  case object Status {
    case object Default extends Status { val className = "" }
    case object Info extends Status { val className = "-primary" }
    case object Success extends Status { val className = "-success" }
    case object Warning extends Status { val className = "-warning" }
    case object Danger extends Status { val className = "-danger" }
  }

  sealed trait Size {
    def className: String
  }
  case object Size {
    case object Default extends Size { val className = "" }
    case object Small extends Size { val className = "-small" }
    case object Medium extends Size { val className = "-medium" }
    case object Large extends Size { val className = "-large" }
  }

  private case class State(over: Boolean)

  private case class Backend(scope: BackendScope[Tooltip, State]) extends OnUnmount {

    private val tipNode = document.createElement("div")
    private var targetRef: HTMLElement = _ // scalastyle:ignore

    private def onMouseOver = {
      for {
        props <- scope.props
        state <- scope.state
        _ <- Callback.when(!state.over && props.tip.nonEmpty) {
          for {
            _ <- Callback {
              document.body.appendChild(tipNode)

              val element = <.span(
                ^.cls := s"arrow ${props.placement.className} -fade",
                props.tip
              )

              element.renderIntoDOM(tipNode)
              tipNode
                .setAttribute("class", s"tip ${props.tipClasses} ${props.status.className} ${props.size.className}")
            }
            _ <- updatePosition
            _ <- scope.modState(_.copy(over = true))
          } yield ()
        }
      } yield ()
    }

    // It's possible to re-calculate the tooltip's position here (by calling `updatePosition()`)
    // but we don't know whether the tooltip is out of viewport (child scrollable element, for example).
    // So the best thing we can do now is to hide the tooltip when scrolling
    def onWindowScroll: Callback = {
      for {
        props <- scope.props
        state <- scope.state
        _ <- Callback.when(state.over && props.tip.nonEmpty) {
          Callback {
            tipNode.classList.add("hide")
          }
        }
      } yield ()
    }

    // Remove tip when the component unmounts
    def removeTip: Callback = {
      for {
        props <- scope.props
        state <- scope.state
        _ <- Callback.when(state.over && props.tip.nonEmpty) {
          Callback {
            document.body.removeChild(tipNode)
          }
        }
      } yield ()
    }

    private def updatePosition = {
      for {
        props <- scope.props
        _ <- Callback.when(props.tip.nonEmpty) {
          val rect = targetRef.getBoundingClientRect()
          val top = rect.top + document.documentElement.scrollTop + document.body.scrollTop
          val left = rect.left + document.documentElement.scrollLeft + document.body.scrollLeft

          Callback {
            val (tipTop, tipLeft) = props.placement match {
              case Placement.Top =>
                (top - tipNode.clientHeight - props.offset, left + rect.width / 2 - tipNode.clientWidth / 2)
              case Placement.TopLeft =>
                (top - tipNode.clientHeight - props.offset, left)
              case Placement.TopRight =>
                (top - tipNode.clientHeight - props.offset, left + rect.width - tipNode.clientWidth)
              case Placement.Bottom =>
                (top + rect.height + props.offset, left + rect.width / 2 - tipNode.clientWidth / 2)
              case Placement.BottomLeft =>
                (top + rect.height + props.offset, left)
              case Placement.BottomRight =>
                (top + rect.height + props.offset, left + rect.width - tipNode.clientWidth)
              case Placement.Left =>
                (top + rect.height / 2 - tipNode.clientHeight / 2, left - tipNode.clientWidth - props.offset)
              case Placement.Right =>
                (top + rect.height / 2 - tipNode.clientHeight / 2, left + rect.width + props.offset)
            }

            tipNode.setAttribute(
              "style",
              s"top: ${tipTop}px; left: ${tipLeft}px"
            )
          }
        }
      } yield ()
    }

    private def onMouseOut = {
      for {
        props <- scope.props
        state <- scope.state
        _ <- Callback.when(state.over && props.tip.nonEmpty) {
          for {
            _ <- Callback {
              document.body.removeChild(tipNode)
            }
            _ <- scope.modState(_.copy(over = false))
          } yield ()
        }
      } yield ()
    }

    def render(props: Tooltip, children: PropsChildren): VdomElement = {
      <.span.ref(targetRef = _)(
        ^.classSet(
          props.containerClasses -> props.containerClasses.nonEmpty
        ),
        ^.onMouseOver --> onMouseOver,
        ^.onMouseOut --> onMouseOut,
        children
      )
    }
  }

  private val component = ScalaComponent
    .builder[Tooltip](ComponentName)
    .initialState(State(over = false))
    .renderBackendWithChildren[Backend]
    .componentWillUnmount(_.backend.removeTip)
    .configure(
      EventListener.install("scroll", _.backend.onWindowScroll, _ => dom.window)
    )
    .build
}
