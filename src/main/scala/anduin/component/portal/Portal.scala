// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import org.scalajs.dom
import org.scalajs.dom.raw.Element

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Portal(
  detachOnUnmount: Boolean = true,
  nodeClasses: String = ""
) {
  def apply(children: VdomNode*): VdomElement = {
    Portal.component(this)(children: _*)
  }
}

object Portal {

  private val ComponentName = this.getClass.getSimpleName

  case class Backend(scope: BackendScope[Portal, _]) {

    private var node: Element = _ // scalastyle:ignore

    def componentWillUnmount: Callback = {
      scope.props.flatMap { props =>
        Callback.when(props.detachOnUnmount) {
          Callback {
            if (this.node != null) {
              dom.document.body.removeChild(this.node)
            }
            this.node = null // scalastyle:ignore
          }
        }
      }
    }

    def getNode: Element = {
      node
    }

    def render(props: Portal, children: PropsChildren): VdomNode = {
      if (node == null) { // scalastyle:ignore
        node = dom.document.createElement("div")
        dom.document.body.appendChild(node)
      }
      node.setAttribute("class", props.nodeClasses)
      ReactPortal(children, node)
    }
  }

  val component = ScalaComponent
    .builder[Portal](ComponentName)
    .stateless
    .renderBackendWithChildren[Backend]
    .componentWillUnmount(_.backend.componentWillUnmount)
    .build
}
