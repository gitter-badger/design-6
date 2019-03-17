package anduin.guide.pages.components.tooltip

import anduin.component.tooltip.Tooltip
import anduin.guide.app.main.Pages
import anduin.guide.components._
import anduin.guide.pages.components.portal.PagePortalPositionTooltip
import anduin.mcro.Source
import japgolly.scalajs.react.vdom.html_<^._

object PageTooltip {

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
          renderTarget = "Target",
          renderContent = () => "Content"
        )()
      }))(),
      Markdown(
        s"""
           |Tooltips is a [portal-based][p] component. It requires at least
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
           |renderTarget = VdomNode
           |```
           |
           |`renderTarget` should receive the tooltip's target element, to
           |which the tooltip will anchor its content.
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
           |For more details and examples, refer to the [Target] section of the
           |Portal guide.
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
           |Tooltips use a [target wrapper][tw] in order to position their
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
        """
          |## Closing
          |
          |`renderContent` receives a single parameter, which is a callback to
          |close the tooltip's content. Consumers can use this to have an
          |explicit method for users to close the content:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Tooltip(
          renderTarget = "Target",
          renderContent = () => "Content"
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
           |By default, a portal's content is placed on top of its target,
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
           |allows consumers to render a portal's content directly (instead of
           |open it via a callback).
           |
           |To learn more about this type of component, including when and how
           |to use it, see the [Content-only] section of the Portal guide.
           |The below content only describes the props of `TooltipContent`:
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
           |does not trigger the render of the tooltip's content at all.
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
           |provides a `Some(closeTooltip)` here so that the content would be
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
           |This defines whether users' focus should be shifted into the tooltip
           |automatically. Usually this should be `true` so keyboard navigation
           |and "Esc to close" work out of the box. However, this can be
           |disabled when the focus should stay outside of the tooltip (e.g.
           |Dropdown's target)
           |
        """.stripMargin
      )(),
    )
  }
}
