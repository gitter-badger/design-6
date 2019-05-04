package design.anduin.docs.pages.components.textbox

import design.anduin.docs.components._
import design.anduin.components.input.textbox.TextBox
import design.anduin.docs.app.main.Pages
import anduin.mcro.Source
import japgolly.scalajs.react.vdom.html_<^._

import design.anduin.components.icon.Icon
import design.anduin.style.Style

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
