// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.modal

import anduin.component.button.{ButtonStyle, ProgressButton}

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

  private type Props = ConfirmationModal
  private case class State(
    btnStatus: ProgressButton.Status = ProgressButton.Status.Default
  )

  private class Backend(scope: BackendScope[Props, State]) {
    def render(props: Props, state: State): VdomNode = {
      React.Fragment(
        ModalBody()(props.actionText),
        ModalFooterWCancel(cancel = props.onCloseModal)(
          ProgressButton(
            color = props.confirmBtnColor,
            status = state.btnStatus,
            isDisabled = props.isDisableConfirm,
            onClick = for {
              _ <- scope.modState(_.copy(btnStatus = ProgressButton.Status.Loading))
              _ <- props.onConfirm
            } yield (),
            labels = _ => props.confirmBtnLabel
          )()
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[ConfirmationModal](ComponentName)
    .initialState(State())
    .renderBackend[Backend]
    .build
}
