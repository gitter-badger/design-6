// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.components.modal

import design.anduin.components.button.{Button, ButtonStyle}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class ConfirmationModal(
  content: VdomNode,
  onConfirm: Callback,
  confirmBtnLabel: String,
  confirmBtnColor: ButtonStyle.Color = Button.Color.Gray0,
  onCloseModal: Callback,
  cancelBtnLabel: String = "Cancel",
  isDisableConfirm: Boolean = false
) {
  def apply(): VdomElement = ConfirmationModal.component(this)
}

object ConfirmationModal {

  private type Props = ConfirmationModal

  private case class State(btnIsBusy: Boolean = false)

  private class Backend(scope: BackendScope[Props, State]) {
    def render(props: Props, state: State): VdomNode = {
      React.Fragment(
        ModalBody()(props.content),
        ModalFooterWCancel(cancel = props.onCloseModal, cancelLabel = props.cancelBtnLabel)(
          Button(
            style = Button.Style.Full(
              color = props.confirmBtnColor,
              isBusy = state.btnIsBusy
            ),
            isDisabled = props.isDisableConfirm,
            onClick = for {
              _ <- scope.modState(_.copy(btnIsBusy = true))
              _ <- props.onConfirm
            } yield ()
          )(props.confirmBtnLabel)
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialState(State())
    .renderBackend[Backend]
    .build
}
