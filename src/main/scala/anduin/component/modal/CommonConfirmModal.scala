// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.modal

import anduin.component.button.Button

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class CommonConfirmModal(
  displayInfo: VdomNode,
  onConfirm: Callback => Callback,
  onClose: Callback,
  confirmBtnLabel: String = "Confirm",
  cancelBtnLabel: String = "Cancel"
) {
  def apply(): VdomElement = CommonConfirmModal.component(this)
}

object CommonConfirmModal {

  private val ComponentName = this.getClass.getSimpleName

  private case class State(
    confirmBtnIsBusy: Boolean
  )

  private class Backend(scope: BackendScope[CommonConfirmModal, State]) {

    def render(props: CommonConfirmModal, state: State): VdomElement = {
      React.Fragment(
        ModalBody()(props.displayInfo),
        ModalFooterWCancel(cancel = props.onClose, cancelLabel = props.cancelBtnLabel)(
          Button(
            style = Button.Style.Full(color = Button.Color.Blue, isBusy = state.confirmBtnIsBusy),
            onClick = for {
              _ <- scope.modState(_.copy(confirmBtnIsBusy = true))
              _ <- props.onConfirm(props.onClose)
            } yield ()
          )(props.confirmBtnLabel)
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[CommonConfirmModal](ComponentName)
    .initialState(State(confirmBtnIsBusy = false))
    .renderBackend[Backend]
    .build
}
