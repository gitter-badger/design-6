// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.modal

import anduin.component.button.{ButtonStyle, ProgressButton}
import anduin.component.portal.{ModalBody, ModalFooterWCancel}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class CommonConfirmModal(
  displayInfo: VdomNode,
  onConfirm: Callback => Callback,
  onClose: Callback,
  confirmBtnLabel: String = "Confirm"
) {
  def apply(): VdomElement = CommonConfirmModal.component(this)
}

object CommonConfirmModal {

  private val ComponentName = this.getClass.getSimpleName

  private case class State(
    confirmBtnStatus: ProgressButton.Status
  )

  private class Backend(scope: BackendScope[CommonConfirmModal, State]) {

    def render(props: CommonConfirmModal, state: State): VdomElement = {
      ReactFragment(
        ModalBody()(props.displayInfo),
        ModalFooterWCancel(cancel = props.onClose)(
          ProgressButton(
            color = ButtonStyle.ColorPrimary,
            status = state.confirmBtnStatus,
            labels = {
              case ProgressButton.Status.Loading => "Confirming"
              case ProgressButton.Status.Success => "Done"
              case ProgressButton.Status.Error   => "Failed to confirm, please try again"
              case ProgressButton.Status.Default | ProgressButton.Status.Disabled =>
                props.confirmBtnLabel
            },
            onClick = for {
              _ <- scope.modState(_.copy(confirmBtnStatus = ProgressButton.Status.Loading))
              _ <- props.onConfirm(props.onClose)
            } yield ()
          )()
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[CommonConfirmModal](ComponentName)
    .initialState(State(confirmBtnStatus = ProgressButton.Status.Default))
    .renderBackend[Backend]
    .build
}
