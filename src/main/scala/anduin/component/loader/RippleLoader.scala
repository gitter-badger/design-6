// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.loader

import japgolly.scalajs.react.ScalaComponent

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object RippleLoader {
  def apply(): VdomElement = {
    ScalaComponent.static(this.getClass.getSimpleName)(
      <.div(
        ^.cls := "acl-ripple",
        <.div(),
        <.div()
      )
    )()
  }
}
