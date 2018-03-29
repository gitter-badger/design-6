// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

//import org.scalajs.dom.document
import org.scalajs.dom.raw.HTMLElement

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Popover(
  position: Popover.Position = Popover.PositionBottom,
  verticalOffset: Double = 0,
  horizontalOffset: Double = 0,
  target: VdomElement,
  content: VdomElement
) {
  def apply(): VdomElement = {
    Popover.component(this)
  }
}

object Popover {

  // Position
  sealed trait Position {
    def className: String
  }
  case object PositionTopLeft extends Position { val className = "-top-left" }
  case object PositionTop extends Position { val className = "-top" }
  case object PositionTopRight extends Position { val className = "-top-right" }

  case object PositionRightTop extends Position { val className = "-right-top" }
  case object PositionRight extends Position { val className = "-right" }
  case object PositionRightBottom extends Position { val className = "-right-bottom" }

  case object PositionBottomLeft extends Position { val className = "-bottom-left" }
  case object PositionBottom extends Position { val className = "-bottom" }
  case object PositionBottomRight extends Position { val className = "-bottom-right" }

  case object PositionLeftTop extends Position { val className = "-left-top" }
  case object PositionLeft extends Position { val className = "-left" }
  case object PositionLeftBottom extends Position { val className = "-left-bottom" }

  private val ComponentName = this.getClass.getSimpleName

  private case class State(isOpen: Boolean = false)

  private case class Backend(scope: BackendScope[Popover, State]) {

    private val targetRef = Ref[HTMLElement]
    //private val portalRef = Ref.toScalaComponent(Portal.component)

    // scalastyle:off cyclomatic.complexity
//    private def onClickTarget = {
//      for {
//        props <- scope.props
//        state <- scope.state
//        target <- targetRef.get
//        portal <- portalRef.get
//        _ <- scope.modState(
//          _.copy(isOpen = !state.isOpen),
//          Callback.when(!state.isOpen) {
//            Callback {
//              val rect = target.getBoundingClientRect()
//              val content = portal.backend.getNode
//              val (top, left) = props.position match {
//                case PositionTopLeft => (rect.top - content.clientHeight, rect.left)
//                case PositionTop =>
//                  (rect.top - content.clientHeight, rect.left + 0.5 * rect.width - 0.5 * content.clientWidth)
//                case PositionTopRight => (rect.top - content.clientHeight, rect.left + rect.width - content.clientWidth)
//
//                case PositionRightTop => (rect.top, rect.left + rect.width)
//                case PositionRight =>
//                  (rect.top + 0.5 * rect.height - 0.5 * content.clientHeight, rect.left + rect.width)
//                case PositionRightBottom => (rect.top + rect.height - content.clientHeight, rect.left + rect.width)
//
//                case PositionBottomRight => (rect.top + rect.height, rect.left + rect.width - content.clientWidth)
//                case PositionBottom =>
//                  (rect.top + rect.height, rect.left + 0.5 * rect.width - 0.5 * content.clientWidth)
//                case PositionBottomLeft => (rect.top + rect.height, rect.left)
//
//                case PositionLeftTop => (rect.top, rect.left - content.clientWidth)
//                case PositionLeft =>
//                  (rect.top + 0.5 * rect.height - 0.5 * content.clientHeight, rect.left - content.clientWidth)
//                case PositionLeftBottom =>
//                  (rect.top + rect.height - content.clientHeight, rect.left - content.clientWidth)
//              }
//              val offsetTop = top + props.verticalOffset + document.body.scrollTop
//              val offsetLeft = left + props.horizontalOffset + document.body.scrollLeft
//              content.setAttribute("style", s"top: ${offsetTop}px; left: ${offsetLeft}px")
//            }
//          }
//        )
//      } yield ()
//    }
    // scalastyle:on cyclomatic.complexity

    def render(props: Popover): VdomElement = {
      PortalWithState(
        children = renderChildren =>
          <.div(
            <.div.withRef(targetRef)(
              ^.onClick ==> renderChildren.openPortal,
              props.target
            ),
            renderChildren.portal(
              <.div(
                ^.classSet(
                  "at-popover" -> true,
                  props.position.className -> true,
                  "-show" -> renderChildren.isOpen
                ),
                props.content
              )
            )
        )
      )()
    }
  }

  private val component = ScalaComponent
    .builder[Popover](ComponentName)
    .initialState(State())
    .renderBackend[Backend]
    .build
}
