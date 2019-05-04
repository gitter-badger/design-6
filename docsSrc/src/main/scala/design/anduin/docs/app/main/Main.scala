package design.anduin.docs.app.main

import design.anduin.docs.app.router.Router
import design.anduin.docs.facades.highlight.Highlight
import design.anduin.facades.focusvisible.FocusVisible
import japgolly.scalajs.react.extra.router.{BaseUrl, Router => RawRouter}
import org.scalajs.dom

object Main {
  private val baseUrl = BaseUrl.fromWindowOrigin / ""

  def main(args: Array[String]): Unit = {

    // 3rd-party libs init
    Highlight.Core.registerLanguage("scala", Highlight.Language.Scala)
    // This does nothing, just mention so they are included by scalajs-bundler
    FocusVisible

    // DOM init
    val container = dom.document.getElementById("root")
    val router = RawRouter(baseUrl, Router.config)
    router().renderIntoDOM(container)
  }
}
