// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.clipboard

import org.scalajs.dom

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class ClipboardCopy(
  text: String,
  render: (Callback, Boolean) => VdomElement
) {
  def apply(): VdomElement = ClipboardCopy.component(this)
}

object ClipboardCopy {

  private type Props = ClipboardCopy

  private case class State(isJustCopied: Boolean)

  private def copyText(text: String): Callback = Callback {
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf")) // scalastyle:ignore
    val textArea = dom.document.createElement("textarea").asInstanceOf[dom.html.TextArea]
    textArea.value = text
    dom.document.body.appendChild(textArea)
    textArea.focus()
    textArea.select()
    dom.document.execCommand("copy")
    dom.document.body.removeChild(textArea)
  }

  private class Backend(scope: BackendScope[Props, State]) {

    private def copy: Callback =
      for {
        props <- scope.props
        _ <- copyText(props.text)
        _ <- scope.modState(_.copy(isJustCopied = true))
        _ <- scope.modState(_.copy(isJustCopied = false)).delayMs(1000).toCallback
      } yield ()

    def render(props: Props, state: State): VdomElement = {
      props.render(copy, state.isJustCopied)
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialState(State(isJustCopied = false))
    .renderBackend[Backend]
    .build
}
