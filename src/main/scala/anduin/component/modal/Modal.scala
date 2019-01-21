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
  size: Modal.Size = Modal.Size(),
  isPermanent: Boolean = false,
  layout: Modal.LayoutMods = Modal.LayoutMods()
) {
  def apply(): VdomElement = Modal.component(this)
}

object Modal {

  private type Props = Modal

  case class LayoutMods(overlay: TagMod = EmptyVdom, container: TagMod = EmptyVdom, content: TagMod = EmptyVdom)

  object Width {
    object Px480 extends ModalSize.Width.Px480
    object Px600 extends ModalSize.Width.Px600
    object Px720 extends ModalSize.Width.Px720
    object Px960 extends ModalSize.Width.Px960
    object Px1160 extends ModalSize.Width.Px1160
    object Full extends ModalSize.Width.Full
  }

  object Height {
    object Content extends ModalSize.Height.Content
    case class Custom(percent: Int) extends ModalSize.Height.Custom
    object Full extends ModalSize.Height.Custom { val percent: Int = 100 }
  }

  final case class Size(
    width: ModalSize.Width = Width.Px480,
    height: ModalSize.Height = Height.Content
  ) extends ModalSize

  // Internal rendering

  private val overlayStaticMod = TagMod(
    Style.position.fixed.coordinate.fill.overflow.autoY,
    ^.backgroundColor := "rgba(48, 64, 77, 0.9)",
    // To center the container. We should not use margin hor auto at container
    // because container's width might be unknown (i.e. Width.Content)
    Style.flexbox.flex.flexbox.justifyCenter.flexbox.itemsStart,
    // Backward compatible
    // https://github.com/anduintransaction/stargazer/issues/17011
    Style.zIndex.idx999
  )

  private val containerStaticMod = TagMod(
    Style.backgroundColor.gray1.borderRadius.px2,
    // Allow (keyboard) focus so Esc can work
    ^.tabIndex := 0
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
        props.layout.overlay,
        PortalUtils.getClosableMods(props.isClosable, close),
        // Anatomy: Container
        <.div(
          ComponentUtils.testId(this, "Container"),
          containerStaticMod,
          props.size.container,
          props.layout.container,
          // Anatomy: Header
          TagMod.when(props.title.nonEmpty) {
            <.div(
              ComponentUtils.testId(this, "Header"),
              props.size.header,
              ModalHeader(props.title, props.isClosable.isDefined, close)()
            )
          },
          // Anatomy: Content
          <.div(
            ComponentUtils.testId(this, "Content"),
            props.size.content,
            props.layout.content,
            props.renderContent(close)
          )
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
