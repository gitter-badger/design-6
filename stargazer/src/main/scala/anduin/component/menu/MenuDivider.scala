// Copyright (C) 2014-2019 Anduin Transactions Inc.

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
  private val render = <.div(Style.background.gray3.margin.ver8.height.px1)
  private val component = ScalaComponent.static(this.getClass.getSimpleName)(render)
}
