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
          |Checkboxes allow users to mark options as selected (checked) or
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
      Markdown(
        """
          |Checkboxes are often used as a list, or even nested lists, to
          |allows users to select no, one or several options:
        """.stripMargin
      )(),
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
          |Checkbox is a [stateless] component. Its consumers should define
          |whether it is checked or not via the `isChecked` prop, as well as
          |response to users' interactions via the `onChange` prop.
          |
          |In practice, `isChecked` is usually based on a boolean
          |state, which is updated accordingly via `onChange`:
          |
          |[stateless]: https://reactjs.org/docs/forms.html#controlled-components
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
          |Set `isDisabled = true` to disable users' interaction with a
          |checkbox. Disabled checkboxes can still reflect whether they are
          |checked or not:
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
        s"""
          |However, avoid using disabled checkboxes for pure informational
          |purpose. To show information without interaction, use [icons].
          |
          |[icons]: ${ctl.urlFor(Pages.IconGlyph("#actions")).value}
        """.stripMargin
      )(),
      Markdown(
        """
          |# Indeterminate
          |
          |```scala
          |isIndeterminate: Boolean = false
          |```
          |
          |A checkbox can be displayed as indeterminate:
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
      Markdown(
        """
          |As shown in the above example, "indeterminate" is **not an
          |additional state** to checked and unchecked. It is only a visual
          |feature that effectively hides the actual `isChecked` value.
          |
          |Because of this, the value of these checkboxes are usually not
          |collected (for submission). They only work as parents of
          |other checkboxes, allow users to check or uncheck all of them at
          |once:
        """.stripMargin
      )(),
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
          |Since checkbox is a stateless component, its consumers are
          |responsible for controlling the `isIndeterminate` prop (as well as
          |`isChecked` and `onChange`) properly if used.
          |
          |The below example should give you a general idea of controlling a
          |checkbox that can be indeterminate:
          |
          |```scala
          |// class Item(isSelected: Boolean)
          |// class State(items: Seq[Item])
          |isChecked = state.items.forall(_.isSelected)
          |isIndeterminate = state.items.exists(item => {
          |  state.items.exists(_.isSelected != item.isSelected)
          |})
          |onChange = isChecked => scope.modState { state =>
          |  val items = state.items.map(_.copy(isSelected = isChecked))
          |  state.copy(items = items)
          |}
          |```
          |
        """.stripMargin
      )(),
      Markdown(
        """
          |# Text Wrap
          |
          |When there is not enough space, a checkbox's label is wrapped onto
          |multiple lines, while the checkbox itself stays at top left:
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
      Markdown(
        """
          |However, it's generally better to keep your checkboxes' labels short
          |and simple. Additional information can then be separated to a
          |paragraph above or below it:
        """.stripMargin
      )(),
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
