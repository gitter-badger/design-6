package anduin.guide.pages.components.textbox

import anduin.guide.components._
import anduin.component.input.textbox.TextBox
import anduin.guide.app.main.Pages
import anduin.mcro.Source
import japgolly.scalajs.react.vdom.html_<^._

import anduin.style.Style

object PageTextBox {

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Text Box", Some(TextBox))(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        """
          |Text boxes let users enter and edit text:
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.Str("Anduin", (value, onChange) => {
          <.div(Style.width.px256)(
            TextBox(value = value, onChange = onChange)()
          )
        })()
      }))(),
      Markdown(
        """
          |
          |# Value
          |
          |```scala
          |value: String
          |onChange: String => Callback = _ => Callback.empty
          |```
          |
          |Text box is a [controlled] component. Its consumers should provide
          |the current value via the `value` prop, as well as updated it
          |accordingly with the `onChange` callback:
          |
          |[controlled]: https://reactjs.org/docs/forms.html#controlled-components
          |
          |```scala
          |// case class State(value: String, ...)
          |
          |TextBox(
          |  value = state.value,
          |  onChange = newValue => {
          |    scope.modState(_.copy(value = newValue)
          |  }
          |)()
          |```
          |
          |""".stripMargin
      )(),
      Markdown(
        """
          |# Behaviour
          |
          |## Disabled & Read-only
          |
          |```scala
          |isDisabled: Boolean = false
          |isReadOnly: Boolean = false
          |```
          |
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.Str("Anduin", (value, onChange) => {
          <.div(Style.width.px256)(
            TextBox(value = value, onChange = onChange)()
          )
        })()
      }))(),
      Markdown(
        """
          |## Required
          |
          |```scala
          |isRequired: Boolean = false
          |```
          |
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.Str("Anduin", (value, onChange) => {
          <.div(Style.width.px256)(
            TextBox(value = value, onChange = onChange)()
          )
        })()
      }))(),
      Markdown(
        """
          |## Auto-focus
          |
          |```scala
          |isAutoFocus: Boolean = false
          |```
          |
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.Str("Anduin", (value, onChange) => {
          <.div(Style.width.px256)(
            TextBox(value = value, onChange = onChange)()
          )
        })()
      }))(),
      Markdown(
        """
          |# Others
          |
          |```scala
          |id: Option[String] = None
          |onFocus: Callback = Callback.empty
          |onBlur: String => Callback = _ => Callback.empty
          |onKeyDown: ReactKeyboardEventFromInput => Callback = _ => Callback.empty
          |onKeyUp: ReactKeyboardEventFromInput => Callback = _ => Callback.empty
          |```
          |
        """.stripMargin
      )()
    )
  }
}
