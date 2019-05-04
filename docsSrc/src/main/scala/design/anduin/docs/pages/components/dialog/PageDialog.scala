package design.anduin.docs.pages.components.dialog

import design.anduin.components.button.Button
import design.anduin.components.dialog.Dialog
import design.anduin.docs.app.main.Pages
import design.anduin.docs.components._
import anduin.mcro.Source
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object PageDialog {

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Dialog", Some(Dialog))(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        """
          |Dialogs give users messages with optional actions:
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        Dialog(
          message = Dialog.Message(
            "Are you sure want to delete this file?",
            Some("You won't be able to recover it later")
          ),
          submit = Dialog.Submit(
            Callback.alert("Deleted"),
            "Delete",
            Button.Color.Danger
          ),
          cancel = Some(Dialog.Cancel(Callback.empty))
        )()
      }))()
    )
  }
}
