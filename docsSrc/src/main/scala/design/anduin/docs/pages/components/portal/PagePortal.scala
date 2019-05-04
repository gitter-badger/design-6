package design.anduin.docs.pages.components.portal

import design.anduin.components.button.Button
import design.anduin.components.input.textbox.TextBox
import design.anduin.components.modal.{Modal, ModalBody}
import design.anduin.components.popover.{Popover, PopoverContent}
import design.anduin.components.portal.{PortalPosition, PortalWrapper}
import design.anduin.components.tooltip.Tooltip
import design.anduin.docs.app.main.Pages
import design.anduin.docs.components._
import design.anduin.macros.Source
import design.anduin.style.Style
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
           |components, such as [Popover], [Tooltip], or [Modal].
           |
           |[Popover]: ${ctl.urlFor(Pages.Popover()).value}
           |[Tooltip]: ${ctl.urlFor(Pages.Tooltip()).value}
           |[Modal]: ${ctl.urlFor(Pages.Modal()).value}
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
          |""".stripMargin
      )(),
      Markdown(
        s"""
           |# Target Wrapper
           |
           |```scala
           |portal.PortalWrapper
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
           |There are 3 options available at the `PortalWrapper` object:
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
            targetWrapper = PortalWrapper.Inline,
            renderTarget = <.span(Style.color.primary5, "inline"),
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
          targetWrapper = PortalWrapper.Block,
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
           |# Content Position
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
          |# Content-only
          |
          |In some rare cases, the [contents](#content) of portal components
          |can be used directly, without their target parts, to simplify the
          |implementation. This section explains when and how to use them.
          |
          |## When to use
          |
          |The API of portal components are designed so that their consumers
          |can only open their content via [callbacks]. In other words, the
          |content can only be opened in response to specific events, like
          |users' interactions with the target element. This prevents users
          |from unexpected interruption.
          |
          |[callbacks]: https://github.com/japgolly/scalajs-react/blob/master/doc/CALLBACK.md
          |
          |That being said, there are a few cases where this callback-based
          |approach may unnecessarily increase the implementation complexity.
          |In some cases, an ordinary render-based approach may allow much
          |simpler implementation.
          |
          |For example, when building a [product tour] experience, it is often
          |easier and more straightforward to conditionally render popovers'
          |contents based on the current step than attaching a popover to every
          |"next" button:
          |
          |[product tour]: https://www.invisionapp.com/inside-design/saas-product-tour-trends/
          |
          |```scala
          |case class State(step: Int)
          |
          |def render(state: State) = state.step match {
          |  case 0 => PopoverContent(...)(Step1()())
          |  case 1 => PopoverContent(...)(Step2()())
          |  case 2 => PopoverContent(...)(Step3()())
          |  case _ => EmptyVdom
          |}
          |```
          |
          |
          |""".stripMargin
      )(),
      Markdown(
        """
          |## How to use
          |
          |The component to render the content of a portal component is
          |located at the same package and has the same name but with a
          |"Content" suffix. For example, `popover.PopoverContent` is the
          |component to render a popover directly.
          |
          |Generally, these Content-only components have similar props to
          |their full version, excluding target-related props (e.g.
          |`renderTarget` and `targetWrapper`). Their exact APIs can be found
          |at the page of its original component.
          |
          |Other than that, a Content-only component is rendered in the exact
          |same way as if it is opened by a target in the full version. This
          |means it also has the same [portal] behaviours (e.g. appear on top
          |of current interface) as well as specific appearance of the full
          |one.
          |
          |[portal]: https://reactjs.org/docs/portals.html
          |
          |Below is a very simple example of using Content-only component, in
          |which a popover will be shown when the text box is empty. Real use
          |cases of Content-only components are usually more complex.
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
