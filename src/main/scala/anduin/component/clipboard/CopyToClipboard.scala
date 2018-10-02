// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.clipboard

import org.scalajs.dom.{document, window}

import anduin.component.portal.{PositionRightCenter, Tooltip}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class CopyToClipboard() {
  def apply(children: VdomNode*): VdomElement = CopyToClipboard.component(this)(children: _*)
}

object CopyToClipboard {

  private val CopyCommand = "copy"

  private case class State(copied: Boolean)

  private class Backend(scope: BackendScope[CopyToClipboard, State]) {

    private def copyToClipboard = {
      for {
        node <- scope.getDOMNode.map(_.asMounted().asElement())
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
        _ <- scope.modState(_.copy(copied = false)).delayMs(3000)
      } yield ()
    }

    def render(state: State, children: PropsChildren): VdomElement = {
      Tooltip(
        position = PositionRightCenter,
        targetTag = <.span,
        renderTarget = <.span(^.onClick --> copyToClipboard, children),
        renderContent = () => if (state.copied) "Copied to clipboard" else "Click to copy"
      )()
    }
  }

  private val component = ScalaComponent
    .builder[CopyToClipboard](this.getClass.getSimpleName)
    .initialState(
      State(copied = false)
    )
    .renderBackendWithChildren[Backend]
    .build
}
