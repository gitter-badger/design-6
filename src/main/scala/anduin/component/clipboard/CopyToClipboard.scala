// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.clipboard

import org.scalajs.dom.{document, window}

import anduin.component.portal.Tooltip

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class CopyToClipboard() {
  def apply(children: VdomNode*): ScalaComponent.Unmounted[_, _, _] =
    CopyToClipboard.component(this)(children: _*)
}

object CopyToClipboard {

  private val componentName = this.getClass.getSimpleName

  private val CopyCommand = "copy"

  private case class State(copied: Boolean)

  private class Backend(scope: BackendScope[CopyToClipboard, State]) {

    private def copyToClipboard = {
      for {
        node <- scope.getDOMNode.map(_.asElement)
        selection = window.getSelection()
        range = document.createRange()
        _ <- Callback {
          range.selectNodeContents(node)
          selection.removeAllRanges()
          selection.addRange(range)

          document.execCommand(CopyCommand)

          // Remove the selection ranges after copying text
          window.getSelection().removeAllRanges()
        }
        _ <- scope.modState(_.copy(copied = true))
      } yield ()
    }

    def render(state: State, children: PropsChildren): VdomNode = {
      <.div(
        ^.cls := "copy-clipboard",
        <.div(
          Tooltip(
            targetTag = <.div,
            renderTarget = () => {
              <.div(
                ^.onMouseOut --> scope.modState(_.copy(copied = false)),
                ^.onClick --> copyToClipboard,
                children
              )
            },
            renderContent = () => if (state.copied) "Copied to clipboard" else "Click to copy"
          )()
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[CopyToClipboard](componentName)
    .initialState(
      State(copied = false)
    )
    .renderBackendWithChildren[Backend]
    .build
}
