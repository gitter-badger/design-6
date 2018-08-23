// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import org.scalajs.dom.raw.HTMLElement

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.scalajs.popper._
// scalastyle:on underscore.import

private[portal] final case class PortalPopper(
  // Portal common props (see Portal for detail)
  renderTarget: PortalPopper.TargetProps => VdomNode,
  renderContent: PortalPopper.ContentProps => VdomNode,
  // Popper specific props
  position: Position,
  offsetVer: Double,
  offsetHor: Double
) {
  def apply(): VdomElement = PortalPopper.component(this)
}

private[portal] object PortalPopper {

  private type Props = PortalPopper

  private type RefIO = Ref[HTMLElement, HTMLElement]
  final case class TargetProps(ref: RefIO, toggle: Callback, update: Callback, isOpened: Boolean)
  final case class ContentProps(ref: RefIO, toggle: Callback, update: Callback, styles: TagMod, arrowMod: TagMod)

  // This is the style that consumer of PortalPopper should add to the
  // "content" element.
  // - It cannot be added by PortalPopper because the consumer might have
  //   in-between layer, like Popover has a "overlay" element.
  // - In other words, only the consumer know exactly which is the actual
  //   "content" element
  private val contentStyles = TagMod(
    Style.width.maxContent, // make Popper calculation more stable
    Style.position.fixed.opacity.pc0.coordinate.top0 // avoid showing while Popper is calculating
  )

  // The appearance of the arrow can be customized by the consumer, but most
  // rendering logic is handled by Popper, so it should be defined here in
  // PortalPopper.
  // - Also note that the arrow cannot be inserted by PortalPopper because it
  //   must be inserted inside the actual "content" element, which might be
  //   deep inside "renderContent" (e.g. Popover has an in-between "overlay"
  //   element)
  // - Therefore the arrow will be sent via ContentProps and let the consumer
  //   to insert it correctly
  val arrowSize = 8
  private val arrowSizePx = s"${arrowSize}px"
  private val arrowMod = TagMod(
    VdomAttr("x-arrow") := "", // Popper's default arrow selector
    Style.position.absolute,
    TagMod(^.width := arrowSizePx, ^.height := arrowSizePx),
    ^.transform := "rotate(45deg)"
  )

  // We need this because PopperJS only set one axis of the arrow.
  // - In its examples, CSS was used to set the other axis. CSS is not suitable
  //   for us, so here we use inline styles to set it.
  // - Also note that this must be called in "onCreated", because the arrow
  //   modifier remove inline style of other axis.
  // - Ref: https://github.com/FezVrasta/popper.js/blob/master/packages/popper/src/modifiers/arrow.js#L85
  private def setArrowStyle(props: Props)(data: Data): Unit = {
    data.arrowElement.toOption.foreach { arrowElement =>
      val style = arrowElement.style
      val value = s"-${arrowSize / 2}px"
      props.position match {
        case _: PositionTopBase    => style.bottom = value
        case _: PositionRightBase  => style.left = value
        case _: PositionBottomBase => style.top = value
        case _: PositionLeftBase   => style.right = value
      }
    }
  }

  // Helper to let consumer offset the content when it uses arrow.
  // - PortalPopper cannot know whether a consumer choose to use arrow or not
  //   (it's in renderContent) so this is a public method to let the consumer
  //   add the offset when needed
  def getArrowOffset(position: Position): (Double, Double) = {
    position match {
      case _: PositionTopBase | _: PositionBottomBase => (0, arrowSize)
      case _: PositionRightBase | _: PositionLeftBase => (arrowSize, 0)
    }
  }

  private class Backend(scope: BackendScope[Props, _]) {

    private val targetRef = Ref[HTMLElement]
    private val contentRef = Ref[HTMLElement]
    private var popperOpt: Option[Popper] = None // scalastyle:ignore var.field

    private def popperUpdate = {
      for {
        _ <- this.popperDestroy
        target <- targetRef.get
        content <- contentRef.get
        props <- scope.props
        _ <- Callback {
          val options = PopperOptions(
            placement = Position.getPopperPlacement(props.position),
            onCreate = setArrowStyle(props),
            modifiers = Modifiers(
              offset = OffsetModifier(props.offsetHor, props.offsetVer)
            )
          )
          val popper = new Popper(target, content, options)
          this.popperOpt = Some(popper)
        }
        // Show content after calculation (content should set 0 opacity itself)
        _ <- Callback { content.style.opacity = "1" }
      } yield ()
    }

    private def popperDestroy = {
      Callback.traverseOption(popperOpt)(popper => Callback { popper.destroy() })
    }

    // ===

    def render(props: Props): VdomElement = {
      Portal(
        renderTarget = (toggle, isOpened) => {
          val targetProps = TargetProps(targetRef, toggle, popperUpdate, isOpened)
          props.renderTarget(targetProps)
        },
        renderContent = toggle => {
          val contentProps = ContentProps(contentRef, toggle, popperUpdate, contentStyles, arrowMod)
          props.renderContent(contentProps)
        },
        defaultIsOpened = false,
        isOpened = None,
        onOpen = popperUpdate,
        onClose = popperDestroy
      )()
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .build
}
