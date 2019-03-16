package anduin.guide.pages.components.portal

import anduin.component.button.Button
import anduin.component.input.textbox.TextBox
import anduin.component.modal.{Modal, ModalBody}
import anduin.component.popover.{Popover, PopoverContent}
import anduin.component.portal.{PortalPosition, PortalTargetWrapper}
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
           |describes the common concepts and designs between portal-based
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
           |# Target and Content
           |
           |Portal components are made of 2 parts: a "target" and a "content":
           |
           |## Target
           |
           |Portals' targets are always rendered. They should trigger the
           |render of their corresponding [content](#content) upon users'
           |interactions, such as [mouse hover] in [Tooltip] components:
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
          |Portal components often render the content with their specific
          |styles. For example, tooltips' contents are shown in black,
          |rounded boxes with an arrow. In fact, the appearance of content is
          |the main difference between portal components.
          |
         """.stripMargin
      )(),
      Markdown(
        s"""
           |# Target Wrapper
           |
           |```scala
           |portal.PortalTargetWrapper
           |```
           |
           |**Available in:** [Popover], [Tooltip]
           |
           |[Popover]: ${ctl.urlFor(Pages.Popover()).value}
           |[Tooltip]: ${ctl.urlFor(Pages.Tooltip()).value}
           |
           |Popovers and tooltips wrap their targets inside a wrapper tag in
           |order to [obtain DOM-level information][ref] (e.g. to position the
           |contents) or provide built-in functionality (e.g. open a tooltip's
           |content when hovered) without knowing or updating their targets
           |directly.
           |
           |[ref]: https://reactjs.org/docs/refs-and-the-dom.html
           |
           |There are 3 options available at the `PortalTargetWrapper` object:
           |""".stripMargin
      )(),
      Markdown(
        """
           |## BlockContent
           |
           |`BlockContent` wraps its targets inside a `div` with [intrinsic]
           |width:
           |
           |[intrinsic]: https://developer.mozilla.org/en-US/docs/Glossary/Intrinsic_Size
           |
           |```scala
           |<.div(Style.width.fitContent.maxWidth.pc100)
           |```
           |
           |As a result, this works best for [block-level] targets whose
           |width should be based on their content. The term "intrinsic" may
           |sound unfamiliar, but in practice it is the expected
           |behaviour in the majority of cases:
           |
           |[block-level]: https://developer.mozilla.org/en-US/docs/Web/HTML/Block-level_elements
           |""".stripMargin
      )(),
      PagePortalWrapperContent()(),
      Markdown(
        """
          |## Inline
          |
          |`Inline` wraps its target inside a `span` tag, without any
          |additional styles. It is suitable for [inline] targets:
          |
          |[inline]: https://developer.mozilla.org/en-US/docs/Web/HTML/Inline_elements
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        <.p(
          "This is a sentence with an ",
          Tooltip(
            targetWrapper = PortalTargetWrapper.Inline,
            renderTarget = <.span(Style.color.blue5, "inline"),
            renderContent = () => "Content"
          )(),
          " tooltip."
        )
      }))(),
      Markdown(
        """
          |## Block
          |
          |`Block` wraps its target inside a `div` tag like `BlockContent`,
          |but without the intrinsic width. This means the built-in full-width
          |behaviour of block-level elements are used, allows full-width
          |targets to be displayed properly:
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        val popover = Popover(
          targetWrapper = PortalTargetWrapper.Block,
          renderTarget = (open, isOpened) => {
            val style = Button.Style.Full(isSelected = isOpened, isFullWidth = true)
            Button(style, onClick = open)("Full-width Target")
          },
          renderContent = _ => <.div(Style.padding.all8, "Content")
        )()
        <.div(Style.width.px256, popover)
      }))(),
      Markdown(
        s"""
          |# Position
          |
          |```scala
          |portal.PortalPosition
          |```
          |
          |**Available in:** [Popover], [Tooltip]
          |
          |[Popover]: ${ctl.urlFor(Pages.Popover()).value}
          |[Tooltip]: ${ctl.urlFor(Pages.Tooltip()).value}
          |
          |The content of a popover or a tooltip is always anchored to its
          |[target](#Target). By default, it is usually placed on top of the
          |target and horizontally centered. The `PortalPosition` object
          |contains options to specify otherwise:
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
          |For both popovers and tooltips, there are options to place the
          |content on top, right, bottom or left of the target. Each position
          |also has 3 additional variants for fine tuning:
        """.stripMargin
      )(),
      ExampleSimple()(PagePortalPositionPopover()()),
      ExampleSimple()(PagePortalPositionTooltip()()),
      Markdown(
        """
          |# Standalone
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
