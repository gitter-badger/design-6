// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.tab

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Tab(
  panels: Seq[Tab.Panel],
  defaultPanel: Int = 0,
  style: Tab.Style = Tab.StyleFull,
  // Uncontrolled tab -> Let parent controls the state
  active: Option[Int] = None,
  setActive: Option[Int => Callback] = None
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
  case object StyleMinimal extends Style
  // case object StyleMinimal extends Style
  // -> Currently we only support StyleFull. Will support StyleMinimal later

  // === Backend

  type Props = Tab

  private case class State(active: Int)

  private class Backend(scope: BackendScope[Props, State]) {

    private def selfSetActive(index: Int) = scope.setState(State(index))

    def render(props: Props, state: State): VdomElement = {
      // decide active based on whether this is an uncontrolled component or not
      val active = props.active.getOrElse(state.active)
      val setActive: Int => Callback =
        props.setActive.getOrElse(this.selfSetActive _)

      val panel = props.panels(active)
      val content = React.Fragment(
        panel.renderContent(),
        panel.renderContent_S(setActive)
      )
      val titles = props.panels.map(_.title).zipWithIndex
      props.style match {
        case StyleFull => TabFull(titles, content, active, setActive)()
        case StyleMinimal => TabMinimal(titles, content, active, setActive)()
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
