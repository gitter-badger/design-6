// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.modal

import anduin.component.portal.{Portal, PortalUtils}
import anduin.component.util.ComponentUtils
import anduin.style.Style
import org.scalajs.dom

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

// Modal's anatomy (based on render result):
// - Overlay (aka Backdrop, the full-screen translucent background)
//   - Container (Portal's content, aka the "Modal")
//     - Header (built-in ModalHeader)
//     - Content (consumer-provided content via props.renderContent)
//       - Body (usually ModalBody)
//       - Footer (usually ModalFooter)
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
  width: Modal.Width = Modal.Width480,
  height: Modal.Height = Modal.Height.Content,
  isPermanent: Boolean = false,
  layout: Modal.LayoutMods = Modal.LayoutMods()
) {
  def apply(): VdomElement = Modal.component(this)
}

object Modal {

  private type Props = Modal

  case class LayoutMods(overlay: TagMod = EmptyVdom, container: TagMod = EmptyVdom)

  sealed trait Width {
    def width: TagMod
    final def container: TagMod = TagMod(width, Style.overflow.hiddenX.margin.horAuto)
  }
  case object Width480 extends Width { val width: TagMod = ^.width := "480px" }
  case object Width600 extends Width { val width: TagMod = ^.width := "600px" }
  case object Width720 extends Width { val width: TagMod = ^.width := "720px" }
  case object Width960 extends Width { val width: TagMod = ^.width := "960px" }
  case object Width1160 extends Width { val width: TagMod = ^.width := "1160px" }
  case object WidthFull extends Width { val width: TagMod = Style.width.pc100 }

  sealed trait Height { def container: TagMod }
  object Height {
    object Content extends Height { val container: TagMod = Style.margin.ver32 }
    // it's intentional to use overflow.hiddenY instead of autoY here since
    // consumers should only use Full to be able to get parent's height
    object Full extends Height { val container: TagMod = Style.height.pc100.overflow.hiddenY }
  }

  // Internal rendering

  private val overlayStaticMod = TagMod(
    Style.position.fixed.coordinate.fill.overflow.autoY,
    ^.backgroundColor := "rgba(48, 64, 77, 0.9)",
    // Backward compatible
    // https://github.com/anduintransaction/stargazer/issues/17011
    Style.zIndex.idx999
  )

  private val containerStaticMod = TagMod(
    Style.backgroundColor.gray1.borderRadius.px2,
    ^.tabIndex := 0 // Allow (keyboard) focus so Esc can work
  )

  // Should see Modal's anatomy at top of file
  private def renderModal(props: Props)(close: Callback): VdomElement = {
    // This double divs is to keep the render consistent with the unmount's
    // detach render (which use renderIntoDOM instead of ReactPortal)
    <.div(
      // Anatomy: Overlay
      <.div(
        ComponentUtils.testId(this, "Overlay"),
        overlayStaticMod,
        PortalUtils.getClosableMods(props.isClosable, close),
        props.layout.overlay,
        // Anatomy: Container
        <.div(
          ComponentUtils.testId(this, "Container"),
          containerStaticMod,
          props.width.container,
          props.height.container,
          props.layout.container,
          // Anatomy: Header
          TagMod.when(props.title.nonEmpty) {
            <.div(
              ComponentUtils.testId(this, "Header"),
              ModalHeader(props.title, props.isClosable.isDefined, close)()
            )
          },
          // Anatomy: Content
          props.renderContent(close)
        )
      )
    )
  }

  // Main render

  private val bodyStyles = dom.document.body.style
  private val setBodyHidden = Callback(bodyStyles.overflow = "hidden")
  private val setBodyAuto = Callback(bodyStyles.overflow = "auto")

  private def render(props: Props): VdomElement = {
    Portal(
      renderTarget = (toggle, _) => props.renderTarget(toggle),
      renderContent = renderModal(props),
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
