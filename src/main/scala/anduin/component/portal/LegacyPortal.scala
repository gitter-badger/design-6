// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import japgolly.scalajs.react.extra.{EventListener, OnUnmount}
import org.scalajs.dom
import org.scalajs.dom.raw.{Element, HashChangeEvent}

import anduin.scalajs.react.ReactDom

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class LegacyPortal(
  status: Status,
  children: (Callback, Status) => VdomNode
) {
  def apply(): VdomElement = {
    LegacyPortal.component(this)
  }
}

object LegacyPortal {

  private val ComponentName = this.getClass.getSimpleName

  case class Backend(scope: BackendScope[LegacyPortal, _]) extends OnUnmount {

    private var node: Element = _ // scalastyle:ignore

    def componentDidMount(): Callback = renderPortal()

    def componentDidUpdate(): Callback = renderPortal()

    def render(): VdomNode = EmptyVdom

    private def renderPortal() = {
      for {
        props <- scope.props
        children = props.children(unmountNode(), props.status)
        _ <- {
          if (props.status != StatusClose) {
            if (node == null) { // scalastyle:ignore
              node = dom.document.createElement("div")
              dom.document.body.appendChild(node)
            }

            Callback {
              ReactDom.renderSubtreeIntoContainer(scope.raw, children.rawNode, node)
            }
          } else {
            unmountNode()
          }
        }
      } yield ()
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

    def getNode: Element = node

    // We don't unmount the component automatically when the parent node is unmounted.
    // Instead, the component is unmounted when user switch to other page
    def onWindowHashchange(e: HashChangeEvent): Callback = {
      Callback.when(e.newURL != e.oldURL) {
        unmountNode()
      }
    }
  }

  val component = ScalaComponent
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
