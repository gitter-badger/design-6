package anduin.guide.pages.components.textbox

import japgolly.scalajs.react.vdom.html_<^._

import anduin.component.input.textbox.TextBox
import anduin.guide.app.main.Pages
import anduin.guide.components._
import anduin.mcro.Source
import anduin.style.Style

object PageTextBoxAppearance {

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Text Box Appearance", Some(TextBox))(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        """
          |
        """.stripMargin
      )(),
      Markdown(
        """
          |# Appearance
          |
          |## Style
          |
          |```scala
          |style: TextBoxSize = TextBox.Size32
          |```
          |
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.Str("Anduin", (value, onChange) => {
          <.div(Style.width.px256)(
            TextBox(
              value = value,
              onChange = onChange
            )()
          )
        })()
      }))(),
      Markdown(
        """
          |## Width
          |
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.Str("Anduin", (value, onChange) => {
          <.div(Style.width.px128)(
            TextBox(value = value, onChange = onChange)()
          )
        })()
      }))(),
      Markdown(
        """
          |## Height
          |
          |```scala
          |height: TextBoxHeight = TextBox.Height32
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
          |## Status
          |
          |```scala
          |status: TextBox.Status = TextBox.StatusNone
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
    )
  }
}
