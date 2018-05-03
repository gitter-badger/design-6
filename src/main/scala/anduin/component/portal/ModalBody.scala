// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.style.Style
// scalastyle:on underscore.import

object ModalBody {

  def apply(children: VdomNode*): VdomElement = {
    component(children: _*)
  }

  private val ComponentName = this.getClass.getSimpleName

  private val component = ScalaComponent
    .builder[Unit](ComponentName)
    .stateless
    .render_C { children =>
      <.div(Style.padding.all20, children)
    }
    .build
}
