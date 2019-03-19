// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.tooltip

import anduin.component.portal.{PortalPosition, PortalWrapper}
import org.scalajs.dom.raw.HTMLElement

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Tooltip(
  renderTarget: VdomNode,
  renderContent: () => String,
  position: PortalPosition = PortalPosition.TopCenter,
  targetWrapper: PortalWrapper = PortalWrapper.BlockContent,
  isDisabled: Boolean = false
) {
  def apply(): VdomElement = Tooltip.component(this)
}

object Tooltip {

  private type Props = Tooltip

  private case class State(isOpen: Boolean)

  private class Backend(scope: BackendScope[Props, State]) {

    private val targetRef: Ref.Simple[HTMLElement] = Ref[HTMLElement]

    private def renderContent(props: Props, state: State): VdomNode = {
      if (state.isOpen && !props.isDisabled) {
        TooltipContent(targetRef, props.position, props.renderContent())()
      } else {
        EmptyVdom
      }
    }

    private def renderTarget(props: Props, state: State): VdomElement = {
      props.targetWrapper.tag.withRef(targetRef)(
        // Mouse navigation
        ^.onMouseEnter --> scope.modState(_.copy(isOpen = true)),
        ^.onMouseLeave --> scope.modState(_.copy(isOpen = false)),
        // Keyboard navigation
        ^.onFocusCapture --> scope.modState(_.copy(isOpen = true)),
        ^.onBlurCapture --> scope.modState(_.copy(isOpen = false)),
        // Users' content
        props.renderTarget
      )
    }

    def render(props: Props, state: State): VdomElement = {
      React.Fragment(
        renderTarget(props, state),
        renderContent(props, state)
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialState(State(isOpen = false))
    .renderBackend[Backend]
    .build
}
