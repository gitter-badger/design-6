// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.clipboard

import anduin.component.portal.PositionTopCenter
import anduin.component.portal.tooltip.Tooltip
import org.scalajs.dom.raw.HTMLElement
import org.scalajs.dom.{document, window}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class CopyToClipboard() {
  def apply(children: VdomNode*): VdomElement = CopyToClipboard.component(this)(children: _*)
}

object CopyToClipboard {

  private val CopyCommand = "copy"

  private type Props = CopyToClipboard

  private final case class State(copied: Boolean)

  private final case class Backend(scope: BackendScope[Props, State]) {

    private val childrenRef = Ref[HTMLElement]

    private def copyToClipboard = {
      for {
        node <- childrenRef.get
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

    def render(state: State, children: PropsChildren): VdomElement = {
      Tooltip(
        position = PositionTopCenter,
        targetTag = <.span,
        renderTarget = <.span.withRef(childrenRef)(
          ^.onClick --> copyToClipboard,
          ^.onMouseLeave --> scope.modState(_.copy(copied = false)),
          children
        ),
        renderContent = () => if (state.copied) "Copied to clipboard" else "Click to copy"
      )()
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialState(State(copied = false))
    .renderBackendWithChildren[Backend]
    .build
}
