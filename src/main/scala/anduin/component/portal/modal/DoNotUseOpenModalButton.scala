// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal.modal

import anduin.component.button.{Button, ButtonStyle}
import anduin.component.portal.tooltip.Tooltip
import anduin.component.portal.{Position, PositionTopCenter}
import anduin.scalajs.react.hammer.ReactHammer

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

// scalastyle:off parameter.number
final case class DoNotUseOpenModalButton(
  buttonClasses: String = "flex items-center",
  buttonColor: ButtonStyle.Color = ButtonStyle.ColorWhite,
  buttonTpe: Button.Tpe = Button.TpeButton,
  buttonStyle: ButtonStyle.Style = ButtonStyle.StyleLink,
  buttonSize: ButtonStyle.Size = ButtonStyle.Size32,
  buttonIsFullWidth: Boolean = false,
  disabled: Boolean = false,
  tip: String = "",
  tipPlacement: Position = PositionTopCenter,
  modalTitle: String = "",
  modalClasses: String = "",
  modalBody: Callback => VdomNode = _ => EmptyVdom,
  modalHeader: Option[DoNotUseModal.ModalHeaderRenderer] = None,
  modalStyle: Map[String, String] = Map.empty,
  overlayCloseable: Boolean = true,
  closeOnEscape: Boolean = true,
  isOpen: Boolean = false,
  onBeforeShowing: Callback = Callback.empty,
  onAfterHiding: Callback = Callback.empty,
  showCloseBtn: Boolean = true,
  // If it is on mobile we use tap instead of click
  isOnMobile: Boolean = false,
  // Add key when we want to distinguish between different button
  keyOpt: Option[String] = None
) {
  def apply(children: VdomNode*): VdomElement = {
    val ctorType =
      keyOpt.map(DoNotUseOpenModalButton.component.withKey(_)).getOrElse(DoNotUseOpenModalButton.component.ctor)
    ctorType(this)(children: _*)
  }
}
// scalastyle:on parameter.number

object DoNotUseOpenModalButton {

  private val componentName = this.getClass.getSimpleName

  private case class State(isOpen: Boolean, over: Boolean)

  private class Backend(scope: BackendScope[DoNotUseOpenModalButton, State]) {

    private def show(): Callback = {
      for {
        props <- scope.props
        _ <- Callback.unless(props.disabled) {
          for {
            _ <- props.onBeforeShowing
            _ <- scope.modState(_.copy(isOpen = true))
          } yield ()
        }
      } yield ()
    }

    private def show(e: ReactHammer.Event): Callback = {
      for {
        _ <- e.srcEvent.stopPropagationCB
        props <- scope.props
        _ <- Callback.unless(props.disabled) {
          for {
            _ <- props.onBeforeShowing
            _ <- scope.modState(_.copy(isOpen = true))
          } yield ()
        }
      } yield ()
    }

    private def hide = {
      for {
        props <- scope.props
        _ <- props.onAfterHiding
        _ <- scope.modState(_.copy(isOpen = false))
      } yield ()
    }

    def render(props: DoNotUseOpenModalButton, state: State, children: PropsChildren): VdomElement = {
      val renderBtn = Button(
        onClick = show(),
        isDisabled = props.disabled,
        isFullWidth = props.buttonIsFullWidth,
        color = props.buttonColor,
        style = props.buttonStyle,
        size = props.buttonSize,
        tpe = props.buttonTpe
      )(
        children
      )

      <.span(
        ReactHammer(onTap = (e: ReactHammer.Event) => Callback.when(props.isOnMobile) { show(e) })(
          <.span(if (props.tip.nonEmpty) {
            Tooltip(
              renderTarget = renderBtn,
              renderContent = () => props.tip,
              position = props.tipPlacement
            )()
          } else {
            renderBtn
          })
        ),
        TagMod.unless(props.disabled) {
          DoNotUseModal(
            title = props.modalTitle,
            isOpen = state.isOpen,
            overlayClassName = props.modalClasses,
            shouldCloseOnOverlayClick = props.overlayCloseable,
            shouldCloseOnEscape = props.closeOnEscape,
            onRequestClose = hide,
            showCloseBtn = props.showCloseBtn,
            renderHeader = props.modalHeader,
            contentStyles = props.modalStyle
          )(props.modalBody)
        }
      )
    }
  }

  private val component = ScalaComponent
    .builder[DoNotUseOpenModalButton](componentName)
    .initialStateFromProps { props =>
      State(
        isOpen = props.isOpen,
        over = false
      )
    }
    .renderBackendWithChildren[Backend]
    .build
}
