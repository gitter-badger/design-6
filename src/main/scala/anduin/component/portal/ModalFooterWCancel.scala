// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import anduin.component.button.Button
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

case class ModalFooterWCancel(
  cancel: Callback,
  cancelLabel: String = "Cancel"
) {
  def apply(children: VdomNode*): VdomElement = {
    ModalFooterWCancel.component(this)(children: _*)
  }
}

object ModalFooterWCancel {
  private val component = ScalaComponent
    .builder[ModalFooterWCancel](this.getClass.getSimpleName)
    .stateless
    .render_PC { (props: ModalFooterWCancel, children) =>
      ModalFooter(
        <.div(
          Style.flexbox.flex.flexbox.justifyEnd,
          <.div(Style.margin.right8, Button(onClick = props.cancel)(props.cancelLabel)),
          children
        )
      )
    }
    .build
}
