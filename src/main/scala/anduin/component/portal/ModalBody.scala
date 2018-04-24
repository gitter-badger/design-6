// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

case class ModalBody() {
  def apply(children: VdomNode*): VdomElement = {
    ModalBody.component(this)(children: _*)
  }
}

object ModalBody {

  private val ComponentName = this.getClass.getSimpleName

  private val component = ScalaComponent
    .builder[ModalBody](ComponentName)
    .stateless
    .render_C { children =>
      <.div(^.cls := "modal-body", children)
    }
    .build
}
