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
          |tpe: TextBoxTpe = TextBox.TpeText
          |```
          |""".stripMargin
      )(),
    )
  }
}
