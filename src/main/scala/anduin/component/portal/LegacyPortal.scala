// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import org.scalajs.dom
import org.scalajs.dom.raw.Element

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

  case class Backend(scope: BackendScope[LegacyPortal, _]) {

    private var node: Element = _ // scalastyle:ignore

    def componentDidMount(): Callback = renderPortal()

    def componentDidUpdate(): Callback = renderPortal()

//    def componentWillUnmount(): Callback = {
//      println("componentWillUnmount")
//      scope.props.flatMap { props =>
//        Callback {
//          if (node != null) {
//            ReactDOM.unmountComponentAtNode(node)
//            dom.document.body.removeChild(node)
//          }
//          node = null // scalastyle:ignore
//        }
//      }
//    }

    private def renderPortal() = {
      for {
        props <- scope.props
        children <- scope.propsChildren
        _ <- Callback.log(s"props.status --> ${props.status}")
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
  }

  val component = ScalaComponent
    .builder[LegacyPortal](ComponentName)
    .stateless
    .backend(Backend(_))
    .renderC((_, _) => EmptyVdom)
    .componentDidMount(_.backend.componentDidMount())
    .componentDidUpdate(_.backend.componentDidUpdate())
    //.componentWillUnmount(_.backend.componentWillUnmount())
    .build
}
