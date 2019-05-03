// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.guide.pages.components.textbox

import anduin.component.input.textbox.TextBox
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class TextBoxEmail() {
  def apply(): VdomElement = TextBoxEmail.component(this)
}

object TextBoxEmail {

  private type Props = TextBoxEmail

  private case class State(values: Map[String, String])

  private class Backend(scope: BackendScope[Props, State]) {
    def render(state: State): VdomElement = {
      <.div(
        Style.width.px256,
        List("First Name", "Last Name", "Company", "Email").toVdomArray { key =>
          <.div(
            ^.key := key,
            TextBox(
              state.values.getOrElse(key, ""),
              v => scope.modState(_.copy(values = state.values.updated(key, v))),
              placeholder = key,
              style = TextBox.Style.Minimal
            )(),
            <.div(
              Style.margin.top4.padding.top4,
              Style.border.top.borderColor.gray3
            )
          )
        }
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialState(State(Map.empty))
    .renderBackend[Backend]
    .build
}
