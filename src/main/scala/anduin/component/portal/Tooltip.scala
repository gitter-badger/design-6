// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import org.scalajs.dom.raw.Element

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Tooltip(
  position: Position = PositionTop,
  renderTarget: () => VdomNode,
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

  private case class Backend(scope: BackendScope[Tooltip, _]) {

    private val targetRef = Ref[Element]
    private val contentRef = Ref[Element]

    private def onOpenPortal = {
      for {
        props <- scope.props
        target <- targetRef.get
        content <- contentRef.get
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
      val target = props.renderTarget()

      if (props.isDisabled) {
        // it is intentional to render targetTag wrapper here to keep the
        // HTML result consistent with the other case
        props.targetTag(target)
      } else {
        PortalWrapper(
          onOpen = onOpenPortal,
          renderTarget = (open, close, _) => {
            props.targetTag.withRef(targetRef)(^.onMouseEnter --> open, ^.onMouseLeave --> close, target)
          },
          renderContent = (_, _) => {
            <.div.withRef(contentRef)(
              Position.styles,
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
