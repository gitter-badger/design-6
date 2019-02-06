package anduin.guide.pages.components.radio

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.component.input.radio.Radio
import anduin.guide.app.main.Pages
import anduin.guide.components._
import anduin.mcro.Source
import anduin.style.Style

object PageRadio {
  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Radio", Some(Radio))(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        """
          |
          |xxxxxxx
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        def render(value: String, onChange: String => Callback) = {
          <.div(Style.flexbox.flex)(List("Apple", "Orange", "Banana").toVdomArray { fruit =>
            <.div(^.key := fruit, Style.margin.right24)(
              Radio(
                name = "fruit",
                value = fruit,
                isChecked = value == fruit,
                onChange = onChange
              )(fruit)
            )
          })
        }
        DemoState.Str(initialValue = "Apple", render = render)()
      }))(),
      Markdown(
        """
          |# Name & Value
        """.stripMargin
      )(),
      Markdown(
        """
          |# Disabled
          |
          |```scala
          |isDisabled: Boolean = false
          |```
          |Set `isDisabled = true` to disable users' interaction with a
          |radio. Disabled radios can still reflect whether they are
          |checked or not:
        """.stripMargin
      )(),
      // format: off
      ExampleRich(Source.annotate({
        <.div(
          Style.flexbox.flex,
          Radio(
            name = "disable", value = "checked", onChange = _ => Callback.empty,
            isChecked = true, isDisabled = true,
          )("Checked"),
          <.div(Style.margin.right24),
          Radio(
            name = "disable", value = "unchecked", onChange = _ => Callback.empty,
            isChecked = false, isDisabled = true,
          )("Unchecked")
        )
      }))(),
      // format: on
      Markdown(
        s"""
           |However, avoid using disabled checkboxes for pure informational
           |purpose. To show information without interaction, use [icons].
           |
           |[icons]: ${ctl.urlFor(Pages.IconGlyph("#actions")).value}
        """.stripMargin
      )()
    )
  }
}
