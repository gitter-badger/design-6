// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.container

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Tab(
  panels: List[Tab.Panel],
  defaultPanel: Int = 0,
  style: Tab.Style = Tab.StyleFull
) {
  def apply(): VdomElement = Tab.component(this)
}

object Tab {

  // === Props

  case class Panel(
    title: VdomNode,
    renderContent: () => VdomNode = () => EmptyVdom,
    renderContent_S: (Int => Callback) => VdomNode = _ => EmptyVdom
  )

  sealed trait Style
  case object StyleFull extends Style
  // case object StyleMinimal extends Style
  // -> Currently we only support StyleFull. Will support StyleMinimal later

  // === Backend

  type Props = Tab

  private case class State(active: Int)

  private class Backend(scope: BackendScope[Props, State]) {

    private def setActive(index: Int) = scope.setState { State(index) }

    def render(props: Props, state: State): VdomElement = {
      val panel = props.panels(state.active)
      val content = ReactFragment(
        panel.renderContent(),
        panel.renderContent_S(setActive)
      )
      val titles = props.panels.map(_.title).zipWithIndex
      props.style match {
        case StyleFull => TabFull(titles, content, state.active, setActive)()
        // case StyleMinimal => TabFull(props.panels, state.active, setActive)()
      }
    }
  }

  // ===

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialStateFromProps(props => State(props.defaultPanel))
    .renderBackend[Backend]
    .build

}
