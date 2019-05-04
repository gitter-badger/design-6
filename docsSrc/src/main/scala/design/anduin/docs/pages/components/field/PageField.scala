package design.anduin.docs.pages.components.field

import design.anduin.docs.components._
import design.anduin.components.field.Field
import design.anduin.components.input.textbox.TextBox
import design.anduin.docs.app.main.Pages
import design.anduin.docs.components.DemoState
import design.anduin.macros.Source
import japgolly.scalajs.react.vdom.html_<^._

object PageField {

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Field", Some(Field))(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        """
          |
        """.stripMargin
      )(),
      // xxxxxxxxxxxxxxx
      ExampleRich(Source.annotate({
        DemoState.Str(
          "",
          (value, onChange) => {
            Field(
              layout = Field.Layout.Hor(),
              id = "foo",
              label = Some("Pre-money valuation")
            )(
              TextBox(value, onChange, id = Some("foo"))()
            )
          }
        )()
      }))(),
      ExampleRich(Source.annotate({
        DemoState.Str(
          "",
          (value, onChange) => {
            Field(
              layout = Field.Layout.Hor(),
              id = "foo2",
              label = Some("Pre-money valuation"),
              desc = Some("10-20% are industry standard")
            )(
              TextBox(value, onChange, id = Some("foo2"))()
            )
          }
        )()
      }))(),
      ExampleRich(Source.annotate({
        DemoState.Str(
          "",
          (value, onChange) => {
            Field(
              layout = Field.Layout.Hor(),
              id = "foo2",
              label = Some("Pre-money valuation"),
              desc = Some("10-20% are industry standard"),
              help = Some(
                """
                  |The sum of the percentage of company owned by the
                  |investors and the ESOP cannot exceed 100%.
                """.stripMargin
              ),
              error = Some(
                """
                  |The sum of the percentage of company owned by the
                  |investors and the ESOP cannot exceed 100%.
                """.stripMargin
              )
            )(
              TextBox(value, onChange, id = Some("foo2"))()
            )
          }
        )()
      }))()
    )
  }
}
