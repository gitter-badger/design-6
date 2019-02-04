// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.popover

import anduin.style.Style

// scalastyle:off underscore.import
import anduin.component.portal._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Popover(
  // Portal common props (see Portal for detail)
  renderTarget: (Callback, Boolean) => VdomNode, // (toggle, isOpened)
  renderContent: Callback => VdomNode, // (close)
  isOpened: Option[Boolean] = None,
  // Portal utils common props
  isClosable: Option[PortalUtils.IsClosable] = PortalUtils.defaultIsClosable,
  // PortalPopper common props (see PortalPopper for detail)
  position: Position = PositionBottomCenter,
  verticalOffset: Double = 0,
  horizontalOffset: Double = 0,
  targetTag: HtmlTag = <.div,
  // Other
  isFullWidth: Boolean = false
) {
  def apply(): VdomElement = Popover.component(this)
}

object Popover {

  private type Props = Popover

  // ===

  private val contentStyles = TagMod(
    Style.background.white.borderRadius.px2.shadow.blur8,
    Style.border.all.borderColor.gray4.borderWidth.px1
  )

  // Because we use body's scroll so we must use absolute position here so
  // the overlay matches the size and position of body tag, in order for Popper
  // to work
  private val overlayStyles = TagMod(
    Style.position.absolute.width.pc100.height.pc100.position.pinAll,
    // Backward compatible
    // https://github.com/anduintransaction/stargazer/issues/17011
    Style.zIndex.idx999
  )

  private def renderContent(props: Props)(popper: PortalPopper.ContentProps) = {
    val isOverlayClosable = props.isClosable.exists(_.onOutsideClick)
    val arrow = <.div(
      popper.arrowMod,
      Style.background.white.borderColor.gray4,
      TagMod(
        props.position match {
          case _: PositionTop    => Style.border.right.border.bottom
          case _: PositionRight  => Style.border.right.border.top
          case _: PositionBottom => Style.border.left.border.top
          case _: PositionLeft   => Style.border.left.border.bottom
        }
      )
    )

    // This is the actual popover node (i.e. the dom node that Popper
    // will position)
    val realContent = props.renderContent(popper.toggle)
    val content = <.div.withRef(popper.ref)(
      // Reset overlay's pointerEvents
      TagMod.when(!isOverlayClosable) { Style.pointerEvents.all },
      realContent,
      // Don't show the arrow when there's no content
      TagMod.unless(realContent == EmptyVdom) {
        TagMod(popper.styles, contentStyles, arrow)
      }
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
    val body = props.renderTarget(popper.toggle, popper.isOpened)
    // Should this be popper.styles(props.isFullWidth)?
    val styles = if (props.isFullWidth) Style.width.pc100 else Style.width.maxContent
    props.targetTag.withRef(popper.ref)(styles, body)
  }

  private def render(props: Props): VdomElement = {
    val (offsetVer, offsetHor) = PortalPopper.getArrowOffset(props.position)

    PortalPopper(
      renderTarget = renderTarget(props),
      renderContent = renderContent(props),
      isOpened = props.isOpened,
      // ===
      position = props.position,
      offsetVer = offsetVer,
      offsetHor = offsetHor
    )()
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
