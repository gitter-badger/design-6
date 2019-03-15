package anduin.guide.pages.components.portal

import anduin.component.button.Button
import anduin.component.input.textbox.TextBox
import anduin.component.modal.{Modal, ModalBody}
import anduin.component.popover.{Popover, PopoverContent}
import anduin.component.portal.{PortalPosition, PortalWrapper}
import anduin.component.tooltip.Tooltip
import anduin.guide.app.main.Pages
import anduin.guide.components._
import anduin.mcro.Source
import anduin.style.Style
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.raw.HTMLElement

object PagePortal {

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Portal Utilities")(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        s"""
           |This page does not describe an actual component. Instead, it
           |describes the common concepts and properties between portal-based
           |components, such as [Modal], [Popover], [Tooltip] or [Toast].
           |
           |[Modal]: ${ctl.urlFor(Pages.Modal()).value}
           |[Popover]: ${ctl.urlFor(Pages.Popover()).value}
           |[Tooltip]: ${ctl.urlFor(Pages.Tooltip()).value}
           |[Toast]: ${ctl.urlFor(Pages.Toast()).value}
        """.stripMargin
      )(),
      Markdown(
        s"""
           |# Target & Content
           |
           |Portal components are made of 2 parts: a "target" and a "content":
           |
           |## Target
           |
           |Portals' targets are always rendered. They should trigger the
           |render of their corresponding [contents](#content) upon users'
           |interactions, such as [mouse hover] in [Tooltip] components.
           |
           |[mouse hover]: https://en.wikipedia.org/wiki/Mouseover
           |[Tooltip]: ${ctl.urlFor(Pages.Tooltip()).value}
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
        """
          |
          |Target elements are rendered inside the DOM hierarchy of their
          |consumers like normal components. In some cases they might be
          |wrapped inside a [target wrapper](#target-wrapper).
          |
          |## Content
          |
          |Portals' contents are rendered in response to users' interactions
          |with the target elements. They will be appended into the `body`
          |tag using [React's Portal], thus always appear in front of the
          |current interface:
          |
          |[React's Portal]: https://reactjs.org/docs/portals.html
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Modal(
          title = "Sample Modal",
          renderTarget = open => Button(onClick = open)("Target"),
          renderContent = _ => ModalBody()("Content")
        )()
      }))(),
      Markdown(
        """
          |No `z-index` is used to render portals' contents, so those that
          |are existing at a same time would be stacked in the [order of their
          |renders][stacking] (i.e. what is rendered later will be above).
          |
          |[stacking]: https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Positioning/Understanding_z_index/Stacking_without_z-index
          |
         """.stripMargin
      )(),
      Markdown(
        s"""
           |# Target Wrapper
           |
           |```scala
           |targetWrapper: PortalWrapper = PortalWrapper.BlockContent
           |```
           |
           |**Application for:** [Popover], [Tooltip]
           |
           |[Popover]: ${ctl.urlFor(Pages.Popover()).value}
           |[Tooltip]: ${ctl.urlFor(Pages.Tooltip()).value}
           |
           |Behind the scene, popovers must have
           |
           |https://github.com/japgolly/scalajs-react/blob/master/doc/REFS.md#refs-to-vdom-tags
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
            targetWrapper = PortalWrapper.Inline,
            renderTarget = (open, _) => Button(Button.Style.Text(), onClick = open)("inline"),
            renderContent = _ => <.div(Style.padding.all8, "Content")
          )(),
          " popover."
        )
      }))(),
      ExampleRich(Source.annotate({
        val popover = Popover(
          targetWrapper = PortalWrapper.BlockFull,
          renderTarget = (open, isOpened) => {
            val style = Button.Style.Full(isSelected = isOpened, isFullWidth = true)
            Button(style, onClick = open)("Full-width Target")
          },
          renderContent = _ => <.div(Style.padding.all8, "Content")
        )()
        <.div(Style.width.px256, popover)
      }))(),
      Markdown(
        """
          |# Position
          |
          |```scala
          |position: PortalPosition = PortalPosition.TopCenter
          |```
          |
          |A popover's content is always anchored to its [target](#Target).
          |By default, it is placed on top of the target, horizontally
          |centered. The `position` prop can be used to set otherwise:
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        Popover(
          position = PortalPosition.RightTop,
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
      ExampleSimple()(PagePortalPositionPopover()()),
      ExampleSimple()(PagePortalPositionTooltip()()),
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
          |  position: PortalPosition = PortalPosition.TopCenter,
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
                  position = PortalPosition.BottomLeft
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
