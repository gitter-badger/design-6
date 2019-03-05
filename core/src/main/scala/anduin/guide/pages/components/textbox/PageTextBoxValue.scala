package anduin.guide.pages.components.textbox

import anduin.guide.components._
import anduin.component.input.textbox.TextBox
import anduin.guide.app.main.Pages
import anduin.mcro.Source
import japgolly.scalajs.react.vdom.html_<^._

import anduin.component.icon.Icon
import anduin.style.Style

object PageTextBoxValue {

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Text Box", Some(TextBox))(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        """
          |## Tpe
          |
          |```scala
          |tpe: TextBoxType = TextBox.TpeText
          |```
          |""".stripMargin
      )(),
      ExampleSimple()({
        <.div(Style.width.px256)(
          DemoState.Str("Anduin", (value, onChange) => {
            TextBox(value = value, onChange = onChange, tpe = TextBox.Tpe.Text)()
          })(),
          <.div(Style.margin.bottom8),
          DemoState.Str("Anduin", (value, onChange) => {
            TextBox(value = value, onChange = onChange, tpe = TextBox.Tpe.Password)()
          })(),
          <.div(Style.margin.bottom8),
          DemoState.Str("Anduin", (value, onChange) => {
            TextBox(value = value, onChange = onChange, tpe = TextBox.Tpe.Area())()
          })(),
        )
      }),
      Markdown(
        """
          |
          |## Placeholder
          |
          |```scala
          |placeholder: String = ""
          |```
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.Str("", (value, onChange) => {
          <.div(Style.width.px256)(
            TextBox(
              placeholder = "Anduin",
              value = value,
              onChange = onChange
            )()
          )
        })()
      }))(),
      Markdown(
        """
          |
          |## Context
          |
          |```scala
          |context: Option[VdomNode] = None
          |```
          |
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.Str("Anduin", (value, onChange) => {
          <.div(Style.width.px256)(
            TextBox(
              context = Some(Icon(Icon.Glyph.Tag)()),
              value = value,
              onChange = onChange
            )()
          )
        })()
      }))(),
      Markdown(
        """
          |## Mask
          |
          |```scala
          |mask: Option[TextBox.Mask] = None
          |```
          |
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.Str("1000", (value, onChange) => {
          <.div(Style.width.px256)(
            TextBox(
              mask = Some(TextBox.Mask.Int),
              value = value,
              onChange = onChange
            )()
          )
        })()
      }))(),
    )
  }
}