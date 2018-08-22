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

  type RefIO = Ref[HTMLElement, HTMLElement]
  final case class TargetProps(ref: RefIO, toggle: Callback, update: Callback, isOpened: Boolean)
  final case class ContentProps(ref: RefIO, toggle: Callback, update: Callback, styles: TagMod)

  private val contentStyles = TagMod(
    Style.width.maxContent, // make Popper calculation more stable
    Style.position.fixed.opacity.pc0.coordinate.top0 // avoid showing while Popper is calculating
  )

  private class Backend(scope: BackendScope[Props, _]) {

    private val targetRef = Ref[HTMLElement]
    private val contentRef = Ref[HTMLElement]
    private var popperOpt: Option[Popper] = None // scalastyle:ignore var.field

    // We need this because PopperJS only set one axis of the arrow.
    // - In its examples CSS was used to set the other axis. CSS is not
    //   suitable for us. Therefore here we use inline styles to set the other
    //   axis.
    // - Also note that this must be called in "onCreated", because the arrow
    //   modifier remove inline style of other axis.
    // - Ref: https://github.com/FezVrasta/popper.js/blob/master/packages/popper/src/modifiers/arrow.js#L85
    private def setArrowStyle(props: Props)(data: Data): Unit = {
      data.arrowElement.toOption.foreach { arrowElement =>
        val style = arrowElement.style
        val value = "-4px"
        props.position match {
          case PositionTopLeft | PositionTop | PositionTopRight          => style.bottom = value
          case PositionRightTop | PositionRight | PositionRightBottom    => style.left = value
          case PositionBottomLeft | PositionBottom | PositionBottomRight => style.top = value
          case PositionLeftTop | PositionLeft | PositionLeftBottom       => style.right = value
        }
      }
    }

    private def popperUpdate = {
      for {
        _ <- this.popperDestroy
        target <- targetRef.get
        content <- contentRef.get
        props <- scope.props
        _ <- Callback {
          val placement = Position.getPopperPlacement(props.position)
          val onCreate = setArrowStyle(props) _
          val offset = OffsetModifier(OffsetSimple(props.offsetHor, props.offsetVer))
          val modifiers = Modifiers(offset)
          val options = PopperOptions(placement, onCreate, modifiers)
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
          props.renderTarget(TargetProps(targetRef, toggle, popperUpdate, isOpened))
        },
        renderContent = toggle => {
          props.renderContent(ContentProps(contentRef, toggle, popperUpdate, contentStyles))
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
