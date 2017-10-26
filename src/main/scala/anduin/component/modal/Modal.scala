// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.modal

import scala.scalajs.js

import japgolly.scalajs.react.vdom.VdomElement

import anduin.component.icon.Icon
import anduin.component.util.ComponentUtils
import anduin.scalajs.react.modal.ReactModal

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object Modal {

  val ComponentName: String = ComponentUtils.name(this)

  final case class Props(
    title: String,
    body: Callback => VdomElement,
    isOpen: Boolean,
    overlayClassName: String,
    shouldCloseOnOverlayClick: Boolean,
    shouldCloseOnEscape: Boolean,
    onAfterOpen: Callback,
    onRequestClose: Callback
  )

  private val component = ScalaComponent
    .builder[Props](ComponentName)
    .stateless
    .render_P { props =>
      ReactModal(
        isOpen = props.isOpen,
        className = "modal",
        overlayClassName = s"modal-wrapper modal-overlay open ${props.overlayClassName}",
        onAfterOpen = props.onAfterOpen,
        onRequestClose = js.defined { _ => props.onRequestClose },
        shouldCloseOnOverlayClick = props.shouldCloseOnOverlayClick,
        contentLabel = props.title,
        shouldCloseOnEsc = props.shouldCloseOnEscape
      )(
        <.div(
          ^.cls := "modal-header",
          <.h3(^.cls := "title fw-600", props.title),
          <.button(
            ^.cls := "btn -plain -close border-radius-round tc",
            ^.title := "Close this dialog",
            ^.onClick --> props.onRequestClose,
            Icon.cross()
          )
        ),
        props.body(props.onRequestClose)
      )
    }
    .build

  def apply(
    title: String,
    isOpen: Boolean = true,
    overlayClassName: String = "",
    shouldCloseOnOverlayClick: Boolean = true,
    shouldCloseOnEscape: Boolean = true,
    onAfterOpen: Callback = Callback.empty,
    onRequestClose: Callback = Callback.empty
  )(body: Callback => VdomElement): ScalaComponent.Unmounted[Props, Unit, Unit] = {
    component(Props(
      title,
      body,
      isOpen,
      overlayClassName,
      shouldCloseOnOverlayClick,
      shouldCloseOnEscape,
      onAfterOpen,
      onRequestClose
    ))
  }
}
