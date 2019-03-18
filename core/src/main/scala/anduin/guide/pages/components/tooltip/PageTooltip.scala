package anduin.guide.pages.components.tooltip

import anduin.component.button.Button
import anduin.component.dialog.Dialog
import anduin.component.icon.Icon
import anduin.component.popover.Popover
import anduin.component.tooltip.Tooltip
import anduin.guide.app.main.Pages
import anduin.guide.components._
import anduin.guide.pages.components.portal.PagePortalPositionTooltip
import anduin.mcro.Source
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object PageTooltip {

  private def renderDeleteDialog(close: Callback) =
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
           |Tooltips should not be your first approach to a UI
           |problem. Tooltips require users' interaction, and it' eas
           |
           |- (e.g. input format requirement)
           |
           |In general, spend time to validate the usefulness of what you
           |want to show in your tooltip. If it's necessary for the users,
           |find a permanent place. If it's not, consider remove it altogether.
           |
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
           |open tooltip on mouse over). The consumers don't need to do any
           |integration:
           |
           |[t]: ${ctl.urlFor(Pages.Portal("#target")).value}
           |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Tooltip(
          renderTarget = "Target",
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
        s"""
           |## Target Wrapper
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
          |Tooltips rely on "hover" and "focus" interactions, so their targets
          |may also be used to open another portal-based component in
          |response to "click" events. For example, an icon button can have a
          |tooltip to describe the action, with a popover to confirm that
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
           |`renderContent` is a [render prop]. It should return the content of
           |a tooltip. This content will be displayed usually in response to
           |users' interactions with the [target](#target) element. To learn
           |more about portal's content in general, see the [Content] section
           |of the Portal guide.
           |
           |[Content]: ${ctl.urlFor(Pages.Portal("#content")).value}
           |[render prop]: https://reactjs.org/docs/render-props.html
           |
           |Tooltips' contents should consist of several elements of one or
           |more types, organized in simple layouts. A common use case is
           |with the [Menu] component:
           |
           |[Menu]: ${ctl.urlFor(Pages.Menu()).value}
           |
           |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Tooltip(
          renderTarget = "Target",
          renderContent = () => "Content"
        )()
      }))(),
      Markdown(
        s"""
           |Generally, tooltips are suitable for intermediate use cases. For
           |more complex ones, consider [Modal]. For single descriptive texts,
           |see [Tooltip].
           |
           |[Tooltip]: ${ctl.urlFor(Pages.Tooltip()).value}
           |[Modal]: ${ctl.urlFor(Pages.Modal()).value}
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
