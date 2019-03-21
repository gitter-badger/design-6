package anduin.guide.pages.components.textbox

import anduin.component.field.Field
import anduin.guide.components._
import anduin.component.input.textbox.{TextBox, TextBoxType}
import anduin.guide.app.main.Pages
import anduin.mcro.Source
import anduin.scalajs.textmask.TextMask
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
          |# Type
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        import TextBox.Type
        val d = TextMask.RegExp("\\d")
        val types = List[(TextBoxType, String)](
          (Type.Password, ""),
          (Type.Text, ""),
          (Type.EmailNative, ""),
          (Type.EmailMask, ""),
          (Type.DateNative, ""),
          (Type.DateMask, ""),
          (Type.Currency, ""),
          (Type.Percentage, ""),
          (Type.NumberInt, ""),
          (Type.NumberFloat, ""),
          (Type.Array(List(d, d, d, d)), "4 digits"),
          (Type.Func(s => List.fill(s.length)(d)), "n digits"),
          (Type.Area(3), "")
        )
        val layout = Field.Layout.Hor(right = Field.Layout.Grow(2))
        val nodes = types.toVdomArray { `type` =>
          val id = `type`._1.getClass.getSimpleName
          <.div(^.key := id, Style.margin.bottom16)(
            DemoState.Str("", (value, onChange) => {
              val label = Some(s"$id ($value)")
              Field(layout, id, label)({
                TextBox(value, onChange, `type`._1, `type`._2)()
              })
            })()
          )
        }
        <.div(nodes)
      }))()
    )
  }
}
