package anduin.guide.pages.components.field

import anduin.guide.components._
import anduin.component.field.Field
import anduin.component.input.textbox.TextBox
import anduin.guide.app.main.Pages
import anduin.guide.components.SimpleState
import anduin.mcro.Source
import anduin.style.Style
import japgolly.scalajs.react.vdom.html_<^._

object PageField {

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Toc(headings = Source.getTocHeadings)(),
      <.header(
        Style.margin.bottom32,
        Header("Field", obj = Some(Field))()
      ),
      Markdown(
        """
          |
        """.stripMargin
      )(),
      // xxxxxxxxxxxxxxx
      ExampleRich(Source.annotate({
        SimpleState.Str(
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
        SimpleState.Str(
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
        SimpleState.Str(
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