package anduin.guide.pages.components.textbox

import anduin.component.field.Field
import anduin.guide.components._
import anduin.component.input.textbox.{TextBox, TextBoxType}
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
          |Text box is a [controlled] component. The current value should be
          |passed via the same name prop, as well as updated with the
          |`onChange` callback:
          |
          |[controlled]: https://reactjs.org/docs/forms.html#controlled-components
          |
          |```scala
          |// case class State(value: String, ...)
          |
          |TextBox(
          |  value = state.value,
          |  onChange = v => scope.modState(_.copy(value = v)
          |)()
          |```
          |""".stripMargin
      )(),
      Markdown(
        """
          |## Constraint
          |
          |### Type
          |
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.Str("", (value, onChange) => {
          <.div(Style.width.px256)(
            TextBox(value, onChange, tpe = TextBox.Tpe.Date)()
          )
        })()
      }))(),
      ExampleSimple()({
        import TextBox.Tpe._
        <.div(
          ^.width := "300px",
          List(Text, Password, Date, Area()).zipWithIndex.toVdomArray { tuple =>
            val (tpe, index) = tuple
            val name = tpe.getClass.getSimpleName
            val textBox = DemoState.Str("Anduin", (v, c) => {
              TextBox(v, c, tpe, id = Some(s"tpe-$name"))()
            })()
            val layout = Field.Layout.Hor(right = Field.Layout.Grow(2))
            <.div(
              TagMod.when(index != 0)(Style.margin.top8),
              Field(layout, s"tpe-$name", Some(name))(textBox)
            )
          }
        )
      }),
      Markdown(
        """
          |### Mask
          |
          |## Assistance
          |
          |### Placeholder
          |
          |### Icon
          |
          |### Status
          |
          |### Field
          |
          |# Appearance
          |
          |## Style
          |
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.Str("Anduin", (value, onChange) => {
          <.div(Style.width.px256)(
            TextBox(value, onChange).copy(
              style = TextBox.Style.Minimal
            )()
          )
        })()
      }))(),
      ExampleSimple()({
        TextBoxEmail()()
      }),
      Markdown(
        """
          |
          |## Size
          |
          |# Behaviour
          |
          |## Disabled
          |
          |## Auto-focus
          |
          |## Others
        """.stripMargin
      )()
    )
  }
}
