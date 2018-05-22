// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.container

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Collapse(
  renderTarget: (Callback, Boolean) => VdomNode,
  renderContent: Boolean => VdomNode
) {
  def apply(): VdomElement = Collapse.component(this)
}

object Collapse {

  private case class State(toggled: Boolean = true)

  private class Backend(scope: BackendScope[Collapse, State]) {

    private def toggle = scope.modState { state =>
      state.copy(toggled = !state.toggled)
    }

    def render(props: Collapse, state: State): VdomNode = {
      ReactFragment(
        props.renderTarget(toggle, state.toggled),
        props.renderContent(state.toggled)
      )
    }
  }

  private val component = ScalaComponent
    .builder[Collapse](this.getClass.getSimpleName)
    .initialState(State())
    .renderBackend[Backend]
    .build
}
