// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.modal

import anduin.component.button.{Button, ButtonStyle}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class ConfirmationModal(
  actionText: VdomNode,
  onConfirm: Callback,
  confirmBtnLabel: String,
  confirmBtnColor: ButtonStyle.Color = ButtonStyle.ColorWhite,
  onCloseModal: Callback,
  isDisableConfirm: Boolean = false
) {
  def apply(): VdomElement = ConfirmationModal.component(this)
}

object ConfirmationModal {

  private val ComponentName = this.getClass.getSimpleName

  private val component = ScalaComponent
    .builder[ConfirmationModal](ComponentName)
    .stateless
    .render_P { props =>
      React.Fragment(
        ModalBody()(props.actionText),
        ModalFooterWCancel(cancel = props.onCloseModal)(
          Button(
            onClick = props.onConfirm,
            color = props.confirmBtnColor,
            isDisabled = props.isDisableConfirm
          )(props.confirmBtnLabel)
        )
      )
    }
    .build
}
