// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import scala.scalajs.js

import org.scalajs.dom.raw.Element

import anduin.scalajs.popper.{Popper, PopperOptions}
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Tooltip(
  position: Position = PositionTop,
  trigger: (VdomNode, Callback, Callback, Status) => TagMod = (t, open, close, _) =>
    TagMod(
      ^.onMouseEnter --> open,
      ^.onMouseLeave --> close,
      t
  ),
  renderTarget: VdomNode,
  targetTag: HtmlTag = <.div,
  renderContent: () => VdomNode,
  isDisabled: Boolean = false
) {
  def apply(): VdomElement = { Tooltip.component(this) }
}

object Tooltip {

  private val ComponentName = this.getClass.getSimpleName

  private val tipSize: Double = 12
  private val tipSizePx = s"${tipSize}px"

  // top, right, bottom, left
  // scalastyle:off cyclomatic.complexity
  private def getTipMod(position: Position): TagMod = {
    val auto = "auto"
    val nega = s"-${tipSize / 2}px"
    val posi = tipSizePx
    val zero = "0px"
    val (top, right, bottom, left) = position match {
      case PositionTopLeft                                        => (auto, auto, nega, posi)
      case PositionTop                                            => (auto, zero, nega, zero)
      case PositionTopRight                                       => (auto, posi, nega, auto)
      case PositionRightTop | PositionRight | PositionRightBottom => (zero, auto, zero, nega)
      case PositionBottomLeft                                     => (nega, auto, auto, posi)
      case PositionBottom                                         => (nega, zero, auto, zero)
      case PositionBottomRight                                    => (nega, posi, auto, auto)
      case PositionLeftTop | PositionLeft | PositionLeftBottom    => (zero, nega, zero, auto)
    }
    TagMod(^.top := top, ^.right := right, ^.bottom := bottom, ^.left := left)
  }
  // scalastyle:on cyclomatic.complexity

  private class Backend(scope: BackendScope[Tooltip, _]) {

    private val targetRef = Ref[Element]
    private val contentRef = Ref[Element]

    private var popper: Option[Popper] = None // scalastyle:ignore var.field

    private def onOpenPortal = {
      for {
        props <- scope.props
        target <- targetRef.get
        content <- contentRef.get
        _ <- Callback {
          val offset = props.position match {
            case PositionTopLeft | PositionBottomLeft | PositionRightTop | PositionLeftTop         => s"-50%, $tipSizePx"
            case PositionTop | PositionBottom | PositionRight | PositionLeft                       => s"0, $tipSizePx"
            case PositionTopRight | PositionBottomRight | PositionRightBottom | PositionLeftBottom => s"50%, $tipSizePx"
          }
          val options = new PopperOptions(
            placement = props.position.placement,
            modifiers = js.Dynamic.literal(
              offset = js.Dynamic.literal(enabled = true, offset = offset)
            )
          )
          popper = Some(new Popper(target, content, options))
        }
      } yield ()
    }

    private def close: Callback = {
      // Destroy the Popper instance after closing the tooltip
      Callback.traverseOption(popper) { p =>
        Callback {
          p.destroy()
        }
      }
    }

    def render(props: Tooltip): VdomNode = {
      if (props.isDisabled) {
        // it is intentional to render targetTag wrapper here to keep the
        // HTML result consistent with the other case
        props.targetTag(props.renderTarget)
      } else {
        PortalWrapper(
          onOpen = onOpenPortal,
          onClose = close,
          isPortalClicked = (clickedTarget, tooltipEle) => {
            CallbackTo(tooltipEle.contains(clickedTarget)) ||
            targetRef.get.map(_.contains(clickedTarget)).getOrElse(false)
          },
          renderTarget = (open, close, status) => {
            props.targetTag.withRef(targetRef)(props.trigger(props.renderTarget, open, close, status))
          },
          renderContent = (_, _) => {
            <.div.withRef(contentRef)(
              // - The next 2 lines are to avoid laggy Tooltip because in the
              //   first render Popper does not have the correct position yet
              //   but we still need to render the tooltip element anyway to
              //   have a correct width so Popper can calculate the position
              // - Popper will override these styles with correct position
              ^.visibility := "hidden",
              Style.position.absolute.coordinate.top0.coordinate.left0,
              Style.zIndex.idx9999.backgroundColor.gray9.color.white.shadow.blur8,
              Style.padding.ver4.padding.hor8.borderRadius.px4,
              // tip
              <.div(
                Style.position.absolute.zIndex.idx0.backgroundColor.gray9.margin.allAuto,
                TagMod(^.transform := "rotate(45deg)", ^.width := tipSizePx, ^.height := tipSizePx),
                getTipMod(props.position)
              ),
              // content
              // - relative position to ensure content is always over tip
              <.div(Style.position.relative.zIndex.idx1, props.renderContent())
            )
          }
        )()
      }
    }
  }

  private val component = ScalaComponent
    .builder[Tooltip](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}
