// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.modal

import scala.scalajs.js
import japgolly.scalajs.react.vdom.VdomElement
import anduin.component.icon.Icon
import anduin.component.util.ComponentUtils
import anduin.scalajs.react.modal.ReactModal
import org.scalajs.dom.raw.Element

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object Modal {

  def setAppElement(appElement: Element): Unit = {
    ReactModal.RawComponent.setAppElement(appElement)
  }

  val ComponentName: String = ComponentUtils.name(this)

  type ModalHeaderRenderer = (
    String, // The title
    Callback // The callback of closing modal
  ) => VdomElement

  final case class Props(
    title: String,
    body: Callback => VdomElement,
    isOpen: Boolean,
    overlayStyles: Map[String, String],
    contentStyles: Map[String, String],
    overlayClassName: String,
    modalClassName: String,
    shouldCloseOnOverlayClick: Boolean,
    shouldCloseOnEscape: Boolean,
    onAfterOpen: Callback,
    onRequestClose: Callback,
    showCloseBtn: Boolean,
    renderHeader: Option[ModalHeaderRenderer] = None
  )

  private val component = ScalaComponent
    .builder[Props](ComponentName)
    .stateless
    .render_P { props =>
      val style = ReactModal.style(
        overlay = props.overlayStyles,
        content = props.contentStyles
      )

      ReactModal(
        isOpen = props.isOpen,
        style = js.defined(style),
        className = s"modal ${props.modalClassName}",
        overlayClassName = s"modal-wrapper modal-overlay open ${props.overlayClassName}",
        onAfterOpen = props.onAfterOpen,
        onRequestClose = js.defined { _ =>
          props.onRequestClose
        },
        shouldCloseOnOverlayClick = props.shouldCloseOnOverlayClick,
        contentLabel = props.title,
        shouldCloseOnEsc = props.shouldCloseOnEscape
      )(
        props.renderHeader.fold[VdomElement](
          <.div(
            ^.cls := "modal-header",
            <.h3(^.cls := "title fw-600", props.title),
            TagMod.when(props.showCloseBtn) {
              <.button(
                ^.cls := "btn -plain -close border-radius-round tc",
                ^.title := "Close this dialog",
                ^.onClick --> props.onRequestClose,
                Icon.cross()
              )
            }
          )
        )(_(props.title, props.onRequestClose)),
        props.body(props.onRequestClose)
      )
    }
    .build

  // scalastyle:off parameter.number
  def apply(
    title: String,
    isOpen: Boolean = true,
    overlayStyles: Map[String, String] = Map.empty,
    contentStyles: Map[String, String] = Map.empty,
    overlayClassName: String = "",
    modalClassName: String = "",
    shouldCloseOnOverlayClick: Boolean = true,
    shouldCloseOnEscape: Boolean = true,
    onAfterOpen: Callback = Callback.empty,
    onRequestClose: Callback = Callback.empty,
    showCloseBtn: Boolean = true,
    renderHeader: Option[ModalHeaderRenderer] = None
  )(body: Callback => VdomElement): ScalaComponent.Unmounted[Props, Unit, Unit] = {
    component(
      Props(
        title,
        body,
        isOpen,
        overlayStyles,
        contentStyles,
        overlayClassName,
        modalClassName,
        shouldCloseOnOverlayClick,
        shouldCloseOnEscape,
        onAfterOpen,
        onRequestClose,
        showCloseBtn,
        renderHeader
      )
    )
  }
  // scalastyle:on parameter.number
}
