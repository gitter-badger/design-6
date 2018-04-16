// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import org.scalajs.dom
import org.scalajs.dom.raw.Element

import anduin.scalajs.react.ReactDom

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class LegacyPortal(
  detachOnUnmount: Boolean = false,
  nodeClasses: String = ""
) {
  def apply(children: VdomNode*): VdomElement = {
    LegacyPortal.component(this)(children: _*)
  }
}

object LegacyPortal {

  private val ComponentName = this.getClass.getSimpleName

  case class Backend(scope: BackendScope[LegacyPortal, _]) {

    private var node: Element = _ // scalastyle:ignore

    def componentDidMount(): Callback = {
      renderPortal()
    }

    def componentDidUpdate(): Callback = {
      renderPortal()
    }

    def componentWillUnmount(): Callback = {
      scope.props.flatMap { props =>
        Callback.when(props.detachOnUnmount) {
          Callback {
            if (this.node != null) {
              ReactDOM.unmountComponentAtNode(this.node)
              dom.document.body.removeChild(this.node)
            }
            this.node = null // scalastyle:ignore
          }
        }
      }
    }

    private def renderPortal() = {
      scope.props.flatMap { props =>
        if (node == null) { // scalastyle:ignore
          node = dom.document.createElement("div")
          dom.document.body.appendChild(node)
        }

        for {
          children <- scope.propsChildren
          _ <- Callback {
            ReactDom.renderSubtreeIntoContainer(component.raw, children.raw, node)
          }
        } yield ()
      }
    }

    def getNode: Element = {
      node
    }
  }

  val component = ScalaComponent
    .builder[LegacyPortal](ComponentName)
    .stateless
    .backend(Backend(_))
    .renderC((_, _) => EmptyVdom)
    .componentDidMount(_.backend.componentDidMount())
    .componentDidUpdate(_.backend.componentDidUpdate())
    .componentWillUnmount(_.backend.componentWillUnmount())
    .build
}
