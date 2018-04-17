// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import japgolly.scalajs.react.extra.{EventListener, OnUnmount}
import org.scalajs.dom
import org.scalajs.dom.raw.{Element, HashChangeEvent}

import anduin.scalajs.react.ReactDom
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class LegacyPortal(status: Status) {
  def apply(children: VdomNode*): VdomElement = {
    LegacyPortal.component(this)(children: _*)
  }
}

object LegacyPortal {

  private val ComponentName = this.getClass.getSimpleName

  private val HideClass = Style.display.none.value

  case class Backend(scope: BackendScope[LegacyPortal, _]) extends OnUnmount {

    private var node: Element = _ // scalastyle:ignore

    def componentDidMount(): Callback = renderPortal()

    def componentDidUpdate(): Callback = renderPortal()

    private def renderPortal() = {
      for {
        props <- scope.props
        children <- scope.propsChildren
        _ <- Callback {
          if (props.status != StatusClose) {
            if (node == null) { // scalastyle:ignore
              node = dom.document.createElement("div")
              dom.document.body.appendChild(node)
            }
            if (node.classList.contains(HideClass)) {
              node.classList.remove(HideClass)
            }

            ReactDom.renderSubtreeIntoContainer(scope.raw, children.rawNode, node)
          } else {
            if (node != null) {
              // Hide the node
              node.classList.add(HideClass)
            }
          }
        }
      } yield ()
    }

    def getNode: Element = node

    def onWindowHashchange(e: HashChangeEvent): Callback = {
      Callback.when(e.newURL != e.oldURL) {
        Callback {
          if (node != null) {
            ReactDOM.unmountComponentAtNode(node)
            dom.document.body.removeChild(node)
          }
          node = null // scalastyle:ignore
        }
      }
    }
  }

  val component = ScalaComponent
    .builder[LegacyPortal](ComponentName)
    .stateless
    .backend(Backend(_))
    .renderC((_, _) => EmptyVdom)
    .componentDidMount(_.backend.componentDidMount())
    .componentDidUpdate(_.backend.componentDidUpdate())
    .configure(
      EventListener[HashChangeEvent].install("hashchange", _.backend.onWindowHashchange, _ => dom.window)
    )
    .build
}
