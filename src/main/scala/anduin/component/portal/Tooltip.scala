// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import scala.scalajs.js

import org.scalajs.dom.raw.Element

import anduin.scalajs.popper.{Popper, PopperOptions}
import anduin.style.{CssVar, Style}

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
  private val arrowSize = s"${tipSize / 2}px"
  private val arrowClass = "js-tooltip-arrow"

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
              arrow = js.Dynamic.literal(element = s".$arrowClass"),
              offset = js.Dynamic.literal(enabled = true, offset = offset)
            ),
            onCreate = data => {
              data.instance.popper.domAsHtml.style.visibility = "visible"
            }
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

    // scalastyle:off multiple.string.literals
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
                Style.position.absolute.borderColor.transparent,
                ^.cls := arrowClass,
                ^.width := "0",
                ^.height := "0",
                ^.borderWidth := arrowSize,
                ^.borderStyle.solid,
                props.position match {
                  case PositionTopLeft | PositionTop | PositionTopRight =>
                    TagMod(
                      ^.bottom := s"-$arrowSize",
                      ^.borderBottomWidth := "0",
                      ^.borderTopColor := CssVar.Color.gray9
                    )
                  case PositionBottomLeft | PositionBottom | PositionBottomRight =>
                    TagMod(
                      ^.top := s"-$arrowSize",
                      ^.borderTopWidth := "0",
                      ^.borderBottomColor := CssVar.Color.gray9
                    )
                  case PositionRightTop | PositionRight | PositionRightBottom =>
                    TagMod(
                      ^.left := s"-$arrowSize",
                      ^.borderLeftWidth := "0",
                      ^.borderRightColor := CssVar.Color.gray9
                    )
                  case PositionLeftTop | PositionLeft | PositionLeftBottom =>
                    TagMod(
                      ^.right := s"-$arrowSize",
                      ^.borderRightWidth := "0",
                      ^.borderLeftColor := CssVar.Color.gray9
                    )
                }
              ),
              // content
              // - relative position to ensure content is always over tip
              <.div(Style.position.relative.zIndex.idx1, props.renderContent())
            )
          }
        )()
      }
    }
    // scalastyle:on multiple.string.literals
  }

  private val component = ScalaComponent
    .builder[Tooltip](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}
