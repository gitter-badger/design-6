package anduin.guide.pages.components.checkbox

import anduin.guide.components._
import anduin.component.input.checkbox.Checkbox
import anduin.guide.app.main.Pages
import anduin.mcro.Source
import japgolly.scalajs.react.vdom.html_<^._

import anduin.style.Style

object PageCheckbox {
  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Checkbox", Some(Checkbox))(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        """
          |Checkboxes allow users to toggle between checked and unchecked
          |states:
          |x
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.Bool(
          initialValue = false,
          render = (value, onChange) => {
            Checkbox(
              isChecked = value,
              onChange = onChange
            )("Remember me")
          }
        )()
      }))(),
      Markdown(
        """
          |# Controlled
          |
          |```scala
          |isChecked: Boolean = false
          |onChange: Boolean => Callback = _ => Callback.empty
          |```
        """.stripMargin
      )(),
      Markdown(
        """
          |# Disabled
          |
          |```scala
          |isDisabled: Boolean = false
          |```
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        <.div(
          Style.flexbox.flex,
          Checkbox(isChecked = true, isDisabled = true)("Checked"),
          Checkbox(isChecked = false, isDisabled = true)("Unchecked")
        )
      }))(),
      Markdown(
        """
          |# Indeterminate
          |
          |```scala
          |isIndeterminate: Boolean = false
          |```
          |xxxxxxxx
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.Bool(
          initialValue = false,
          render = (isChecked, setIsChecked) => {
            Checkbox(
              isChecked = isChecked,
              onChange = setIsChecked,
              isIndeterminate = true
            )(s"Actual value: $isChecked")
          }
        )()
      }))(),
      Markdown(
        """
          |# Text Wrap
        """.stripMargin
      )(),
      ExampleSimple()({
        DemoState.Bool(
          initialValue = false,
          render = (value, onChange) => {
            Checkbox(
              isChecked = value,
              onChange = onChange
            )("""
                |At the end of the day, you are solely responsible for your
                |success and your failure. And the sooner you realize that, you
                |accept that, and integrate that into your work ethic, you will
                |start being successful. As long as you blame others for the
                |reason you aren't where you want to be, you will always be a
                |failure. Erin Cummings
              """.stripMargin)
          }
        )()
      })
    )
  }
}
