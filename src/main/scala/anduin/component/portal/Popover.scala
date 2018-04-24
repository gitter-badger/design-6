// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import org.scalajs.dom.raw.Element

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Popover(
  popoverClassName: String = Style.padding.all8.value,
  position: Position = PositionBottom,
  verticalOffset: Double = 0,
  horizontalOffset: Double = 0,
  closeOnEsc: Boolean = true,
  closeOnOutsideClick: Boolean = true,
  isPortalClicked: (Element, Element) => CallbackTo[Boolean] = PortalWrapper.IsPortalClicked,
  renderTarget: (Callback, Status) => VdomElement,
  renderContent: Callback => VdomElement
) {
  def apply(): VdomElement = {
    Popover.component(this)
  }
}

object Popover {

  private val ComponentName = this.getClass.getSimpleName

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
        isPortalClicked = props.isPortalClicked,
        renderTarget = (open, _, status) => {
          <.div.withRef(targetRef)(
            props.renderTarget(open, status)
          )
        },
        renderContent = (close, status) => {
          <.div.withRef(portalRef)(
            ^.classSet(
              "at-popover" -> true,
              props.popoverClassName -> true,
              "-show" -> (status == StatusOpen)
            ),
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
