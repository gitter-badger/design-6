// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Popover(
  // Portal common props (see Portal for detail)
  renderTarget: (Callback, Callback, Boolean) => VdomNode, // (toggle, update position, isOpened)
  renderContent: (Callback, Callback) => VdomNode, // (close, update position)
  // Portal utils common props
  isClosable: Option[PortalUtils.isClosable] = PortalUtils.defaultIsClosable,
  // PortalPopper common props (see PortalPopper for detail)
  position: Position = PositionBottom,
  verticalOffset: Double = 0,
  horizontalOffset: Double = 0,
  targetTag: HtmlTag = <.div
) {
  def apply(): VdomElement = Popover.component(this)
}

object Popover {

  private type Props = Popover

  // ===

  private val contentStyles = TagMod(
    Style.backgroundColor.white.borderRadius.px2.shadow.blur8,
    Style.border.all.borderColor.gray4.borderWidth.px1
  )

  // Because we use body's scroll so we must use absolute position here so
  // the overlay matches the size and position of body tag, in order for Popper
  // to work
  private val overlayStyles = TagMod(
    Style.position.absolute.width.pc100.height.pc100.coordinate.fill,
    // Backward compatible
    // https://github.com/anduintransaction/stargazer/issues/17011
    Style.zIndex.idx999
  )

  private def renderContent(props: Props)(popper: PortalPopper.ContentProps) = {
    val isOverlayClosable = props.isClosable.exists(_.onOutsideClick)

    // This is the actual popover node (i.e. the dom node that Popper
    // will position)
    val content = <.div.withRef(popper.ref)(
      // Reset overlay's pointerEvents
      TagMod.when(!isOverlayClosable) { Style.pointerEvents.all },
      TagMod(popper.styles, contentStyles),
      props.renderContent(popper.toggle, popper.update)
    )

    // This is the overlay of Popover. This will catch closable events
    // like onEsc or onOutsideClick
    <.div(
      overlayStyles,
      PortalUtils.getClosableMods(props.isClosable, popper.toggle),
      // Allow click through if not closable via outsideClick
      TagMod.when(!isOverlayClosable) { Style.pointerEvents.none },
      content
    )
  }

  private def renderTarget(props: Props)(popper: PortalPopper.TargetProps) = {
    val body = props.renderTarget(popper.toggle, popper.update, popper.isOpened)
    props.targetTag.withRef(popper.ref)(body)
  }

  private def render(props: Props): VdomElement = {
    PortalPopper(
      renderTarget = renderTarget(props),
      renderContent = renderContent(props),
      // ===
      position = props.position,
      offsetVer = props.verticalOffset,
      offsetHor = props.horizontalOffset
    )()
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
