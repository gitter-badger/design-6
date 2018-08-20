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
  isClosable: Option[PortalUtils.isClosable] = PortalUtils.defaultIsClosable,
  isPortalClicked: (Element, Element, Element) => CallbackTo[Boolean] = Popover.IsPortalClicked,
  targetTag: HtmlTag = <.div,
  // (toggle, update position, isOpened) => target Vdom
  renderTarget: (Callback, Callback, Boolean) => VdomNode,
  // (close, update position) => content Vdom
  renderContent: (Callback, Callback) => VdomNode
) {
  def apply(): VdomElement = Popover.component(this)
}

object Popover {

  private type Props = Popover

  val IsPortalClicked = (clickedTarget: Element, target: Element, portal: Element) => {
    CallbackTo(target.contains(clickedTarget) || portal.contains(clickedTarget))
  }

  private class Backend(scope: BackendScope[Props, _]) {

    private val targetRef = Ref[Element]
    private val portalRef = Ref[Element]

    private var popper: Option[Popper] = None // scalastyle:ignore var.field

    private def updatePosition = {
      for {
        props <- scope.props
        target <- targetRef.get
        content <- portalRef.get
        _ <- Callback {
          popper = Some(new Popper(target, content, new PopperOptions(props.position.placement)))
        }
      } yield ()
    }

    private def close: Callback = {
      // Destroy the Popper instance after closing the Popover
      Callback.traverseOption(popper) { p =>
        Callback {
          p.destroy()
        }
      }
    }

    def render(props: Props): VdomElement = {
      PortalWrapper(
        onOpen = updatePosition,
        onClose = close,
        closeOnEsc = props.isClosable.exists(_.onEsc),
        closeOnOutsideClick = props.isClosable.exists(_.onOutsideClick),
        isPortalClicked = (clickedTarget, portal) => {
          targetRef.get.asCallback.flatMap { target =>
            target.fold(CallbackTo(false)) { t =>
              props.isPortalClicked(clickedTarget, t, portal)
            }
          }
        },
        renderTarget = (open, close, status) => {
          val isOpened = status == StatusOpen
          val toggle = if (isOpened) close else open
          props.targetTag.withRef(targetRef)(
            props.renderTarget(toggle, updatePosition, isOpened)
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
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .build
}
