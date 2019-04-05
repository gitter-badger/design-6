package anduin.guide.pages.components.suggest

import anduin.component.button.Button
import anduin.guide.components._
import anduin.component.input.suggest.Suggest
import anduin.guide.app.main.Pages
import anduin.mcro.Source
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object PageSuggest {

  private case class Contact(name: String, email: String)

  private val SuggestContact = (new Suggest[Contact])()
  private val IntContact = (new Suggest[Int])()

  private val sampleContacts = Suggest.Opts[Contact](
    items = List(
      Contact("Ada Banks", "ada.banks.cceo@gmail.com"),
      Contact("Angelino Sansone", "angelino.sansone.cs@gmail.com"),
      Contact("Daiki Rikuto", "daiki.rikuto.cs@gmail.com"),
      Contact("David Walton", "david.walton.ni@gmail.com"),
      Contact("Eric Miller", "eric.miller.ni@gmail.com"),
      Contact("Harry Williams", "hary.williams.ic@gmail.com"),
      Contact("Jenny Yoo", "jenny.yoo.bm@gmail.com"),
      Contact("John Mcregor", "john.mcregor.icfo@gmail.com"),
      Contact("Jonathin Sundin", "jonathin.sundin.bm@gmail.com"),
      Contact("Logan Smith", "logan.smith.igp@gmail.com"),
      Contact("Vincent Harris", "vincent.harris.ccfo@gmail.com"),
      Contact("Virginia Lee", "virginia.lee.cc@gmail.com")
    ).map(c => Suggest.Opt(c)),
    getNode = c => s"${c.name} (${c.email})",
    getFilterString = c => s"${c.name} + ${c.email}",
    getSubmitString = _.email
  )

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Suggest", Some(Suggest))(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        """
          |
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.Str(
          initialValue = "",
          render = (value, onChange) => {
            val input = SuggestContact(value, onChange, sampleContacts, None)()
            val help = <.p("Basic input test")
            val current = <.p(
              s"Current input is: ($value). You can ",
              Button(style = Button.Style.Text(), onClick = onChange(""))("reset"),
              " it to empty."
            )
            <.div(help, current, input)
          }
        )()
      }))(),
      ExampleRich(Source.annotate({
        DemoState.SeqStr(
          initialValue = List.empty[String],
          render = (strings, setStrings) => {
            DemoState.Str(
              initialValue = "",
              render = (value, onChange) => {
                val selection = Suggest.Selection(
                  triggerKeys = List(",", ";", "Enter"),
                  onSelect = s => {
                    for {
                      _ <- if (strings.contains(s)) {
                        setStrings(strings.filter(_ != s))
                      } else {
                        Callback.when(s != "")(setStrings(strings :+ s))
                      }
                      _ <- onChange("")
                    } yield ()
                  },
                  selected = strings
                )
                val input = SuggestContact(value, onChange, sampleContacts, Some(selection))()
                val help = <.p("Selection test")
                val current = <.p(
                  s"Current input is: ($value). You can ",
                  Button(style = Button.Style.Text(), onClick = onChange(""))("reset"),
                  " it to empty."
                )
                val selected = <.div(
                  "Selected strings are: [",
                  strings.mkString(", "),
                  "]. You can ",
                  Button(style = Button.Style.Text(), onClick = setStrings(List.empty[String]))("reset"),
                  " it to empty."
                )
                <.div(help, current, selected, input)
              }
            )()
          }
        )()
      }))(),
      ExampleRich(Source.annotate({
        DemoState.Str(
          initialValue = "",
          render = (value, onChange) => {
            val opts = Suggest.Opts[Int](
              items = 0.to(10000).map(i => Suggest.Opt(i)),
              getNode = _.toString,
              getFilterString = _.toString,
              getSubmitString = _.toString
            )
            val input = IntContact(value, onChange, opts)()
            val help = <.p(
              s"Performance test. Current input is: $value. You can ",
              Button(style = Button.Style.Text(), onClick = onChange(""))("reset"),
              " it to empty."
            )
            <.div(input, help)
          }
        )()
      }))()
    )
  }
}
