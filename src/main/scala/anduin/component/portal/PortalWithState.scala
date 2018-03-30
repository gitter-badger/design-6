// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import japgolly.scalajs.react.extra.{EventListener, OnUnmount}
import org.scalajs.dom
import org.scalajs.dom.raw.MouseEvent

import anduin.component.util.EventUtils

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class PortalWithState(
  nodeClasses: String = "",
  onOpen: Callback = Callback.empty,
  closeOnEsc: Boolean = true,
  closeOnOutsideClick: Boolean = true,
  children: PortalWithState.RenderChildren => VdomNode
) {
  def apply(): VdomElement = {
    PortalWithState.component(this)
  }
}

object PortalWithState {

  case class RenderChildren(
    openPortal: ReactEvent => Callback,
    closePortal: Callback,
    portal: VdomElement => VdomNode,
    isOpen: Boolean
  )

  private val ComponentName = this.getClass.getSimpleName

  case class State(active: Boolean = false)

  case class Backend(scope: BackendScope[PortalWithState, State]) extends OnUnmount {

    private val portalRef = Ref.toScalaComponent(Portal.component)

    private def openPortal(e: ReactEvent) = {
      for {
        props <- scope.props
        state <- scope.state
        _ <- Callback.when(!state.active) {
          e.nativeEvent.stopImmediatePropagation()
          scope.modState(_.copy(active = true), props.onOpen)
        }
      } yield ()
    }

    private def closePortal = {
      for {
        state <- scope.state
        _ <- Callback.when(state.active) {
          scope.modState(_.copy(active = false))
        }
      } yield ()
    }

    private def wrapPortal(props: PortalWithState, state: State)(children: VdomElement) = {
      if (!state.active) {
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
        _ <- Callback.when(props.closeOnOutsideClick && state.active) {
          val node = portal.backend.getNode
          Callback.when(Option(node).nonEmpty && !EventUtils.clickInside(e, node) && EventUtils.leftButtonClicked(e)) {
            closePortal
          }
        }
      } yield ()
    }

    def render(props: PortalWithState, state: State): VdomNode = {
      props.children(
        RenderChildren(
          openPortal,
          closePortal,
          wrapPortal(props, state),
          state.active
        )
      )
    }
  }

  val component = ScalaComponent
    .builder[PortalWithState](ComponentName)
    .initialState(State())
    .renderBackend[Backend]
    .configure(
      EventListener[MouseEvent].install("click", _.backend.onDocumentClick, _ => dom.document)
    )
    .build
}
