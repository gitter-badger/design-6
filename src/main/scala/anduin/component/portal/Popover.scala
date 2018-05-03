// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import org.scalajs.dom.raw.Element

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Popover(
  position: Position = PositionBottom,
  verticalOffset: Double = 0,
  horizontalOffset: Double = 0,
  closeOnEsc: Boolean = true,
  closeOnOutsideClick: Boolean = true,
  isPortalClicked: (Element, Element, Element) => CallbackTo[Boolean] = Popover.IsPortalClicked,
  renderTarget: (Callback, Callback, Status) => VdomElement,
  renderContent: Callback => VdomElement
) {
  def apply(): VdomElement = {
    Popover.component(this)
  }
}

object Popover {

  private val ComponentName = this.getClass.getSimpleName

  val IsPortalClicked = (clickedTarget: Element, target: Element, portal: Element) => {
    CallbackTo(target.contains(clickedTarget) || portal.contains(clickedTarget))
  }

  private case class Backend(scope: BackendScope[Popover, _]) {

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

    def render(props: Popover): VdomElement = {
      PortalWrapper(
        onOpen = onOpenPortal,
        closeOnEsc = props.closeOnEsc,
        closeOnOutsideClick = props.closeOnOutsideClick,
        isPortalClicked = (clickedTarget, portal) => {
          targetRef.get.asCallback.flatMap { target =>
            target.fold(CallbackTo(false)) { t =>
              props.isPortalClicked(clickedTarget, t, portal)
            }
          }
        },
        renderTarget = (open, close, status) => {
          <.div.withRef(targetRef)(
            props.renderTarget(open, close, status)
          )
        },
        renderContent = (close, _) => {
          <.div.withRef(portalRef)(
            Position.styles,
            Style.zIndex.idx999,
            Style.backgroundColor.white.borderRadius.px4.shadow.blur8,
            Style.border.all.borderColor.gray4.borderWidth.px1,
            props.renderContent(close)
          )
        }
      )()
    }
  }

  private val component = ScalaComponent
    .builder[Popover](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}
