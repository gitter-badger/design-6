// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import org.scalajs.dom
import org.scalajs.dom.raw.{Element, HTMLElement}

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Modal(
  title: String = "",
  size: Modal.Size = Modal.SizeSmall,
  isOpen: Boolean = false,
  isClosable: Option[PortalUtils.isClosable] = PortalUtils.defaultIsClosable,
  // (open callback) => target Vdom
  renderTarget: Callback => VdomNode,
  // (close callback) => content Vdom
  renderContent: Callback => VdomNode,
  unsafeOverlayMod: TagMod = TagMod.empty,
  onOpen: Callback = Callback.empty,
  onClose: Callback = Callback.empty
) {
  def apply(): VdomElement = Modal.component(this)
}

object Modal {

  private val ComponentName = this.getClass.getSimpleName

  sealed trait Size { val style: TagMod }
  case object SizeSmall extends Size { val style: TagMod = ^.width := "480px" }
  case object Size600 extends Size { val style: TagMod = ^.width := "600px" }
  case object Size720 extends Size { val style: TagMod = ^.width := "720px" }
  case object SizeMedium extends Size { val style: TagMod = ^.width := "960px" }
  case object SizeLarge extends Size { val style: TagMod = ^.width := "1160px" }
  case object SizeFull extends Size { val style: TagMod = Style.width.pc100.height.pc100 }

  private class Backend() {

    private val modalRef = Ref[HTMLElement]

    private val overflowHidden = Style.overflow.hidden.value

    private def isPortalClicked(clickedTarget: Element) = {
      val t = for {
        modal <- modalRef.get
      } yield !clickedTarget.contains(modal)
      t.asCallback.map(_.getOrElse(false))
    }

    def render(props: Modal): VdomElement = {
      PortalWrapper(
        isOpen = props.isOpen,
        closeOnEsc = props.isClosable.exists(_.onEsc),
        closeOnOutsideClick = props.isClosable.exists(_.onOutsideClick),
        isPortalClicked = (clickedTarget, _) => isPortalClicked(clickedTarget),
        onOpen = props.onOpen,
        onClose = props.onClose,
        renderTarget = (open, _, _) => {
          // Disable the scrolling on body when the modal is opened
          val onOpen = Callback(dom.document.body.classList.add(overflowHidden)) >> open
          props.renderTarget(onOpen)
        },
        renderContent = (close, _) => {
          val onClose = Callback(dom.document.body.classList.remove(overflowHidden)) >> close
          <.div(
            Style.position.fixed.coordinate.fill.zIndex.idx999,
            props.unsafeOverlayMod,
            ^.background := "rgba(48, 64, 77, 0.9)",
            TagMod.when(props.size != SizeFull) { Style.overflow.autoY.padding.ver32 },
            <.div.withRef(modalRef)(
              Style.backgroundColor.gray1.borderRadius.px2.overflow.hidden.margin.horAuto,
              props.size.style,
              TagMod.when(props.title.nonEmpty) {
                ModalHeader(props.title, props.isClosable.isDefined, onClose)()
              },
              props.renderContent(onClose)
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
