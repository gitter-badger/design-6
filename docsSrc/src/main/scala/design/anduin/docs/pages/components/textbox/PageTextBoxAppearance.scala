package design.anduin.docs.pages.components.textbox

import japgolly.scalajs.react.vdom.html_<^._

import design.anduin.components.input.textbox.TextBox
import design.anduin.docs.app.main.Pages
import design.anduin.docs.components._
import design.anduin.macros.Source
import design.anduin.style.Style

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
