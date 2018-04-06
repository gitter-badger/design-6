// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.menu

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.style.Style
// scalastyle:on underscore.import

object VerticalDivider {

  private val ComponentName = this.getClass.getSimpleName

  def apply(): VdomElement = {
    ScalaComponent.static(ComponentName)(
      <.div(
        Style.typography.truncate.backgroundColor.gray4.margin.horizontal1,
        ^.width := "1px",
        ^.height := "20px"
      )
    )()
  }
}
