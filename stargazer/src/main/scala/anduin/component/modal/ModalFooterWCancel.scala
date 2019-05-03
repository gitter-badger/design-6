// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.modal

import anduin.component.button.Button
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class ModalFooterWCancel(
  cancel: Callback,
  cancelLabel: String = "Cancel"
) {
  def apply(children: VdomNode*): VdomElement =
    ModalFooterWCancel.component(this)(children: _*)
}

object ModalFooterWCancel {

  private type Props = ModalFooterWCancel

  private def render(props: Props, children: PropsChildren) = {
    ModalFooter()(
      <.div(
        Style.flexbox.flex.flexbox.justifyEnd,
        <.div(Style.margin.right8, Button(onClick = props.cancel)(props.cancelLabel)),
        children
      )
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
