// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import org.scalajs.dom.raw.Element

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Tooltip(
  tooltipClassName: String = Style.padding.all8.value,
  position: Position = PositionBottom,
  verticalOffset: Double = 0,
  horizontalOffset: Double = 0,
  renderTarget: (Callback, Status) => VdomNode,
  renderContent: Callback => VdomNode
) {
  def apply(): VdomElement = {
    Tooltip.component(this)
  }
}

object Tooltip {

  private val ComponentName = this.getClass.getSimpleName

  private case class Backend(scope: BackendScope[Tooltip, _]) {

    private val targetRef = Ref[Element]
    private val portalRef = Ref[Element]

    private def onOpenPortal = {
      for {
        props <- scope.props
        target <- targetRef.get
        content <- portalRef.get
        _ <- Callback {
          Position.calculate(props.position, target, content, props.verticalOffset, props.horizontalOffset)
        }
      } yield ()
    }

    def render(props: Tooltip): VdomElement = {
      PortalWrapper(
        onOpen = onOpenPortal,
        renderTarget = (open, close, status) => {
          <.div.withRef(targetRef)(
            ^.onMouseOver --> open,
            ^.onMouseOut --> close,
            props.renderTarget(open, status)
          )
        },
        renderContent = (close, _) => {
          <.div.withRef(portalRef)(
            Style.position.absolute,
            ^.classSet(
              "at-tooltip" -> true,
              props.position.className -> true,
              props.tooltipClassName -> true
            ),
            props.renderContent(close)
          )
        }
      )()
    }
  }

  private val component = ScalaComponent
    .builder[Tooltip](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}
