// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal.modal

import anduin.component.portal.{Portal, PortalUtils}
import anduin.style.Style
import org.scalajs.dom

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Modal(
  // Portal common props (see Portal for detail)
  renderTarget: Callback => VdomNode = _ => EmptyVdom, // (Toggle) - might not necessary if isOpened is defined
  renderContent: Callback => VdomNode, // (Toggle)
  defaultIsOpened: Boolean = false,
  isOpened: Option[Boolean] = None,
  afterUserOpen: Callback = Callback.empty,
  afterUserClose: Callback = Callback.empty,
  afterOpen: Callback = Callback.empty,
  beforeClose: Callback = Callback.empty,
  // Portal utils common props
  isClosable: Option[PortalUtils.IsClosable] = PortalUtils.defaultIsClosable,
  // Modal specific props
  title: String = "",
  size: Modal.Size = Modal.Size480,
  isPermanent: Boolean = false,
  layout: Modal.LayoutMods = Modal.LayoutMods()
) {
  def apply(): VdomElement = Modal.component(this)
}

object Modal {

  private type Props = Modal

  case class LayoutMods(
    overlay: TagMod = EmptyVdom,
    content: TagMod = EmptyVdom
  )

  // Public options
  private val defaultOverlayPadding = Style.margin.ver32

  sealed trait Size { val style: TagMod }
  case object Size480 extends Size { val style: TagMod = TagMod(^.width := "480px", defaultOverlayPadding) }
  case object Size600 extends Size { val style: TagMod = TagMod(^.width := "600px", defaultOverlayPadding) }
  case object Size720 extends Size { val style: TagMod = TagMod(^.width := "720px", defaultOverlayPadding) }
  case object Size960 extends Size { val style: TagMod = TagMod(^.width := "960px", defaultOverlayPadding) }
  case object Size1160 extends Size { val style: TagMod = TagMod(^.width := "1160px", defaultOverlayPadding) }
  case object SizeFull extends Size { val style: TagMod = Style.width.pc100.height.pc100 }
  case class SizeDynamic(style: TagMod) extends Size

  // Internal rendering

  private val overlayStyles = TagMod(
    Style.position.fixed.coordinate.fill.overflow.autoY,
    ^.backgroundColor := "rgba(48, 64, 77, 0.9)",
    // Backward compatible
    // https://github.com/anduintransaction/stargazer/issues/17011
    Style.zIndex.idx999
  )

  private val contentStyles = TagMod(
    Style.backgroundColor.gray1.borderRadius.px2,
    Style.overflow.hidden.margin.horAuto
  )

  private def renderContent(props: Props)(close: Callback): VdomElement = {
    val content = <.div(
      overlayStyles,
      props.layout.overlay,
      PortalUtils.getClosableMods(props.isClosable, close),
      <.div(
        contentStyles,
        props.layout.content,
        props.size.style,
        ^.tabIndex := 0, // Allow (keyboard) focus so Esc can work
        // Content
        TagMod.when(props.title.nonEmpty) {
          ModalHeader(props.title, props.isClosable.isDefined, close)()
        },
        props.renderContent(close)
      )
    )
    // This external div is to keep the render consistent with the unmount's
    // detach render (which use renderIntoDOM instead of ReactPortal)
    <.div(content)
  }

  // Main render

  private val bodyStyles = dom.document.body.style
  private val setBodyHidden = Callback(bodyStyles.overflow = "hidden")
  private val setBodyAuto = Callback(bodyStyles.overflow = "auto")

  private def render(props: Props): VdomElement = {
    Portal(
      renderTarget = (toggle, _) => props.renderTarget(toggle),
      renderContent = renderContent(props),
      // ===
      defaultIsOpened = props.defaultIsOpened,
      isOpened = props.isOpened,
      isPermanent = props.isPermanent,
      // ===
      afterUserClose = props.afterUserClose,
      afterUserOpen = props.afterUserOpen,
      afterOpen = setBodyHidden >> props.afterOpen,
      beforeClose = setBodyAuto >> props.beforeClose
    )()
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
