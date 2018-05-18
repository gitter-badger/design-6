// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.menu

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

import anduin.style.Style

case class MenuDivider() {
  def apply(): VdomElement = MenuDivider.component()
}

object MenuDivider {
  private def render =
    <.div(Style.backgroundColor.gray4.flexbox.flex.margin.ver8.width.pc100, ^.height := "1px")
  private val component = ScalaComponent.static(this.getClass.getSimpleName)(render)
}
