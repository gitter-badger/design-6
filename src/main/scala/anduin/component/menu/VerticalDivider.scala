// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.menu

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.style.Style
// scalastyle:on underscore.import

object VerticalDivider {
  private val component = ScalaComponent.static(this.getClass.getSimpleName)(
    <.div(Style.background.gray4.margin.hor4, ^.width := "1px", ^.height := "20px")
  )()
  def apply(): VdomElement = component
}
