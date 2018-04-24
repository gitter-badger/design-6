// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object ModalHeader {

  def apply(children: VdomNode*): VdomElement = {
    component(children: _*)
  }

  private val ComponentName = this.getClass.getSimpleName

  private val component = ScalaComponent
    .builder[Unit](ComponentName)
    .stateless
    .render_C { children =>
      <.div(^.cls := "modal-header", children)
    }
    .build
}
