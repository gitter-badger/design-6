// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.modal

import anduin.component.tooltip.Tooltip
import anduin.component.util.JavaScriptUtils
import anduin.scalajs.react.hammer.ReactHammer

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

// scalastyle:off parameter.number
final case class OpenModalButton(
  buttonLabel: String,
  buttonClasses: String = "",
  disabled: Boolean = false,
  tip: String = "",
  tipPlacement: Tooltip.Placement = Tooltip.Placement.Top,
  modalTitle: String = "",
  modalClasses: String = "",
  modalBody: Callback => VdomNode = _ => EmptyVdom,
  modalHeader: Option[Modal.ModalHeaderRenderer] = None,
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
      keyOpt.map(OpenModalButton.component.withKey(_)).getOrElse(OpenModalButton.component.ctor)
    ctorType(this)(children: _*)
  }
}
// scalastyle:on parameter.number

object OpenModalButton {

  private val componentName = this.getClass.getSimpleName

  private case class State(isOpen: Boolean, over: Boolean)

  private case class Backend(scope: BackendScope[OpenModalButton, State]) {

    private def show(e: ReactEventFromHtml): Callback = {
      for {
        _ <- e.stopPropagationCB
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

    def render(props: OpenModalButton, state: State, children: PropsChildren): VdomElement = {
      <.span(
        ReactHammer(
          onTap = (e: ReactHammer.Event) =>
            Callback.when(
              props.isOnMobile
            ) {
              show(e)
          }
        )(
          <.a(
            ^.href := JavaScriptUtils.voidMethod,
            ^.classSet(
              s"dib ${props.buttonClasses}" -> true,
              "disabled" -> props.disabled
            ),
            TagMod.when(!props.isOnMobile) {
              ^.onClick ==> show
            },
            if (props.tip.nonEmpty) {
              Tooltip(tip = props.tip, placement = props.tipPlacement)(props.buttonLabel)
            } else {
              props.buttonLabel
            },
            if (props.buttonLabel.isEmpty && props.tip.nonEmpty) {
              Tooltip(tip = props.tip, placement = props.tipPlacement)(children)
            } else {
              children
            }
          )
        ),
        TagMod.unless(props.disabled) {
          Modal(
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
    .builder[OpenModalButton](componentName)
    .initialStateFromProps { props =>
      State(
        isOpen = props.isOpen,
        over = false
      )
    }
    .renderBackendWithChildren[Backend]
    .build
}
