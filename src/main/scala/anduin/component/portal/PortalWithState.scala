// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class PortalWithState(
  nodeClasses: String = "",
  onOpen: Callback,
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

  case class Backend(scope: BackendScope[PortalWithState, State]) {

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
        Portal(
          nodeClasses = props.nodeClasses
        )(children)
      }
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
    .build
}
