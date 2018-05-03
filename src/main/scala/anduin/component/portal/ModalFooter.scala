// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object ModalFooter {

  def apply(children: VdomNode*): VdomElement = {
    component(children: _*)
  }

  private val ComponentName = this.getClass.getSimpleName

  private val component = ScalaComponent
    .builder[Unit](ComponentName)
    .stateless
    .render_C { children =>
      <.div(Style.padding.hor20.padding.bottom20, children)
    }
    .build
}
