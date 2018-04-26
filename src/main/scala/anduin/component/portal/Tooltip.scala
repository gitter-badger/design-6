// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import org.scalajs.dom.raw.Element

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Tooltip(
  position: Position = PositionBottom,
  isDisabled: Boolean = false,
  renderTarget: () => VdomNode,
  renderContent: () => VdomNode
) {
  def apply(): VdomElement = {
    Tooltip.component(this)
  }
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
      case PositionTopLeft     => (auto, auto, nega, posi)
      case PositionTop         => (auto, zero, nega, zero)
      case PositionTopRight    => (auto, posi, nega, auto)
      case PositionRightTop    => (posi, auto, auto, nega)
      case PositionRight       => (zero, auto, zero, nega)
      case PositionRightBottom => (auto, auto, posi, nega)
      case PositionBottomRight => (nega, auto, auto, posi)
      case PositionBottom      => (nega, zero, auto, zero)
      case PositionBottomLeft  => (nega, posi, auto, auto)
      case PositionLeftTop     => (posi, nega, auto, auto)
      case PositionLeft        => (zero, nega, zero, auto)
      case PositionLeftBottom  => (auto, nega, posi, auto)
    }
    TagMod(^.top := top, ^.right := right, ^.bottom := bottom, ^.left := left)
  }
  // scalastyle:on cyclomatic.complexity

  private case class Backend(scope: BackendScope[Tooltip, _]) {

    private val targetRef = Ref[Element]
    private val portalRef = Ref[Element]

    private def onOpenPortal = {
      for {
        props <- scope.props
        target <- targetRef.get
        content <- portalRef.get
        _ <- Callback {
          val (verOff, horOff): (Double, Double) = props.position match {
            case PositionTopLeft | PositionTop | PositionTopRight          => (-tipSize, 0)
            case PositionRightTop | PositionRight | PositionRightBottom    => (0, tipSize)
            case PositionBottomRight | PositionBottom | PositionBottomLeft => (tipSize, 0)
            case PositionLeftTop | PositionLeft | PositionLeftBottom       => (0, -tipSize)
          }
          Position.calculate(props.position, target, content, verOff, horOff)
        }
      } yield ()
    }

    def render(props: Tooltip): VdomNode = {
      if (props.isDisabled) {
        props.renderTarget()
      } else {
        PortalWrapper(
          onOpen = onOpenPortal,
          renderTarget = (open, close, _) => {
            <.div.withRef(targetRef)(
              ^.onMouseOver --> open,
              ^.onMouseOut --> close,
              props.renderTarget()
            )
          },
          renderContent = (_, _) => {
            <.div.withRef(portalRef)(
              Position.styles,
              Style.zIndex.idx9999,
              Style.backgroundColor.gray9.color.white.shadow.blur8,
              Style.padding.ver4.padding.hor8.borderRadius.px4,
              // tip
              <.div(
                Style.position.absolute.zIndex.idx0,
                Style.backgroundColor.gray9,
                ^.transform := "rotate(45deg)",
                ^.width := tipSizePx,
                ^.height := tipSizePx,
                ^.margin := "auto",
                getTipMod(props.position)
              ),
              // content
              <.span(
                // ensure content is always over tip
                Style.position.relative.zIndex.idx1,
                props.renderContent()
              )
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
