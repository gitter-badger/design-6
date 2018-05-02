// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import scala.concurrent.duration
import scala.concurrent.duration.FiniteDuration

import japgolly.scalajs.react.extra.TimerSupport
import org.scalajs.dom.raw.Element

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[portal] final case class PortalWrapper(
  isOpen: Boolean = false,
  onOpen: Callback = Callback.empty,
  onClose: Callback = Callback.empty,
  closeOnEsc: Boolean = true,
  closeOnOutsideClick: Boolean = true,
  // A callback to check if user click inside the portal
  // The first parameter presents the node which is clicked
  // The second parameter presents the portal node
  isPortalClicked: (Element, Element) => CallbackTo[Boolean] = PortalWrapper.IsPortalClicked,
  renderTarget: (Callback, Callback, Status) => VdomNode,
  renderContent: (Callback, Status) => VdomNode
) {
  def apply(): VdomElement = {
    PortalWrapper.component(this)
  }
}

private[portal] object PortalWrapper {

  val IsPortalClicked: (Element, Element) => CallbackTo[Boolean] =
    (target: Element, portal: Element) => CallbackTo(portal.contains(target))

  private val ComponentName = this.getClass.getSimpleName

  private case class State(status: Status = StatusClose)

  private case class Backend(scope: BackendScope[PortalWrapper, State]) extends TimerSupport {

    private val portalRef = Ref.toScalaComponent(LegacyPortal.component)

    private def openPortal = {
      for {
        props <- scope.props
        state <- scope.state
        _ <- Callback.when(state.status != StatusOpen) {
          // When the status is open, the portal is rendered.
          // But `ReactDom.renderSubtreeIntoContainer` used in `LegacyPortal` is async, we have to use
          // `setTimeout(..., 0)` to ensure that the rendering process is done completely.
          // Hence, `props.onOpen` is able to access the element inside the portal without any problems.
          // Notice that we don't need this if use `Portal`.
          scope.modState(_.copy(status = StatusOpen), setTimeout(props.onOpen, FiniteDuration(0, duration.SECONDS)))
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

    private def destroyPortal = {
      for {
        portal <- portalRef.get
        _ <- portal.backend.destroy(portal.props)
      } yield ()
    }

    def render(props: PortalWrapper, state: State): VdomElement = {
      // We don't need to wrap a `div` here because the portal's `render` actually doesn't render anything
      ReactFragment(
        props.renderTarget(openPortal, destroyPortal, state.status),
        portalRef.component(
          LegacyPortal(
            status = state.status,
            children = props.renderContent,
            closeOnOutsideClick = props.closeOnOutsideClick,
            isPortalClicked = props.isPortalClicked,
            onClose = closePortal
          )
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[PortalWrapper](ComponentName)
    .initialStateFromProps { props =>
      val status = if (props.isOpen) StatusOpen else StatusClose
      State(status = status)
    }
    .renderBackend[Backend]
    .componentWillReceiveProps { scope =>
      Callback.when(scope.nextProps.isOpen != scope.currentProps.isOpen) {
        val status = if (scope.nextProps.isOpen) StatusOpen else StatusClose
        scope.modState(_.copy(status = status))
      }
    }
    .configure(
      TimerSupport.install
    )
    .build
}
