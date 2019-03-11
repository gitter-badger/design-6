package anduin.guide.pages.components.dialog

import anduin.component.button.Button
import anduin.component.dialog.Dialog
import anduin.guide.app.main.Pages
import anduin.guide.components._
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
            Button.Color.Red
          ),
          cancel = Some(Dialog.Cancel(Callback.empty))
        )()
      }))()
    )
  }
}
