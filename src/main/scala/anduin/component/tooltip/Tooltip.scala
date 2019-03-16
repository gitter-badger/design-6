// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.tooltip

import anduin.component.portal.{PortalPosition, PortalTargetWrapper}
import org.scalajs.dom.raw.HTMLElement

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Tooltip(
                          renderTarget: VdomNode,
                          renderContent: () => String,
                          position: PortalPosition = PortalPosition.TopCenter,
                          targetWrapper: PortalTargetWrapper = PortalTargetWrapper.BlockContent,
                          isDisabled: Boolean = true
) {
  def apply(): VdomElement = Tooltip.component(this)
}

object Tooltip {

  private type Props = Tooltip

  private case class State(isOpened: Boolean)

  private class Backend(scope: BackendScope[Props, State]) {

    private val targetRef: Ref.Simple[HTMLElement] = Ref[HTMLElement]

    private val eventListeners = TagMod(
      ^.onMouseEnter --> scope.modState(_.copy(isOpened = true)),
      ^.onMouseLeave --> scope.modState(_.copy(isOpened = false))
    )

    private def renderContent(props: Props, state: State): VdomNode = {
      if (state.isOpened) {
        TooltipContent(targetRef, props.position, props.renderContent())()
      } else {
        EmptyVdom
      }
    }

    private def renderTarget(props: Props, state: State): VdomElement = {
      val children = TagMod(props.renderTarget, eventListeners)
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
