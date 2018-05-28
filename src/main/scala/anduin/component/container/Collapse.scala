// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.container

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Collapse(
  render: (Callback, Boolean) => VdomNode,
  isExpanded: Boolean = false
) {
  def apply(): VdomElement = Collapse.component(this)
}

object Collapse {

  type Props = Collapse

  case class State(isExpanded: Boolean)

  def getStateFromProps(props: Props): State = {
    State(isExpanded = props.isExpanded)
  }

  private class Backend(scope: BackendScope[Collapse, State]) {

    def modStateFromProps: Callback = {
      scope.modState { (_, props) =>
        getStateFromProps(props)
      }
    }

    private def toggle = scope.modState { state =>
      state.copy(isExpanded = !state.isExpanded)
    }

    def render(props: Props, state: State): VdomNode = {
      props.render(toggle, state.isExpanded)
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialStateFromProps(this.getStateFromProps)
    .renderBackend[Backend]
    .componentWillReceiveProps(_.backend.modStateFromProps)
    .build
}
