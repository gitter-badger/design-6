package design.anduin.docs.pages.components.button

import design.anduin.components.button.Button
import design.anduin.components.icon.Icon
import design.anduin.components.input.textbox.TextBox
import design.anduin.docs.app.main.Pages
import design.anduin.docs.components._
import design.anduin.macros.Source
import design.anduin.style.Style
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object PageButton {

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Button", Some(Button))(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        """
          |Buttons let users take action:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Button(
          onClick = Callback.alert("Hello")
        )("Say Hi")
      }))(),
      Markdown(
        """
          |# Event Listener
          |
          |```scala
          |onClick: Callback = Callback.empty
          |onDoubleClick: Callback = Callback.empty
          |```
          |
          |Buttons can have event listeners attached in response to users'
          |interactions, such as click or double-click:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Button(
          onDoubleClick = Callback.alert("Hello")
        )("Say Hi")
      }))(),
      Markdown(
        """
          |Although supported by all types, event listeners are usually
          |defined for [Plain](#plain) buttons only. Other types like
          |[Submit](#submit) and [Link](#link) already have useful behaviours
          |out of the box.
          |
          |# Disable
          |
          |```scala
          |isDisabled: Boolean = false
          |```
          |
          |Set `isDisabled = true` to prevent users' interactions with the
          |button. This disables both event listeners and built-in behaviours
          |(eg. in case of [Submit](#submit) buttons):
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Button(
          onClick = Callback.alert("Hello"),
          isDisabled = true
        )("Say Hi")
      }))(),
      Markdown(
        """
          |When disabled, the button's appearance is grayed out:
          |""".stripMargin
      )(),
      ExampleSimple()({
        <.div(
          Style.flexbox.flex.flexbox.itemsCenter,
          <.div(Style.margin.right16, Button(isDisabled = true)("Full")),
          <.div(Style.margin.right16, Button(style = Button.Style.Ghost(), isDisabled = true)("Ghost")),
          <.div(Style.margin.right16, Button(style = Button.Style.Minimal(), isDisabled = true)("Minimal")),
          <.div(Style.margin.right16, Button(style = Button.Style.Text(), isDisabled = true)("Text"))
        )
      }),
      Markdown(
        """
          |# Style
          |
          |```scala
          |style: ButtonStyle = Button.Style.Full()
          |```
          |
          |The `style` prop defines how a button looks. There are 4 styles,
          |with further customization (like color) in each:
          |
          |## Box
          |""".stripMargin
      )(),
      Markdown(
        """
          |`Style.Full`, `Style.Ghost` and `Style.Minimal` provide box-like
          |looks for a button by having a padding around its label:
          |""".stripMargin
      )(),
      ExampleSimple()({
        import Button.Color._
        import Button.Style._
        val margin = <.div(Style.margin.right12)
        val icon = Some(Icon.Glyph.Download)
        val border = Style.margin.bottom16.padding.bottom16.border.bottom.borderColor.gray3
        val label = <.p(Style.flexbox.none.width.px64.margin.right24.fontWeight.semiBold)
        <.div(
          <.div(
            Style.flexbox.flex.flexbox.itemsCenter,
            border,
            label("Full"),
            margin(Button(style = Full(color = Primary))("Submit")),
            margin(Button(style = Full(color = Gray0))("Cancel")),
            margin(Button(style = Full(color = Gray0, icon = icon))()),
          ),
          <.div(
            Style.flexbox.flex.flexbox.itemsCenter,
            border,
            label("Ghost"),
            margin(Button(style = Ghost(color = Primary))("Submit")),
            margin(Button(style = Ghost(color = Gray9))("Cancel")),
            margin(Button(style = Ghost(color = Gray9, icon = icon))()),
          ),
          <.div(
            Style.flexbox.flex.flexbox.itemsCenter,
            label("Minimal"),
            margin(Button(style = Minimal(color = Primary))("Submit")),
            margin(Button(style = Minimal(color = Gray9))("Cancel")),
            margin(Button(style = Minimal(color = Gray9, icon = icon))()),
          ),
        )
      }),
      Markdown(
        """
          |These buttons are usually placed in their own areas (like in a
          |Toolbar or Modal's footer). Learn more in their guide:
          |""".stripMargin
      )(),
      BigButtonLink(ctl, Pages.ButtonBox(), "View Box Button guide")(),
      Markdown(
        """
          |## Text
          |
          |`Style.Text` provides a text-only look, similar to [HTML's `<a>`
          |element][a]. These buttons are usually parts of sentences, such as
          |words or phrases:
          |
          |[a]: https://developer.mozilla.org/en-US/docs/Web/HTML/Element/a
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        <.p(
          "You can ",
          Button(style = Button.Style.Text())("Accept"),
          " or ",
          Button(style = Button.Style.Text(color = Button.Color.Danger))("Decline"),
          " this invitation."
        )
      }))(),
      Markdown(
        """
          |Their color can be changed via the `color` parameter, with
          |`Button.Color.Primary` is the default one:
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        <.p(
          Button(style = Button.Style.Text())("Primary (Default)"),
          <.span(", "),
          Button(style = Button.Style.Text(color = Button.Color.Danger))("Danger"),
          <.span(", "),
          Button(style = Button.Style.Text(color = Button.Color.Gray9))("Gray9"),
        )
      }))(),
      Markdown(
        """
          |# Type
          |
          |```scala
          |tpe: ButtonType = Button.Tpe.Plain(),
          |```
          |
          |The `tpe` prop defines the built-in behaviours of a button. There
          |are 3 options:
          |
          |## Plain
          |
          |```scala
          |case class Button.Tpe.Plain(
          |  isAutoFocus: Boolean = false
          |)
          |```
          |
          |The default `Tpe.Plain` has no built-in behaviour. It is similar to
          |HTML's `<button type="button"/>`. These Buttons usually have [event
          |listeners](#event-listener) attached to make them useful:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Button(
          onClick = Callback.alert("Hello")
        )("Say Hi")
      }))(),
      Markdown(
        """
          |## Submit
          |
          |```scala
          |case class Button.Tpe.Submit(...)
          |```
          |
          |`Tpe.Submit` automatically triggers the `onSubmit` handler of the
          |associated [form] when users click on the button. This is a native,
          |built-in behaviour so no `onClick` needs to be defined:
          |
          |[form]: https://developer.mozilla.org/en-US/docs/Web/HTML/Element/form
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.Str(
          initialValue = "",
          render = (value, setValue) => {
            <.form(
              Style.flexbox.flex,
              ^.onSubmit ==> (_.preventDefaultCB >> Callback.alert(value)),
              <.div(Style.width.px128, TextBox(value, setValue, placeholder = "Name...")()),
              <.div(Style.margin.left8, Button(tpe = Button.Tpe.Submit())("Submit"))
            )
          }
        )()
      }))(),
      Markdown(
        """
          |In general, prefer form's `onSubmit` to button's `onClick` when
          |for submit handlers because the former allows users to submit
          |via other ways, such as pressing "Enter" at an input.
          |
          |## Link
          |
          |```scala
          |case class Button.Tpe.Link(
          |  href: String,
          |  target: ButtonType.Target = Target.Self
          |)
          |```
          |
          |`Tpe.Link` renders the button as an [HTML `<a>` element][a]. It
          |should be used when the button's target is a page, location
          |on a page, file, email address, or other URL:
          |
          |[a]: https://developer.mozilla.org/en-US/docs/Web/HTML/Element/a
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Button(
          tpe = Button.Tpe.Link("https://anduintransact.com")
        )("Go to Anduin Transact website")
      }))(),
      Markdown(
        """
          |Similar to `<a>` elements, `Tpe.Link` accepts a `target` parameter
          |to specify where to display the linked URL:
          |- `Target.Self`, which use the same browsing context, like the
          |current tab.
          |- `Target.Blank`, which create a new browsing context, like a new
          |tab or window.
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        Button(
          tpe = Button.Tpe.Link(
            href = "https://anduintransact.com",
            target = Button.Target.Blank
          )
        )("Open Anduin Transact website in new tab")
      }))(),
      Markdown(
        """
          |`Target.Self` is the default value. Before switching to `Blank`,
          |make sure you have [good reasons] to do so.
          |
          |[good reasons]: https://css-tricks.com/use-target_blank/
        """.stripMargin
      )()
    )
  }
}
