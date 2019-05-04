package design.anduin.docs.pages.components.popover

import design.anduin.components.button.Button
import design.anduin.components.dialog.Dialog
import design.anduin.components.icon.Icon
import design.anduin.components.menu.{Menu, MenuItem}
import design.anduin.components.popover.Popover
import design.anduin.components.portal.PortalPosition
import design.anduin.docs.app.main.Pages
import design.anduin.docs.components._
import design.anduin.docs.pages.components.portal.PagePortalPositionPopover
import anduin.mcro.Source
import design.anduin.style.Style
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
                Button.Color.Danger
              ),
              cancel = Some(Dialog.Cancel(close))
            )()
          }
        )()
      }))(),
      Markdown(
        s"""
          |Popovers is a [portal-based][p] component. It requires at least
          |a [target](#target) and a [content](#content) to work:
          |
          |[p]: ${ctl.urlFor(Pages.Portal()).value}
        """.stripMargin
      )(),
      Markdown(
        s"""
           |# Target
           |
           |```scala
           |renderTarget = (Callback, Boolean) => VdomNode
           |// (open, isOpened) => target
           |```
           |
           |`renderTarget` is a [render prop]. It should return the popover's
           |target element, to which the popover will anchor its content.
           |`renderTarget` has and should use the following parameters:
           |
           |- `open: Callback` to open the popover's content
           |- `isOpen: Boolean` to indicate whether the content is open
           |
           |[render prop]: https://reactjs.org/docs/render-props.html
           |
           |In practice, [Buttons] usually make good targets as their
           |`onClick` and `isSelected` props are highly suitable for these
           |parameters:
           |
           |[Buttons]: ${ctl.urlFor(Pages.Button()).value}
           |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Popover(
          renderTarget = (open, isOpen) => {
            val style = Button.Style.Full(isSelected = isOpen)
            Button(style = style, onClick = open)("Target")
          },
          renderContent = _ => <.p(Style.padding.all8, "Content")
        )()
      }))(),
      Markdown(
        s"""
          |For more details and examples, refer to the [Target] section of the
          |Portal guide.
          |
          |[Target]: ${ctl.urlFor(Pages.Portal("#target")).value}
        """.stripMargin
      )(),
      Markdown(
        s"""
          |## Wrapper
          |
          |```scala
          |targetWrapper: PortalWrapper = PortalWrapper.BlockContent
          |```
          |
          |Popovers use a [target wrapper][tw] in order to position their
          |contents properly. By default, the [`BlockContent`] wrapper is
          |used, which can be changed via the `targetWrapper` prop.
          |
          |For detailed usages and examples, refer to the [Target Wrapper][tw]
          |section of the Portal guide.
          |
          |[tw]: ${ctl.urlFor(Pages.Portal("#target-wrapper")).value}
          |[`BlockContent`]: ${ctl.urlFor(Pages.Portal("#blockcontent")).value}
          |
        """.stripMargin
      )(),
      Markdown(
        s"""
           |# Content
           |
           |```scala
           |renderContent = (Callback) => VdomNode
           |// (close) => content
           |```
           |
           |`renderContent` is a [render prop]. It should return the content of
           |a popover. This content will be displayed usually in response to
           |users' interactions with the [target](#target) element. To learn
           |more about portal's content in general, see the [Content] section
           |of the Portal guide.
           |
           |[Content]: ${ctl.urlFor(Pages.Portal("#content")).value}
           |[render prop]: https://reactjs.org/docs/render-props.html
           |
           |Popovers' contents should consist of several elements of one or
           |more types, organized in simple layouts. A common use case is
           |with the [Menu] component:
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
           |Generally, popovers are suitable for intermediate use cases. For
           |more complex ones, consider [Modal]. For single descriptive texts,
           |see [Tooltip].
           |
           |[Tooltip]: ${ctl.urlFor(Pages.Tooltip()).value}
           |[Modal]: ${ctl.urlFor(Pages.Modal()).value}
        """.stripMargin
      )(),
      Markdown(
        """
          |## Closing
          |
          |`renderContent` receives a single parameter, which is a callback to
          |close the popover's content. Consumers can use this to have an
          |explicit method for users to close the content:
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
          |In practice, however, it is often unused as users can also close
          |the content by clicking outside of it or pressing the "Esc" key.
          |These 2 methods are actually more intuitive and well-known to
          |most users.
        """.stripMargin
      )(),
      Markdown(
        s"""
          |## Position
          |
          |```scala
          |position: PortalPosition = PortalPosition.TopCenter,
          |```
          |
          |By default, a popover's content is placed on top of its target,
          |horizontally centered. This can be set otherwise via the
          |`position` prop, using one of the options available at the
          |[`PortalPosition`][pp] object.
          |
          |Please refer to the [Content Position][pp] section of the Portal
          |guide for more details and examples. The below demo simply shows all
          |available positions when using with popovers:
          |
          |[pp]: ${ctl.urlFor(Pages.Portal("#content-position")).value}
          |""".stripMargin
      )(),
      ExampleSimple()(PagePortalPositionPopover()()),
      Markdown(
        s"""
          |## Content-only
          |
          |```scala
          |popover.PopoverContent
          |```
          |
          |The `PopoverContent` component, available at the same package,
          |allows consumers to render a popover's content directly (instead of
          |open it via a callback).
          |
          |To learn more about this type of component, including when and how
          |to use it, see the [Content-only] section of the Portal guide.
          |The below content only describes the props of `PopoverContent`:
          |
          |[Content-only]: ${ctl.urlFor(Pages.Portal("#content-only")).value}
          |
          |### targetRef
          |
          |```scala
          |targetRef: Ref.Simple[raw.HTMLElement]
          |```
          |
          |A [ref] to a DOM element for the content to be anchored to. Note
          |that is simply a reference for informational purpose. This element
          |does not trigger the render of the popover's content at all.
          |
          |[ref]: https://github.com/japgolly/scalajs-react/blob/master/doc/REFS.md
          |
          |### onOverlayClick
          |
          |```scala
          |onOverlayClick: Option[Callback]
          |```
          |
          |Callback to execute when users click on the outside of the content.
          |Note that it is an `Option` because there is a difference between
          |`None` and `Some(Callback.empty)`:
          |- `None` allows users to interact with the main interface behind,
          |while still keeping the content visible. This is the equivalent of
          |a backdrop with `pointer-events: none`.
          |- `Some(Callback.empty)` prevents users from interacting with the
          |main interface behind. In other words, clicks on anywhere outside
          |of the content would result in nothing at all.
          |
          |For reference, the Portal component (the full version with target)
          |provides a `Some(closePopover)` here so that the content would be
          |closed (unmounted) when users click outside of it.
          |
          |### position [position2]
          |
          |```scala
          |position: PortalPosition = PortalPosition.TopCenter
          |```
          |
          |This is similar to the [same name prop](#position) in the full
          |version.
          |
          |### isAutoFocus
          |
          |```scala
          |isAutoFocus: Boolean = true
          |```
          |
          |This defines whether users' focus should be shifted into the popover
          |automatically. Usually this should be `true` so keyboard navigation
          |and "Esc to close" work out of the box. However, this can be
          |disabled when the focus should stay outside of the popover (e.g.
          |Dropdown's target)
          |
        """.stripMargin
      )(),
    )
  }
}
