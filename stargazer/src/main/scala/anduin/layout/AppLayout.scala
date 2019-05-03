// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.layout

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

// A pure layout with header and no additional layers
final case class AppLayout(
  headerContent: VdomNode = EmptyVdom,
  isFullHeight: Boolean = false
) {
  def apply(children: VdomNode*): VdomElement = {
    AppLayout.component(this)(children: _*)
  }
}

object AppLayout {

  private type Props = AppLayout

  private def render(props: Props, children: PropsChildren) = {
    <.div(
      TagMod.when(props.isFullHeight) {
        Style.height.vh100.flexbox.flex.flexbox.column
      },
      <.div(
        TagMod.when(props.isFullHeight)(Style.flexbox.none),
        props.headerContent
      ),
      <.div(
        TagMod.when(props.isFullHeight)(Style.flexbox.fill),
        children
      )
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
