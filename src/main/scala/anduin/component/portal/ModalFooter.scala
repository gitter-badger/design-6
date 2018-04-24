// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

case class ModalFooter() {
  def apply(children: VdomNode*): VdomElement = {
    ModalFooter.component(this)(children: _*)
  }
}

object ModalFooter {

  private val ComponentName = this.getClass.getSimpleName

  private val component = ScalaComponent
    .builder[ModalFooter](ComponentName)
    .stateless
    .render_C { children =>
      <.div(^.cls := "modal-footer", children)
    }
    .build
}
