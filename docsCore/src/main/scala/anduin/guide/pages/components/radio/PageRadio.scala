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
          |Radio buttons allow users to choose an option from several. They
          |are usually arranged in groups:
        """.stripMargin
      )(),
      ExampleSimple()({
        def render(value: String, onChange: String => Callback) = {
          <.div(Style.flexbox.flex)(List("Apple", "Orange", "Banana").toVdomArray { fruit =>
            <.div(^.key := fruit, Style.margin.right24)(
              Radio(fruit == "Apple", onChange(fruit))(fruit)
            )
          })
        }
        DemoState.Str(initialValue = "Apple", render = render)()
      }),
      Markdown(
        s"""
           |In each radio group, only one option can be selected at a time. To
           |allow users to select multiple options, use [checkbox] or
           |[multi-dropdown].
           |
           |[checkbox]: ${ctl.urlFor(Pages.Checkbox()).value}
           |[multi-dropdown]: ${ctl.urlFor(Pages.DropdownMulti()).value}
           |
           |In radio groups, all options are always visible. If they can be
           |collapsed, consider [dropdown] to take up less space.
           |
           |[dropdown]: ${ctl.urlFor(Pages.Dropdown()).value}
        """.stripMargin
      )(),
      Markdown(
        """
          |# Value
          |
          |```scala
          |value: String
          |isChecked: Boolean
          |onChange: String => Callback
          |```
          |
          |Radio is a [controlled] component. Its consumers should define
          |whether it is checked or not via the `isChecked` prop, as well as
          |response to users' interactions via the `onChange` prop.
          |
          |In practice, `isChecked` is usually based on a boolean
          |state, which is updated accordingly via `onChange`:
          |
          |[controlled]: https://reactjs.org/docs/forms.html#controlled-components
          |
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        def render(value: String, onChange: String => Callback) = {
          <.div(Style.flexbox.flex)(List("Apple", "Orange", "Banana").toVdomArray { fruit =>
            <.div(^.key := fruit, Style.margin.right24)(
              Radio(
                isChecked = value == fruit,
                onChange = onChange(fruit)
              )(fruit)
            )
          })
        }
        DemoState.Str(initialValue = "Apple", render = render)()
      }))(),
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
          Radio( isChecked = true, onChange = Callback.empty, isDisabled = true )("Checked"),
          <.div(Style.margin.right24),
          Radio( isChecked = false, onChange = Callback.empty, isDisabled = true )("Unchecked"),
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
