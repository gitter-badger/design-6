package anduin.guide.pages.components.popover

import anduin.component.button.Button
import anduin.component.dialog.Dialog
import anduin.component.icon.Icon
import anduin.component.menu.{Menu, MenuItem}
import anduin.component.popover.Popover
import anduin.component.portal.PortalPosition
import anduin.guide.app.main.Pages
import anduin.guide.components._
import anduin.mcro.Source
import anduin.style.Style
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object PagePopover {

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Popover", Some(Popover))(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        """
          |Popovers display floating contents anchored to a target element:
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        Popover(
          renderTarget = (open, isOpened) => {
            val style = Button.Style.Full(isSelected = isOpened)
            Button(style, onClick = open)("Delete")
          },
          renderContent = close => {
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
              cancel = Some(Dialog.Cancel(close))
            )()
          }
        )()
      }))(),
      Markdown(
        """
          |A popover requires 2 things to be defined: the [content](#content)
          |itself, and a [target](#target) where the content will be anchored
          |to.
        """.stripMargin
      )(),
      Markdown(
        s"""
           |# Target
           |
           |```scala
           |// (open, isOpened) => target
           |renderTarget = (Callback, Boolean) => VdomNode
           |```
           |
           |`renderTarget` is a [render prop]. It should return the popover's
           |target element, to which the popover will anchor its content.
           |`renderTarget` has and should use the following parameters:
           |
           |- `open: Callback` to open the popover's content
           |- `isOpened: Boolean` to indicate whether the content is being
           |opened
           |
           |[render prop]: https://reactjs.org/docs/render-props.html
           |
           |Because of this, [Buttons] and their [`isSelected`] prop usually
           |make good targets:
           |
           |[Buttons]: ${ctl.urlFor(Pages.Button()).value}
           |[`isSelected`]: ${ctl.urlFor(Pages.Button("#selected")).value}
           |
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        Popover(
          renderTarget = (open, isOpened) => {
            val style = Button.Style.Full(isSelected = isOpened)
            Button(style = style, onClick = open)("Target")
          },
          renderContent = _ => <.p(Style.padding.all8, "Content")
        )()
      }))(),
      Markdown(
        """
          |In general, popovers' contents should only be opened in response
          |to users' interaction to avoid unexpected distraction. For cases
          |where the target element (or more specific, the interaction with
          |it) seems to be unnecessary, see the [Standalone](#standalone)
          |section below.
        """.stripMargin
      )(),
      Markdown(
        s"""
           |# Content
           |
           |```scala
           |// (close) => content
           |renderContent = (Callback) => VdomNode
           |```
           |
           |`renderContent` is a [render prop]. It should return the content of
           |a popover. This content will be displayed usually in response to
           |users' interaction with the [target](#target) element.
           |
           |[render prop]: https://reactjs.org/docs/render-props.html
           |
           |Popovers' contents should consist of several elements of one or
           |more types, organized in simple layouts. A good example is the
           |[Menu] component:
           |
           |[Menu]: ${ctl.urlFor(Pages.Menu()).value}
           |
           |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Popover(
          renderTarget = (open, isOpened) => {
            val icon = Some(Icon.Glyph.EllipsisHorizontal)
            val style = Button.Style.Full(isSelected = isOpened, icon = icon)
            Button(style, onClick = open)()
          },
          renderContent = _ => {
            Menu()(
              MenuItem(icon = Some(Icon.Glyph.Eye))("Preview"),
              MenuItem(icon = Some(Icon.Glyph.Download))("Download")
            )
          },
          position = PortalPosition.BottomLeft
        )()
      }))(),
      Markdown(
        s"""
           |In general, popovers are good for simple to intermediate use cases.
           |For more complex ones, consider [Modal]. For single descriptive
           |texts, see [Tooltip].
           |
           |[Tooltip]: ${ctl.urlFor(Pages.Tooltip()).value}
           |[Modal]: ${ctl.urlFor(Pages.Modal()).value}
        """.stripMargin
      )(),
      Markdown(
        """
          |## Closing
          |
          |`renderContent` receives a callback to close the popover's content
          |as its parameter. Consumers can use this to create an explicit
          |method for users to close the content:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Popover(
          renderTarget = (open, isOpened) => {
            val style = Button.Style.Full(isSelected = isOpened)
            Button(style, onClick = open)("Target")
          },
          renderContent = close => {
            val button = Button(onClick = close)("Close me")
            <.div(Style.padding.all16, button)
          }
        )()
      }))(),
      Markdown(
        """
          |Besides, users can also close the content by clicking outside of it
          |or pressing the "Esc" key. In fact, these two methods are quite
          |standard and well-known to most users, causing the close callback
          |to be unnecessary sometimes.
        """.stripMargin
      )()
    )
  }
}
