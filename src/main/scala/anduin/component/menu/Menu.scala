// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.menu

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

import anduin.style.Style

case class Menu() {
  def apply(children: VdomNode*): VdomElement =
    Menu.component(children: _*)
}

object Menu {
  private def render(children: PropsChildren): VdomElement =
    <.div(Style.margin.ver8, ^.minWidth := "180px", children)

  private val component = ScalaComponent
    .builder[Unit](this.getClass.getSimpleName)
    .stateless
    .render_C(render)
    .build
}
