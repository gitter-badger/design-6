// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.modal

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.Div

import anduin.component.button.ProgressButton
// scalastyle:on underscore.import

final case class CommonConfirmModal(
  displayInfo: TagMod,
  onConfirm: Callback => Callback,
  onClose: Callback,
  confirmBtnLabel: String = "Confirm",
  confirmBtnExtraClasses: String = ""
) {
  def apply(): ScalaComponent.Unmounted[_, _, _] = CommonConfirmModal.component(this)
}

object CommonConfirmModal {

  private val ComponentName = this.getClass.getSimpleName

  private case class State(
    confirmBtnStatus: ProgressButton.Status
  )

  private case class Backend(scope: BackendScope[CommonConfirmModal, State]) {

    def render(props: CommonConfirmModal, state: State): VdomTagOf[Div] = {
      <.div(
        <.div(^.cls := "modal-body", props.displayInfo),
        <.div(
          ^.cls := "modal-footer",
          <.div(
            ^.cls := "btn-group ml-auto",
            <.button(
              ^.cls := "btn",
              ^.onClick --> props.onClose,
              "Cancel"
            ),
            ProgressButton(
              status = state.confirmBtnStatus,
              extraClasses = props.confirmBtnExtraClasses,
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
      )
    }
  }

  private val component = ScalaComponent
    .builder[CommonConfirmModal](ComponentName)
    .initialState(State(confirmBtnStatus = ProgressButton.Status.Default))
    .renderBackend[Backend]
    .build
}
