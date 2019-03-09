package anduin.guide.pages.components.popover

import anduin.component.button.Button
import anduin.component.dialog.Dialog
import anduin.component.icon.Icon
import anduin.component.input.textbox.TextBox
import anduin.component.menu.{Menu, MenuItem}
import anduin.component.popover.{Popover, PopoverContent, PopoverPosition}
import anduin.guide.app.main.Pages
import anduin.guide.components._
import anduin.mcro.Source
import anduin.style.Style
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.raw.HTMLElement

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
        """
          |## Inline
          |
          |```scala
          |isInline: Boolean = false
          |```
          |
          |In order to get [accurate measurement][m] of [targets](#target),
          |popovers will wrap them inside a `<.div(Style.width.maxContent)`.
          |This means the rendered results of popovers will always be
          |[block-level][b] elements.
          |
          |[m]: https://developer.mozilla.org/en-US/docs/Web/CSS/width#max-content
          |[b]: https://developer.mozilla.org/en-US/docs/Web/HTML/Block-level_elements
          |
          |There are a few cases where these block-level elements will not
          |work, such as in the middle of sentences. In these cases, set
          |`isInline = true` to have [inline][i] results instead:
          |
          |[i]: https://developer.mozilla.org/en-US/docs/Web/HTML/Inline_elements
          |
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        <.p(
          "A sentence with an ",
          Popover(
            isInline = true,
            renderTarget = (open, _) => Button(Button.Style.Text(), onClick = open)("inline"),
            renderContent = _ => <.div(Style.padding.all8, "Content")
          )(),
          " popover."
        )
      }))(),
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
          position = PopoverPosition.BottomLeft
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
      )(),
      Markdown(
        """
          |## Position
          |
          |```scala
          |position: PopoverPosition = PopoverPosition.TopCenter
          |```
          |
          |A popover's content is always anchored to its [target](#Target).
          |By default, it is placed on top of the target, horizontally
          |centered. The `position` prop can be used to set otherwise:
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        Popover(
          position = PopoverPosition.RightTop,
          renderTarget = (toggle, isOpened) => {
            val style = Button.Style.Full(isSelected = isOpened)
            Button(style, onClick = toggle)("Target")
          },
          renderContent = _ => <.p(Style.padding.all8, "Content")
        )()
      }))(),
      Markdown(
        """
          |There are options to place the content on top, right, bottom or
          |left of the target, with 3 additional variants each for fine tuning:
        """.stripMargin
      )(),
      ExampleSimple()(PagePopoverPosition()()),
      Markdown(
        """
          |## Standalone
          |
          |The `PopoverContent` component, available at the same package,
          |allows render the popover's content directly, without a target
          |element or any user interaction. It has the following signature:
          |
          |```scala
          |PopoverContent(
          |  // To where the content should be anchored to
          |  targetRef: Ref.Simple[raw.HTMLElement],
          |  // Callback when users click on outside:
          |  // - None works like overlay.pointerEvents = none
          |  // - Some(Callback.empty) works like overlay.pointerEvents = auto
          |  onOverlayClick: Option[Callback],
          |  // Same as Popover's `position` prop
          |  position: PopoverPosition = PopoverPosition.TopCenter,
          |  // Whether users' focus should be shifted into the popover automatically
          |  // - Usually this should be true so keyboard navigation and "Esc to close"
          |  //   would work out of the box
          |  // - However, this should be turned off when the focus should stay OUTSIDE
          |  //   of the popover (e.g. Dropdown's target)
          |  isAutoFocus: Boolean = true
          |)(children: VdomNode*)
          |```
          |
          |`PopoverContent` renders its children in the exact same way of
          |of `Popover`. The only difference is that there is no longer any
          |state management, so the popover's content will always be visible
          |as long as `PopoverContent` is rendered.
          |
          |[properly]: https://reactjs.org/docs/portals.html
          |
          |This could be useful when you want to open a popover based on some
          |complex condition instead of users' interaction. Below is a simple
          |example in which a popover will be shown whenever the text box is
          |empty:
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        // This ref should be defined at your component's Backend
        val ref = Ref[HTMLElement]

        DemoState.Str(
          initialValue = "Clear me!",
          render = (value, onChange) => {
            <.div.withRef(ref)(Style.width.px128)(
              TextBox(value, onChange)(),
              TagMod.when(value == "") {
                PopoverContent(
                  targetRef = ref,
                  onOverlayClick = None,
                  isAutoFocus = false,
                  position = PopoverPosition.BottomLeft
                )({
                  val padding = Style.padding.ver8.padding.hor12
                  <.p(padding, "Type something to close this Popover")
                })
              }
            )
          }
        )()
      }))()
    )
  }
}
