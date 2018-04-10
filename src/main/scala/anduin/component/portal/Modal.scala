// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import anduin.component.icon.IconAcl

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Modal(
  title: String,
  size: Modal.Size = Modal.SizeDefault,
  closeOnEsc: Boolean = true,
  closeOnOutsideClick: Boolean = true,
  renderTarget: (Callback, PortalWithState.Status) => VdomElement,
  renderHeader: Option[Modal.HeaderRenderer] = None,
  renderContent: Callback => VdomElement,
  renderFooter: Option[(Callback => VdomNode)] = None
) {
  def apply(): VdomElement = {
    Modal.component(this)
  }
}

object Modal {

  type HeaderRenderer = (
    String, // The title
    Callback // The callback of closing modal
  ) => VdomNode

  private val ComponentName = this.getClass.getSimpleName

  sealed trait Size {
    def className: String
  }
  case object SizeDefault extends Size { val className = "" }
  case object SizeMedium extends Size { val className = "-medium" }
  case object SizeLarge extends Size { val className = "-large" }

  private case class Backend(scope: BackendScope[Modal, _]) {

    def render(props: Modal): VdomElement = {
      PortalWithState(
        closeOnEsc = props.closeOnEsc,
        closeOnOutsideClick = props.closeOnOutsideClick,
        renderChildren = renderer => {
          <.div(
            props.renderTarget(renderer.openPortal, renderer.status),
            renderer.portal(
              <.div(
                ^.classSet(
                  "modal-wrapper modal-overlay" -> true,
                  props.size.className -> true,
                  "-open" -> (renderer.status == PortalWithState.StatusOpen),
                  "-hide" -> (renderer.status == PortalWithState.StatusHide)
                ),
                <.div(
                  ^.cls := "modal",
                  props.renderHeader.fold[VdomNode] {
                    <.div(
                      ^.cls := "modal-header",
                      <.h3(^.cls := "title", props.title),
                      <.div(
                        ^.cls := "close",
                        ^.onClick --> renderer.closePortal,
                        IconAcl(name = IconAcl.NameCross)()
                      )
                    )
                  }(_(props.title, renderer.closePortal)),
                  <.div(^.cls := "modal-body", props.renderContent(renderer.closePortal)),
                  props.renderFooter.fold(EmptyVdom) { footer =>
                    <.div(^.cls := "modal-footer", footer(renderer.closePortal))
                  }
                )
              )
            )
          )
        }
      )()
    }
  }

  private val component = ScalaComponent
    .builder[Modal](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}
