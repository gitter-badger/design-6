// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.toggle

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Collapse(
  renderTarget: (Callback, Boolean) => VdomNode,
  renderContent: Boolean => VdomNode
) {
  def apply(): VdomElement = {
    Collapse.component(this)
  }
}

object Collapse {

  private val ComponentName = this.getClass.getSimpleName

  private case class State(toggled: Boolean = true)

  private class Backend(scope: BackendScope[Collapse, State]) {

    private def toggle = {
      scope.modState { state =>
        state.copy(toggled = !state.toggled)
      }
    }

    def render(props: Collapse, state: State): VdomNode = {
      VdomArray(
        props.renderTarget(toggle, state.toggled),
        props.renderContent(state.toggled)
      )
    }
  }

  private val component = ScalaComponent
    .builder[Collapse](ComponentName)
    .initialState(State())
    .renderBackend[Backend]
    .build
}
