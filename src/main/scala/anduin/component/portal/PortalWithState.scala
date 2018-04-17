// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import japgolly.scalajs.react.extra.{EventListener, OnUnmount}
import org.scalajs.dom
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.raw.{Element, KeyboardEvent, MouseEvent}

import anduin.component.util.EventUtils

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class PortalWithState(
  isOpen: Boolean = false,
  onOpen: Callback = Callback.empty,
  onClose: Callback = Callback.empty,
  closeOnEsc: Boolean = true,
  closeOnInsideClick: Boolean = false,
  closeOnOutsideClick: Boolean = true,
  // A callback to check if user click inside the portal
  // The first parameter presents the node which is clicked
  // The second parameter presents the portal node
  isPortalClicked: (Element, Element) => CallbackTo[Boolean] = PortalWithState.IsPortalClicked,
  renderChildren: PortalWithState.RenderChildren => VdomNode
) {
  def apply(): VdomElement = {
    PortalWithState.component(this)
  }
}

object PortalWithState {

  case class RenderChildren(
    openPortal: Callback,
    closePortal: Callback,
    portal: VdomElement => VdomNode,
    status: Status
  )

  val IsPortalClicked = (target: Element, portal: Element) => CallbackTo(portal.contains(target))

  private val ComponentName = this.getClass.getSimpleName

  case class State(status: Status = StatusClose)

  case class Backend(scope: BackendScope[PortalWithState, State]) extends OnUnmount {

    private val portalRef = Ref.toScalaComponent(LegacyPortal.component)

    private var shouldCloseOpt: Option[Boolean] = None // scalastyle:off var.field

    private def openPortal = {
      for {
        props <- scope.props
        state <- scope.state
        _ <- Callback.when(state.status != StatusOpen) {
          shouldCloseOpt = Some(false)
          scope.modState(_.copy(status = StatusOpen), props.onOpen)
        }
      } yield ()
    }

    private def closePortal = {
      for {
        props <- scope.props
        state <- scope.state
        _ <- Callback.when(state.status != StatusClose) {
          scope.modState(_.copy(status = StatusClose), props.onClose)
        }
      } yield ()
    }

    private def wrapPortal(state: State)(children: VdomElement) = {
      portalRef
        .component(LegacyPortal(status = state.status))(children)
        .vdomElement
    }

    def onDocumentClick(e: MouseEvent): Callback = {
      for {
        props <- scope.props
        state <- scope.state
        portal <- portalRef.get
        _ <- Callback {
          if (shouldCloseOpt.isEmpty) {
            shouldCloseOpt = Some(true)
          }
        }
        node = portal.backend.getNode
        _ <- Callback.when(
          shouldCloseOpt.contains(true) && state.status == StatusOpen
            && Option(node).nonEmpty && EventUtils.leftButtonClicked(e)
        ) {
          val clickInside = e.target match {
            case t: Element => props.isPortalClicked(t, node)
            case _          => CallbackTo(false)
          }
          clickInside.flatMap { isInside =>
            if (isInside) {
              Callback.when(props.closeOnInsideClick)(scope.modState(_.copy(status = StatusHide)))
            } else {
              Callback.when(props.closeOnOutsideClick)(closePortal)
            }
          }
        }
        _ <- Callback {
          shouldCloseOpt = None
        }
      } yield ()
    }

    def onDocumentKeydown(e: KeyboardEvent): Callback = {
      for {
        state <- scope.state
        _ <- Callback.when(e.keyCode == KeyCode.Escape && state.status == StatusOpen) {
          closePortal
        }
      } yield ()
    }

    def render(props: PortalWithState, state: State): VdomNode = {
      props.renderChildren(
        RenderChildren(
          openPortal,
          closePortal,
          wrapPortal(state),
          state.status
        )
      )
    }
  }

  val component = ScalaComponent
    .builder[PortalWithState](ComponentName)
    .initialStateFromProps { props =>
      val status = if (props.isOpen) StatusOpen else StatusClose
      State(status = status)
    }
    .renderBackend[Backend]
    .configure(
      EventListener[MouseEvent].install("click", _.backend.onDocumentClick, _ => dom.document),
      EventListener[KeyboardEvent].install("keydown", _.backend.onDocumentKeydown, _ => dom.document)
    )
    .build
}
