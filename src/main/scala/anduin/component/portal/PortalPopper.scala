// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.portal

import scala.scalajs.js

import org.scalajs.dom.raw.{HTMLElement, MutationObserver, MutationObserverInit, MutationRecord}

import anduin.scalajs.popper.{Popper, PopperModifiers}
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[component] final case class PortalPopper(
  // Portal common props (see Portal for detail)
  renderTarget: PortalPopper.TargetProps => VdomNode,
  renderContent: PortalPopper.ContentProps => VdomNode,
  isOpened: Option[Boolean],
  // Popper specific props
  position: Position,
  offsetVer: Double,
  offsetHor: Double
) {
  def apply(): VdomElement = PortalPopper.component(this)
}

private[component] object PortalPopper {

  private type Props = PortalPopper
  private type RefIO = Ref.Simple[HTMLElement]

  // RenderProps
  private[component] final case class TargetProps(ref: RefIO, toggle: Callback, isOpened: Boolean)
  private[component] final case class ContentProps(ref: RefIO, toggle: Callback, styles: TagMod, arrowMod: TagMod)

  // These are styles that consumer of PortalPopper should add to the
  // "content" element.
  // - It cannot be added by PortalPopper because the consumer might have
  //   in-between layer, like Popover has a "overlay" element.
  // - In other words, only the consumer know exactly which is the actual
  //   "content" element
  private val contentStyles = TagMod(
    // Avoid showing while Popper is calculating
    Style.position.fixed.opacity.pc0.position.pinTop
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
  val arrowSize: Double = 8
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
  private def setArrowStyle(props: Props)(data: Popper.Data): Unit = {
    data.arrowElement.toOption.foreach { arrowElement =>
      val style = arrowElement.style
      val value = s"-${arrowSize / 2}px"
      props.position match {
        case _: PositionTop    => style.bottom = value
        case _: PositionRight  => style.left = value
        case _: PositionBottom => style.top = value
        case _: PositionLeft   => style.right = value
      }
    }
  }

  // Helper to let consumer offset the content when it uses arrow.
  // - PortalPopper cannot know whether a consumer choose to use arrow or not
  //   (it's in renderContent) so this is a public method to let the consumer
  //   add the offset when needed
  def getArrowOffset(position: Position): (Double, Double) = {
    position match {
      case _: PositionTop | _: PositionBottom => (0, arrowSize)
      case _: PositionRight | _: PositionLeft => (arrowSize, 0)
    }
  }

  private val observerOptions = {
    MutationObserverInit(subtree = true, characterData = true, childList = true)
  }

  // These modifiers are currently not customizable so they're defined here
  // for performance purpose
  private object staticModifiers {
    import PopperModifiers._ // scalastyle:ignore underscore.import import.grouping
    val preventOverflow = new PreventOverflow(PreventOverflow.BoundariesViewPort)
  }

  private class Backend(scope: BackendScope[Props, _]) {

    private val targetRef = Ref[HTMLElement]
    private val contentRef = Ref[HTMLElement]
    // @TODO use ref instead of var
    private var popperOpt: Option[Popper] = None // scalastyle:ignore var.field
    private val observer = new MutationObserver(
      (_: js.Array[MutationRecord], _: MutationObserver) => {
        popperOpt.foreach(_.scheduleUpdate())
      }
    )

    private def popperInit = {
      for {
        target <- targetRef.get
        content <- contentRef.get
        props <- scope.props
        _ <- Callback {
          val options = new Popper.Options(
            placement = Position.getPopperPlacement(props.position),
            onCreate = setArrowStyle(props),
            onUpdate = setArrowStyle(props),
            modifiers = new PopperModifiers(
              offset = new PopperModifiers.Offset(props.offsetHor, props.offsetVer),
              preventOverflow = staticModifiers.preventOverflow
            )
          )
          // This applies necessary coordination to the content (via inline
          // styles)
          val popper = new Popper(target, content, options)
          this.popperOpt = Some(popper)
          // Show content after calculation (content should be hidden by
          // contentStyles in the initial render)
          content.style.opacity = "1"
          // Watch for content's change to re-calculate position
          observer.observe(content, observerOptions)
        }
      } yield ()
    }

    private def popperDestroy: Callback = {
      Callback.traverseOption(popperOpt)(p => Callback(p.destroy()))
    }

    // ===

    def render(props: Props): VdomElement = {
      Portal(
        renderTarget = (toggle, isOpened) => {
          val targetProps = TargetProps(targetRef, toggle, isOpened)
          props.renderTarget(targetProps)
        },
        renderContent = toggle => {
          val contentProps = ContentProps(contentRef, toggle, contentStyles, arrowMod)
          props.renderContent(contentProps)
        },
        // ===
        defaultIsOpened = false,
        isOpened = props.isOpened,
        isPermanent = false,
        // ===
        afterUserOpen = Callback.empty,
        afterUserClose = Callback.empty,
        afterOpen = popperInit,
        beforeClose = popperDestroy
      )()
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .build
}
