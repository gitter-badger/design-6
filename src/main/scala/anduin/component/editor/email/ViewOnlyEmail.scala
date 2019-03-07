// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.email

import anduin.component.button.Button
import anduin.component.icon.Icon
import anduin.component.tooltip.Tooltip
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class ViewOnlyEmail(content: String) {
  def apply(): VdomElement = ViewOnlyEmail.component(this)
}

object ViewOnlyEmail {

  private type Props = ViewOnlyEmail

  private case class State(hideQuotedContent: Boolean = false)

  private case class Backend(scope: BackendScope[Props, State]) {

    private def toggleQuotedContent() = {
      scope.modState { state =>
        state.copy(hideQuotedContent = !state.hideQuotedContent)
      }
    }

    def render(props: Props, state: State): VdomElement = {
      if (state.hideQuotedContent) {
        <.div(
          EmailFrame(
            content = QuoteTransformer.removeQuotedHtml(props.content)
          )(),
          // Dots for toggling the quoted content
          <.div(
            Style.margin.ver8,
            Button(
              style = Button.Style.Minimal(),
              onClick = toggleQuotedContent
            )(
              Tooltip(
                renderTarget = Icon(name = Icon.Glyph.EllipsisHorizontal)(),
                renderContent = () => "Show message history"
              )()
            )
          )
        )
      } else {
        EmailFrame(
          content = props.content
        )()
      }
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialStateFromProps { props =>
      State(
        hideQuotedContent = QuoteTransformer.hasQuotedHtml(props.content)
      )
    }
    .renderBackend[Backend]
    .build
}
