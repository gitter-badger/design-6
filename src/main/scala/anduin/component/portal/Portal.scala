// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import org.scalajs.dom

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

// This is a low level component so it should not have default values
private[portal] final case class Portal(
  // Rendering
  renderTarget: (Callback, Boolean) => VdomNode, // (Toggle, isOpened)
  renderContent: Callback => VdomNode, // (Toggle)
  // State management
  defaultIsOpened: Boolean, // For initial state
  isOpened: Option[Boolean], // Opt-out internal state (controlled Portal)
  // Event hooks (these are actually "afterOpen" and "afterClose")
  onOpen: Callback,
  onClose: Callback,
  // Other behaviours
  isPermanent: Boolean
) {
  def apply(): VdomElement = Portal.component(this)
}

private[portal] object Portal {

  private type Props = Portal

  private case class State(isOpened: Boolean)

  private class Backend(scope: BackendScope[Props, State]) {

    private def getIsOpened(props: Props, state: State) = {
      props.isOpened.getOrElse(state.isOpened)
    }

    private def toggle: Callback = {
      for {
        props <- scope.props
        state <- scope.state
        // Choose correct event hook to call
        eventHook = if (getIsOpened(props, state)) props.onClose else props.onOpen
        // If it's uncontrolled component: update internal state then call event hook
        _ <- Callback.when(props.isOpened.isEmpty) { scope.setState(State(!state.isOpened), eventHook) }
        // If it's controlled component:   just call event hook directly
        _ <- Callback.when(props.isOpened.isDefined) { eventHook }
      } yield ()
    }

    private def renderContent(props: Props): VdomNode = {
      ReactPortal(props.renderContent(toggle), dom.document.body)
    }

    def render(props: Props, state: State): VdomNode = {
      val isOpened = getIsOpened(props, state)
      ReactFragment(
        if (isOpened) renderContent(props) else EmptyVdom,
        props.renderTarget(toggle, isOpened)
      )
    }

    def willUnmount: Callback = {
      for {
        props <- scope.props
        state <- scope.state
        _ <- Callback.when(
          props.isPermanent && getIsOpened(props, state)
        ) {
          PortalUtils.detach { unmount =>
            val close = props.onClose >> unmount
            // Dangerous: we need a div wrapper here since only VdomElement
            // can detach
            <.div(props.renderContent(close))
          }
        }
      } yield ()
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialStateFromProps(props => State(props.defaultIsOpened))
    .renderBackend[Backend]
    .componentWillUnmount(_.backend.willUnmount)
    .build
}
