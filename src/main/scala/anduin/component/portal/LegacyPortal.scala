// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import japgolly.scalajs.react.extra.{EventListener, OnUnmount}
import org.scalajs.dom
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.raw.{Element, HashChangeEvent, KeyboardEvent, MouseEvent}

import anduin.component.util.EventUtils
import anduin.scalajs.react.ReactDom

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[portal] final case class LegacyPortal(
  status: Status,
  children: (Callback, Status) => VdomNode,
  closeOnOutsideClick: Boolean = true,
  isPortalClicked: (Element, Element) => CallbackTo[Boolean],
  onClose: Callback
) {
  def apply(): VdomElement = {
    LegacyPortal.component(this)
  }
}

private[portal] object LegacyPortal {

  private val ComponentName = this.getClass.getSimpleName

  private case class Backend(scope: BackendScope[LegacyPortal, _]) extends OnUnmount {

    private var node: Element = _ // scalastyle:ignore
    private var shouldCloseOpt: Option[Boolean] = None // scalastyle:off var.field

    def componentDidMount(): Callback = {
      dom.document.addEventListener("click", onDocumentClick)
      dom.document.addEventListener("keydown", onDocumentKeydown)
      renderPortal()
    }

    def componentDidUpdate(): Callback = renderPortal()

    def render(): VdomNode = EmptyVdom

    private def renderPortal() = {
      for {
        props <- scope.props
        children = props.children(destroy(props), props.status)
        _ <- Callback.when(props.status != StatusClose) {
          if (node == null) { // scalastyle:ignore
            node = dom.document.createElement("div")
            dom.document.body.appendChild(node)
          }
          shouldCloseOpt = Some(false)

          Callback {
            ReactDom.renderSubtreeIntoContainer(scope.raw, children.rawNode, node)
          }
        }
      } yield ()
    }

    private def removeEventHandlers() = {
      dom.document.removeEventListener("click", onDocumentClick)
      dom.document.removeEventListener("keydown", onDocumentKeydown)
    }

    private def unmountNode() = {
      Callback {
        if (node != null) {
          ReactDOM.unmountComponentAtNode(node)
          dom.document.body.removeChild(node)
        }
        node = null // scalastyle:ignore
      }
    }

    private def onDocumentClick(e: MouseEvent): Unit = {
      val cb = for {
        props <- scope.props
        _ <- Callback {
          if (shouldCloseOpt.isEmpty) {
            shouldCloseOpt = Some(true)
          }
        }
        _ <- Callback.when(
          shouldCloseOpt.contains(true) && props.status == StatusOpen
            && Option(node).nonEmpty && EventUtils.leftButtonClicked(e)
        ) {
          val clickInside = e.target match {
            case t: Element => props.isPortalClicked(t, node)
            case _          => CallbackTo(false)
          }
          clickInside.flatMap { isInside =>
            Callback.when(!isInside && props.closeOnOutsideClick)(destroy(props))
          }
        }
        _ <- Callback {
          shouldCloseOpt = None
        }
      } yield ()

      cb.runNow()
    }

    private def onDocumentKeydown(e: KeyboardEvent): Unit = {
      val cb = for {
        props <- scope.props
        _ <- Callback.when(e.keyCode == KeyCode.Escape && props.status == StatusOpen) {
          destroy(props)
        }
      } yield ()

      cb.runNow()
    }

    private def destroy(props: LegacyPortal) = {
      Callback(removeEventHandlers()) >> unmountNode() >> props.onClose
    }

    // We don't unmount the component automatically when the parent node is unmounted.
    // Instead, the component is unmounted when user switch to other page
    def onWindowHashchange(e: HashChangeEvent): Callback = {
      Callback.when(e.newURL != e.oldURL) {
        // After unmounting the portal, we also need to run `props.onClose` which updates status of `PortalWithState`.
        // It ensures that the portal won't be shown again.
        unmountNode() >> scope.props.flatMap(_.onClose)
      }
    }
  }

  private val component = ScalaComponent
    .builder[LegacyPortal](ComponentName)
    .stateless
    .renderBackend[Backend]
    .componentDidMount(_.backend.componentDidMount())
    .componentDidUpdate(_.backend.componentDidUpdate())
    .configure(
      EventListener[HashChangeEvent].install("hashchange", _.backend.onWindowHashchange, _ => dom.window)
    )
    .build
}
