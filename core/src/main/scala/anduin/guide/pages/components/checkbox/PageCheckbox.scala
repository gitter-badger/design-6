package anduin.guide.pages.components.checkbox

import anduin.guide.components._
import anduin.component.input.checkbox.Checkbox
import anduin.guide.app.main.Pages
import anduin.mcro.Source
import japgolly.scalajs.react.vdom.html_<^._

import anduin.style.Style

object PageCheckbox {

  private val sampleAgreement: String =
    """
      |By accessing or using any web site or service made available
      |by Anduin Transactions, you acknowledge that you have read
      |and agree to be bound by these terms of service and agree to
      |comply with all applicable laws and regulations, including
      |US state and federal securities laws and regulations.
    """.stripMargin

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Checkbox", Some(Checkbox))(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        """
          |A checkbox allows users to mark an option as selected (checked) or
          |not (unchecked):
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.Bool(initialValue = false, render = (value, onChange) => {
          Checkbox(
            isChecked = value,
            onChange = onChange
          )("Remember me")
        })()
      }))(),
      ChecklistExample(
        items = List(("Tomato", true), ("Orange", false), ("Apple", false)),
        parent = Some("Fruit")
      )(),
      Markdown(
        """
          |# Value
          |
          |```scala
          |isChecked: Boolean = false
          |onChange: Boolean => Callback = _ => Callback.empty
          |```
          |
          |```scala
          |// case class State(isFoo: Boolean, ...)
          |
          |Checkbox(
          |  value = state.isFoo,
          |  onChange = isChecked => {
          |    scope.modState(_.copy(isFoo = isChecked)
          |  }
          |)("Foo")
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
          <.div(Style.margin.right24),
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
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.Bool(initialValue = false, render = (value, onChange) => {
          Checkbox(
            isChecked = value,
            onChange = onChange,
            isIndeterminate = true
          )(s"Actual value: $value")
        })()
      }))(),
      ChecklistExample(
        items = List(
          ("Term Sheet", true),
          ("Restated Certificate of Incorporation", false),
          ("Preferred Stock Investment Agreement", false)
        ),
        parent = Some("Legal documents")
      )(),
      Markdown(
        """
          |# Text Wrap
        """.stripMargin
      )(),
      ExampleSimple()({
        DemoState.Bool(initialValue = false, render = (value, onChange) => {
          Checkbox(
            isChecked = value,
            onChange = onChange
          )(sampleAgreement)
        })()
      }),
      ExampleSimple()({
        DemoState.Bool(initialValue = false, render = (value, onChange) => {
          <.div(
            <.p(Style.margin.bottom8, sampleAgreement),
            Checkbox(isChecked = value, onChange = onChange)("I agree")
          )
        })()
      }),
    )
  }
}
