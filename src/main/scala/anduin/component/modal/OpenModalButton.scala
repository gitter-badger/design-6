// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.modal

import anduin.component.util.JavaScriptUtils

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
  modalTitle: String = "",
  modalClasses: String = "",
  modalBody: Callback => VdomElement = _ => <.span(),
  overlayCloseable: Boolean = true,
  closeOnEscape: Boolean = true,
  isOpen: Boolean = false,
  onBeforeShowing: Callback = Callback.empty,
  onAfterHiding: Callback = Callback.empty,
  showCloseBtn: Boolean = true,
  // Add key when we want to distinguish between different button
  keyOpt: Option[String] = None
) {
  def apply(children: VdomNode*): ScalaComponent.Unmounted[_, _, _] = {
    val ctorType = keyOpt.map(OpenModalButton.component.withKey(_)).getOrElse(OpenModalButton.component.ctor)
    ctorType(this)(children: _*)
  }
}
// scalastyle:on parameter.number

object OpenModalButton {

  private val componentName = this.getClass.getSimpleName

  private case class State(isOpen: Boolean, over: Boolean)

  private case class Backend(scope: BackendScope[OpenModalButton, State]) {

    private def show = {
      for {
        props <- scope.props
        _ <- props.onBeforeShowing
        _ <- scope.modState(_.copy(isOpen = true))
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
        <.a(
          ^.href := JavaScriptUtils.VoidMethod,
          ^.classSet(
            s"dib ${props.buttonClasses}" -> true,
            "disabled" -> props.disabled
          ),
          ^.onClick --> Callback.when(!props.disabled)(show),
          TagMod.when(props.tip.nonEmpty) {
            TagMod(
              TagMod.when(state.over)(VdomAttr("data-tip") := props.tip),
              ^.onMouseEnter --> Callback.when(!state.over)(scope.modState(_.copy(over = true))),
              ^.onMouseLeave --> Callback.when(state.over)(scope.modState(_.copy(over = false)))
            )
          },
          props.buttonLabel,
          children
        ),

        TagMod.unless(props.disabled) {
          Modal(
            title = props.modalTitle,
            isOpen = state.isOpen,
            overlayClassName = props.modalClasses,
            shouldCloseOnOverlayClick = props.overlayCloseable,
            shouldCloseOnEscape = props.closeOnEscape,
            onRequestClose = hide,
            showCloseBtn = props.showCloseBtn
          )(props.modalBody)
        }
      )
    }
  }

  private val component = ScalaComponent.builder[OpenModalButton](componentName)
    .initialStateFromProps { props =>
      State(
        isOpen = props.isOpen,
        over = false
      )
    }
    .renderBackendWithChildren[Backend]
    .build
}
