// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import org.scalajs.dom.raw.Element

import anduin.scalajs.popper.{Popper, PopperOptions}
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
  targetTag: HtmlTag = <.div,
  // (open, close, update position, status) => target Vdom
  renderTarget: (Callback, Callback, Callback, Status) => VdomNode,
  // (close, update position) => content Vdom
  renderContent: (Callback, Callback) => VdomNode
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

  private class Backend(scope: BackendScope[Popover, _]) {

    private val targetRef = Ref[Element]
    private val portalRef = Ref[Element]

    private def updatePosition = {
      for {
        props <- scope.props
        target <- targetRef.get
        content <- portalRef.get
        _ <- Callback {
          // TODO: Destroy the Popper instance after closing the Popover
          new Popper(target, content, new PopperOptions(props.position.placement))
        }
      } yield ()
    }

    def render(props: Popover): VdomElement = {
      PortalWrapper(
        onOpen = updatePosition,
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
          props.targetTag.withRef(targetRef)(
            props.renderTarget(open, close, updatePosition, status)
          )
        },
        renderContent = (close, _) => {
          val content = props.renderContent(close, updatePosition)
          if (content == EmptyVdom) {
            EmptyVdom
          } else {
            <.div.withRef(portalRef)(
              Style.zIndex.idx999,
              Style.backgroundColor.white.borderRadius.px2.shadow.blur8,
              Style.border.all.borderColor.gray4.borderWidth.px1,
              content
            )
          }
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
