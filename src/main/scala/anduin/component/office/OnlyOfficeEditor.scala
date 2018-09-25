// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.office

import scala.concurrent.Promise
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

import org.scalajs.dom
import org.scalajs.dom.ext.PimpedNodeList
import org.scalajs.dom.raw.HTMLScriptElement

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class OnlyOfficeEditor(
  containerId: String,
  apiUrl: String,
  config: OnlyOfficeConfig
) {
  def apply(): VdomElement = OnlyOfficeEditor.component(this)
}

object OnlyOfficeEditor {

  private type Props = OnlyOfficeEditor

  private case class Backend(scope: BackendScope[Props, _]) {

    def loadScript(props: Props): Callback = {
      if (dom.document.querySelectorAll(s"script[src='${props.apiUrl}']").length > 0) {
        // The script is already loaded
        showEditor(props)
      } else {
        Callback.future {
          val promise = Promise[Callback]()

          @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
          val script = dom.document.createElement("script").asInstanceOf[HTMLScriptElement]
          script.setAttribute("src", props.apiUrl)
          script.setAttribute("async", "true")
          script.onload = { _ =>
            promise.success {
              showEditor(props)
            }
          }

          dom.document.body.appendChild(script)
          promise.future
        }
      }
    }

    def removeScript(props: Props): Callback = {
      Callback {
        PimpedNodeList(dom.document.querySelectorAll("script[src]")).map { script =>
          @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
          val src = script.asInstanceOf[HTMLScriptElement].src
          if (src == props.apiUrl) {
            script.parentNode.removeChild(script)
          }
        }
      }
    }

    private def showEditor(props: Props) = {
      Callback {
        OnlyOfficeFacade.Editor(props.containerId, props.config)
      }
    }

    def render(props: Props): VdomElement = {
      <.div(^.id := props.containerId)
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .componentDidMount { scope =>
      scope.backend.loadScript(scope.props)
    }
    .componentWillUnmount { scope =>
      scope.backend.removeScript(scope.props)
    }
    .build
}
