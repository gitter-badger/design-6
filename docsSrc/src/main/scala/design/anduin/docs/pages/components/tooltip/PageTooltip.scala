package design.anduin.docs.pages.components.tooltip

import design.anduin.components.button.Button
import design.anduin.components.dialog.Dialog
import design.anduin.components.icon.Icon
import design.anduin.components.popover.Popover
import design.anduin.components.tag.Tag
import design.anduin.components.tooltip.Tooltip
import design.anduin.docs.app.main.Pages
import design.anduin.docs.components._
import design.anduin.docs.pages.components.portal.PagePortalPositionTooltip
import anduin.mcro.Source
import design.anduin.style.Style
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object PageTooltip {

  private def renderDeleteDialog(close: Callback) =
    Dialog(
      message = Dialog.Message(
        "Are you sure want to delete this file?",
        Some("You won't be able to recover it later")
      ),
      submit = Dialog.Submit(Callback.alert("Deleted"), "Delete", Button.Color.Danger),
      cancel = Some(Dialog.Cancel(close))
    )()

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Tooltip", Some(Tooltip))(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        """
          |Tooltips display short, informative text when users hover over or
          |focus on a target element:
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        Tooltip(
          renderTarget = {
            val icon = Some(Icon.Glyph.Bold)
            Button(style = Button.Style.Full(icon = icon))()
          },
          renderContent = () => "Bold (Ctrl + B)"
        )()
      }))(),
      Markdown(
        s"""
           |Tooltips is a [portal-based][p] component. It requires at least
           |a [target](#target) and a [content](#content) to work.
           |
           |[p]: ${ctl.urlFor(Pages.Portal()).value}
        """.stripMargin
      )(),
      Markdown(
        s"""
           |# Usage
           |
           |> Many of today’s use cases for tooltips could be omitted if
           |> people followed other design guidelines (for example, labeling
           |> icons).
           |>
           |> [Alita Joyce] in Nielsen Norman Group's [Tooltip Guidelines]
           |
           |[Alita Joyce]: https://www.nngroup.com/articles/author/alita-joyce/
           |[Tooltip Guidelines]: https://www.nngroup.com/articles/tooltip-guidelines/
           |
           |Tooltips should never be your first approach to a UI problem. They
           |can easily do more harm than good to the users' experience if
           |not careful. Think of them as fail-safe solutions, and use them
           |sparingly, or not at all.
           |
           |Before using a tooltip to show an information, ask yourself if this
           |information is necessary for the users to achieve their goal or
           |not:
           |- If it is, then don't use tooltips. Tooltips require users'
           |interactions to show the information, and usually there is little
           |to no visual hint whether a element has a tooltip attached or not.
           |- It it is not, then why you need to show it at all? Be aware of
           |[information pollution].
           |
           |[information pollution]: https://www.nngroup.com/articles/information-pollution/
           |
           |In fact, a main point of this page is to show common issues of
           |tooltips and better alternatives for these cases. Learn more in the
           |[Target Accessibility](#target-a11y),
           |[Content Accessibility](#content-a11y) and
           |[Common Use Cases](#common-use-cases) section.
           |""".stripMargin
      )(),
      Markdown(
        s"""
           |# Target
           |
           |```scala
           |renderTarget = VdomNode
           |```
           |
           |The `renderTarget` prop defines a tooltip's target element, to
           |which it will anchor the content to. Technically, it can be any
           |[render-able][r] item, like a native tag (e.g. `<.div`), a custom
           |component (`Button()`) or even a string (`"Foo"`).
           |
           |[r]: https://reactjs.org/docs/react-component.html#render
           |
           |This is because the Tooltip component will wrap its target inside
           |a [wrapper](#target-wrapper) tag anyway. It will also attach
           |necessary event handlers to this tag out of the box (e.g. to
           |open tooltip on mouse over). The consumers don't really need to do
           |any integration:
           |
           |[t]: ${ctl.urlFor(Pages.Portal("#target")).value}
           |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Tooltip(
          renderTarget = <.span(^.tabIndex := 0, "Target"),
          renderContent = () => "Content"
        )()
      }))(),
      Markdown(
        s"""
           |To learn more about target elements in general, see the [Target]
           |section of the Portal guide.
           |
           |[Target]: ${ctl.urlFor(Pages.Portal("#target")).value}
        """.stripMargin
      )(),
      Markdown(
        """
          |## Common use cases
          |
          |In practice, tooltips are usually used with the following targets.
          |This section describes their issues and potential better
          |alternatives.
          |
          |### Icon
          |
          |["Most icons have some level of ambiguity"][icon], so tooltips are
          |often used to provide labels for them. This is especially common
          |in case of toolbars, where several similar icons are placed next
          |to each other:
          |
          |[icon]: https://www.nngroup.com/articles/icon-usability/
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        def button(name: Icon.Name, text: String) = {
          val style = Button.Style.Minimal(icon = Some(name))
          Tooltip(Button(style = style)(), () => text)()
        }
        <.div(
          Style.flexbox.flex,
          button(Icon.Glyph.AlignLeft, "Left align (Ctrl + Shift + L)"),
          button(Icon.Glyph.AlignCenter, "Center align (Ctrl + Shift + E)"),
          button(Icon.Glyph.AlignRight, "Right align (Ctrl + Shift + R)"),
          button(Icon.Glyph.AlignJustify, "Justify (Ctrl + Shift + J)")
        )
      }))(),
      Markdown(
        """
          |Icons in toolbars is a rare case where tooltips are actually
          |suitable and should be used. Icons in other places, especially
          |standalone ones, should always have explicit labels that are
          |always visible.
          |
          |### Disabled elements
          |
          |Tooltips are also often used to explain why an action is disabled
          |or an information is not available:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Tooltip(
          renderTarget = Button(isDisabled = true)("Archive"),
          renderContent = () =>
            "You don't have permission to archive this deal. Please " +
            "contact your deal admins."
        )()
      }))(),
      Markdown(
        """
          |Despite being a common pattern, this is not a good one. It has 2
          |main problems:
          |- First, there is no visual hint. Tooltips on disabled elements is
          |not a universal pattern, and thus many users may never know about
          |the explanation at all.
          |- Second, explaining why is often not enough. Users often expect
          |to know what should they do to enable that element or access that
          |information. This is usually too much for text-only tooltips.
          |
          |For example, the above case should give users a link to the
          |"Contact" page so they can know how to contact their admins:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        <.div(
          Button(isDisabled = true)("Archive"),
          <.div(Style.color.gray7.margin.top8)(
            <.p("You don't have permission to archive this deal."),
            <.p("Please ", <.a(^.href := "#", "click here"), " to contact your deal admins")
          )
        )
      }))(),
      Markdown(
        """
          |### Truncated text
          |
          |Tooltips are often used to show the full version of truncated texts:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        val text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
          "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
        Tooltip(
          renderTarget = <.p(Style.width.px128.typography.truncate, text),
          renderContent = () => text
        )()
      }))(),
      Markdown(
        s"""
           |In these cases, users can see the full version when hover over,
           |but they cannot select or copy it. When using this pattern for
           |important information, such as file names or email addresses, the
           |consumers should make sure users have a way to access the
           |information fully, not just see it.
           |""".stripMargin
      )(),
      Markdown(
        s"""
          |## Accessibility [target-a11y]
          |
          |Anduin's tooltips can be triggered not only via mouse hover but
          |also keyboard focus. However, this is still far from enough, as it
          |left out those who don't have or can't use a keyboard or a mouse.
          |For instance, tooltips are effectively inaccessible for touch
          |screens and voice navigation.
          |
          |Note that out of the box it's not possible to focus on a tooltip
          |using keyboard. The focus on its target is what actually trigger a
          |tooltips' content.
          |
          |Tooltip's consumers, therefore, should ensure that their target can
          |be focused, either by using an interactive components (such as
          |[TextBox] or [Button]), or applying [`tabindex`] to non-interactive
          |ones:
          |
          |[focus-able]: https://developer.mozilla.org/en-US/docs/Mozilla/Tech/XUL/Tutorial/Focus_and_Selection
          |[TextBox]: ${ctl.urlFor(Pages.TextBox()).value}
          |[Button]: ${ctl.urlFor(Pages.Button()).value}
          |[`tabindex`]: https://developer.mozilla.org/en-US/docs/Web/HTML/Global_attributes/tabindex
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        Tooltip(
          renderTarget = <.span(^.tabIndex := 0, "Target"),
          renderContent = () => "Content"
        )()
      }))(),
      Markdown(
        s"""
           |## Wrapper
           |
           |```scala
           |targetWrapper: PortalWrapper = PortalWrapper.BlockContent
           |```
           |
           |As mentioned above, Tooltips use a [target wrapper][tw] to not
           |only position their content but also provide necessary event
           |handlers out of the box. By default, they use a [`BlockContent`]
           |wrapper, which can be changed via the `targetWrapper` prop.
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
        """
          |## With other portals
          |
          |Tooltips rely on "hover" and "focus" interactions, so in response to
          |"click" events their targets may be used to open another
          |portal-based component. For example, an icon button can have a
          |tooltip to label the action, with a popover to confirm that
          |action when users click on it.
          |
          |In these cases, ensure that the tooltip is rendered _inside_ the
          |other portal component. This is required for the tooltip to gone
          |when the other is shown:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Popover(
          renderTarget = (open, isOpen) => {
            Tooltip(
              renderTarget = {
                val icon = Some(Icon.Glyph.Trash)
                val style = Button.Style.Full(icon = icon, isSelected = isOpen)
                Button(style = style, onClick = open)()
              },
              renderContent = () => "Delete"
            )()
          },
          renderContent = renderDeleteDialog
        )()
      }))(),
      Markdown(
        s"""
           |# Content
           |
           |```scala
           |renderContent = () => String
           |```
           |
           |To avoid unnecessary calculations, `renderContent` is a [render 
           |prop]. It should return a single `String` to be displayed when
           |users hover over or focus on the [target](#target) element:
           |
           |[render prop]: https://reactjs.org/docs/render-props.html
           |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Tooltip(
          renderTarget = <.span(^.tabIndex := 0, "Target"),
          renderContent = () => "Content"
        )()
      }))(),
      Markdown(
        s"""
           |To learn more about portal's content in general, see the [Content]
           |section of the Portal guide.
           |
           |[Content]: ${ctl.urlFor(Pages.Portal("#content")).value}
           |""".stripMargin
      )(),
      Markdown(
        """
          |## Accessibility [content-a11y]
          |
          |Due to its design, the [content](#content) inside a tooltip can
          |only be read, either by users or their screen readers, but not
          |copied or even selected. Avoid tooltips if the text should be
          |copied by users:
          |""".stripMargin
      )(),
      ExampleSimple(
        """
          |This is a bad example. It's possible that users may want to copy
          |the email addresses inside tooltips, but they simply can't.
        """.stripMargin
      )({
        Tooltip(
          renderTarget = Tag()("Ada Banks"),
          renderContent = () => "ada.banks@anduintransact.com"
        )()
      }),
      Markdown(
        s"""
           |## String only
           |
           |Tooltips intentionally accept only a single `String` instead of
           |`VdomNode*` as their content. This prevents the consumers from
           |building complex interfaces inside tooltips' content, which likely
           |bring bad experiences due to tooltips'
           |[accessibility](#content-a11y) problems.
           |
           |To have anything more than text, consider [Popover].
           |
           |[Popover]: ${ctl.urlFor(Pages.Popover()).value}
           |""".stripMargin
      )(),
      Markdown(
        s"""
           |## Position
           |
           |```scala
           |position: PortalPosition = PortalPosition.TopCenter,
           |```
           |
           |By default, a tooltip's content is placed on top of its target,
           |horizontally centered. This can be set otherwise via the
           |`position` prop, using one of the options available at the
           |[`PortalPosition`][pp] object.
           |
           |Please refer to the [Content Position][pp] section of the Portal
           |guide for more details and examples. The below demo simply shows all
           |available positions when using with tooltips:
           |
           |[pp]: ${ctl.urlFor(Pages.Portal("#content-position")).value}
           |""".stripMargin
      )(),
      ExampleSimple()(PagePortalPositionTooltip()()),
      Markdown(
        s"""
           |## Content-only
           |
           |```scala
           |tooltip.TooltipContent
           |```
           |
           |The `TooltipContent` component, available at the same package,
           |allows consumers to render a tooltip's content directly (instead
           |of open it via a callback).
           |
           |To learn more about this type of component, including when and how
           |to use it, see the [Content-only] section of the Portal guide.
           |The below content only describes the props of `TooltipContent`:
           |
           |[Content-only]: ${ctl.urlFor(Pages.Portal("#content-only")).value}
           |
           |### text
           |
           |```scala
           |text: String
           |```
           |
           |The [content](#content) of the tooltip.
           |
           |### targetRef
           |
           |```scala
           |targetRef: Ref.Simple[raw.HTMLElement]
           |```
           |
           |A [ref] to a DOM element for the content to be anchored to. Note
           |that is simply a reference for informational purpose. This element
           |does not trigger the render of the tooltip's content at all.
           |
           |[ref]: https://github.com/japgolly/scalajs-react/blob/master/doc/REFS.md
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
        """.stripMargin
      )(),
      Markdown(
        """
          |# See also
          |
          |- Nielsen Norman Group's [Tooltip Guidelines](https://www.nngroup.com/articles/tooltip-guidelines/)
          |- Wikipedia's [Tooltip article](https://en.wikipedia.org/wiki/Tooltip)
        """.stripMargin
      )()
    )
  }
}
