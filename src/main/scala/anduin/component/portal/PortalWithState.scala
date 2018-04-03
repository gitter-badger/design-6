// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import japgolly.scalajs.react.extra.{EventListener, OnUnmount}
import org.scalajs.dom
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.raw.{KeyboardEvent, MouseEvent, Node}

import anduin.component.util.EventUtils

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class PortalWithState(
  nodeClasses: String = "",
  onOpen: Callback = Callback.empty,
  onClose: Callback = Callback.empty,
  closeOnEsc: Boolean = true,
  closeOnInsideClick: Boolean = true,
  closeOnOutsideClick: Boolean = true,
  renderChildren: PortalWithState.RenderChildren => VdomNode
) {
  def apply(): VdomElement = {
    PortalWithState.component(this)
  }
}

object PortalWithState {

  sealed trait Status
  case object StatusOpen extends Status
  case object StatusClose extends Status
  case object StatusHide extends Status

  case class RenderChildren(
    openPortal: Callback,
    closePortal: Callback,
    portal: VdomElement => VdomNode,
    status: Status
  )

  private val ComponentName = this.getClass.getSimpleName

  case class State(status: Status = StatusClose)

  case class Backend(scope: BackendScope[PortalWithState, State]) extends OnUnmount {

    private val portalRef = Ref.toScalaComponent(Portal.component)

    private var shouldCloseOpt: Option[Boolean] = None // scalastyle:off var.field

    private def openPortal = {
      for {
        props <- scope.props
        state <- scope.state
        _ <- Callback.when(state.status != StatusOpen) {
          this.shouldCloseOpt = Some(false)
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

    private def wrapPortal(props: PortalWithState, state: State)(children: VdomElement) = {
      if (state.status == StatusClose) {
        EmptyVdom
      } else {
        portalRef
          .component(
            Portal(
              nodeClasses = props.nodeClasses
            )
          )(children)
          .vdomElement
      }
    }

    def onDocumentClick(e: MouseEvent): Callback = {
      for {
        props <- scope.props
        state <- scope.state
        portal <- portalRef.get
        _ <- Callback {
          if (this.shouldCloseOpt.isEmpty) {
            this.shouldCloseOpt = Some(true)
          }
        }
        node = portal.backend.getNode
        _ <- Callback.when(
          this.shouldCloseOpt.contains(true) && state.status == StatusOpen
            && Option(node).nonEmpty && EventUtils.leftButtonClicked(e)
        ) {
          val clickInside = e.target match {
            case t: Node => node.contains(t)
            case _       => false
          }
          if (clickInside) {
            Callback.when(props.closeOnInsideClick)(scope.modState(_.copy(status = StatusHide)))
          } else {
            Callback.when(props.closeOnOutsideClick)(closePortal)
          }
        }
        _ <- Callback {
          this.shouldCloseOpt = None
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
          wrapPortal(props, state),
          state.status
        )
      )
    }
  }

  val component = ScalaComponent
    .builder[PortalWithState](ComponentName)
    .initialState(State())
    .renderBackend[Backend]
    .configure(
      EventListener[MouseEvent].install("click", _.backend.onDocumentClick, _ => dom.document),
      EventListener[KeyboardEvent].install("keydown", _.backend.onDocumentKeydown, _ => dom.document)
    )
    .build
}
