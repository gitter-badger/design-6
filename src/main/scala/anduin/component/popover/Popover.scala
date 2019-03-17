// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.popover

import anduin.component.portal.{PortalPosition, PortalWrapper}
import org.scalajs.dom.raw.HTMLElement

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Popover(
  renderTarget: (Callback, Boolean) => VdomNode,
  renderContent: Callback => VdomNode,
  position: PortalPosition = PortalPosition.TopCenter,
  targetWrapper: PortalWrapper = PortalWrapper.BlockContent
) {
  def apply(): VdomElement = Popover.component(this)
}

object Popover {

  private type Props = Popover

  private case class State(isOpened: Boolean)

  private class Backend(scope: BackendScope[Props, State]) {

    private val targetRef: Ref.Simple[HTMLElement] = Ref[HTMLElement]

    private def toggle: Callback = scope.modState { state =>
      state.copy(isOpened = !state.isOpened)
    }

    private def renderContent(props: Props, state: State): VdomNode = {
      if (state.isOpened) {
        PopoverContent(
          targetRef = targetRef,
          onOverlayClick = Some(toggle),
          position = props.position
        )(props.renderContent(toggle))
      } else {
        EmptyVdom
      }
    }

    private def renderTarget(props: Props, state: State): VdomElement = {
      val children = props.renderTarget(toggle, state.isOpened)
      props.targetWrapper.tag.withRef(targetRef)(children)
    }

    def render(props: Props, state: State): VdomElement = {
      React.Fragment(renderTarget(props, state), renderContent(props, state))
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialState(State(isOpened = false))
    .renderBackend[Backend]
    .build
}
