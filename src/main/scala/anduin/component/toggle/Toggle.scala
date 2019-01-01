// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.toggle

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Toggle(
  render: (Callback, Boolean) => VdomNode,
  isExpanded: Boolean = false
) {
  def apply(): VdomElement = Toggle.component(this)
}

object Toggle {

  type Props = Toggle

  case class State(isExpanded: Boolean)

  private class Backend(scope: BackendScope[Toggle, State]) {

    private def toggle = scope.modState { state =>
      state.copy(isExpanded = !state.isExpanded)
    }

    def render(props: Props, state: State): VdomNode = {
      props.render(toggle, state.isExpanded)
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialStateFromProps(props => State(isExpanded = props.isExpanded))
    .renderBackend[Backend]
    .build
}
