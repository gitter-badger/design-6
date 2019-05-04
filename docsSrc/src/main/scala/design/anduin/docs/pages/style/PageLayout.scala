package design.anduin.docs.pages.style

import design.anduin.docs.components._
import design.anduin.docs.app.main.Pages
import anduin.mcro.Source
import design.anduin.style.Style
import japgolly.scalajs.react.vdom.html_<^._

object PageLayout {
  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Layout", Some(Style))(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        """
          |Layout should be built with [flexbox]:
          |
          |[flexbox]: https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Flexible_Box_Layout/Basic_Concepts_of_Flexbox
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        val box = Style.background.gray3.textAlign.center.padding.all8
        <.div(
          Style.flexbox.flex,
          <.div(Style.flexbox.none.margin.right8, box, "None"),
          <.div(Style.flexbox.fill, box, "Fill"),
        )
      }))(),
      Markdown(
        """
          |We intentionally support only a small subset of flexbox to simplify
          |the layout work. Features/properties that have little or practical
          |usages to us are removed.
          |
          |Also, we do not support [responsive] layout at the moment. We are
          |looking into some solutions but nothing concrete for short-term
          |future.
          |
          |[responsive]: https://developer.mozilla.org/en-US/docs/Web/Apps/Progressive/Responsive/responsive_design_building_blocks
          |""".stripMargin
      )(),
      Markdown(
        """
          |# Flex container
          |
          |To build a layout with flexbox, first we need to define a flex
          |container using `Style.flexbox.flex`:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        <.div(
          Style.flexbox.flex,
          <.div(Style.flexbox.none.margin.right8, "First"),
          <.div(Style.flexbox.none, "Second")
        )
      }))(),
      Markdown(
        """
          |The direct children of this container are now [flex
          |items](#flex-items). Their sizes and positions can be
          |controlled with flexbox using the properties described below.
          |
          |Note that this creates a block-level container (i.e. like a `div`).
          |We highly discourage the idea of inline-level flex container since
          |it can be handled better with the [normal inline flow][inline].
          |
          |[inline]: https://developer.mozilla.org/en-US/docs/Web/HTML/Inline_elements
          |""".stripMargin
      )(),
      Markdown(
        """
          |## Direction
          |
          |By default, flex items are positioned horizontally. To laid them
          |out vertically, use `Style.flexbox.column`:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        <.div(
          Style.flexbox.flex.flexbox.column,
          <.div(Style.flexbox.none, "First"),
          <.div(Style.flexbox.none, "Second")
        )
      }))(),
      Markdown(
        """
          |This behaviour is very similar to using [block elements], which is
          |more lightweight and should be considered first.
          |
          |[block elements]: https://developer.mozilla.org/en-US/docs/Web/HTML/Block-level_elements
          |""".stripMargin
      )(),
      Markdown(
        """
          |## Wrap
          |
          |By default, flexbox will try to place all items on one line,
          |including shrinking them if possible, or it will cause an overflow.
          |To avoid overflow and wrap flex items onto new lines, use
          |`Style.flexbox.wrap`:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        val box = TagMod(
          Style.flexbox.none.padding.all8.background.gray3,
          Style.margin.right8.margin.bottom8
        )
        <.div(
          Style.flexbox.flex.flexbox.wrap,
          ^.marginBottom := "-8px", // visual touch, don't mind me
          <.div(box, "There is"),
          <.div(box, "only one"),
          <.div(box, "corner of the universe"),
          <.div(box, "you can"),
          <.div(box, "be certain of"),
          <.div(box, "improving"),
          <.div(box, "and that's"),
          <.div(box, "your own self"),
          <.div(box, "–"),
          <.div(box, "Aldous Huxley")
        )
      }))(),
      Markdown(
        """
          |## Alignment
          |
          |Flexbox makes no assumption about the writing mode, so it uses
          |somewhat unusual terms like "main-axis" or "start" to specify items
          |alignment.
          |
          |If you are not familar with these terms yet, we recommend to take
          |a look at [this MDN's article][mdn] before moving on.
          |
          |[mdn]: https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Flexible_Box_Layout/Basic_Concepts_of_Flexbox#The_two_axes_of_flexbox
          |""".stripMargin
      )(),
      Markdown(
        """
          |### Items*
          |
          |```scala
          |Style.flexbox.itemsStart
          |Style.flexbox.itemsEnd
          |Style.flexbox.itemsCenter
          |Style.flexbox.itemsBaseline
          |Style.flexbox.itemsStretch // default
          |```
          |
          |`Style.flexbox.items*` controls how flex items are positioned
          |along their container's **cross axis**:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        val box = Style.flexbox.none.padding.all4.background.gray3.margin.right8
        <.div(
          Style.flexbox.flex.flexbox.itemsCenter,
          <.div(box, Style.height.px32, "one"),
          <.div(box, Style.height.px64, "two"),
          <.div(box, Style.height.px48, "tree")
        )
      }))(),
      Markdown(
        """
          |### Justify*
          |
          |```scala
          |Style.flexbox.justifyStart // default
          |Style.flexbox.justifyEnd
          |Style.flexbox.justifyCenter
          |Style.flexbox.justifyBetween
          |Style.flexbox.justifyAround
          |```
          |
          |`Style.flexbox.justify*` controls how flex items are positioned
          |along their container's **main axis**:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        val box = Style.flexbox.none.padding.all4.background.gray3.margin.right8
        <.div(
          Style.flexbox.flex.flexbox.justifyBetween,
          <.div(box, "one"),
          <.div(box, "two"),
          <.div(box, "tree")
        )
      }))(),
      Markdown(
        """
          |Since this works by distributing [available spaces][as] between
          |items, it has no practical effect when there are [fill](#flex-items)
          |items:
          |
          |[as]: https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Flexible_Box_Layout/Basic_Concepts_of_Flexbox#Properties_applied_to_flex_items
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        val box = Style.padding.all8.background.gray3.margin.right8
        <.div(
          // No space left to distribute...
          Style.flexbox.flex.flexbox.justifyBetween,
          <.div(box, Style.flexbox.none, "one"),
          // ...since this already takes any available space:
          <.div(box, Style.flexbox.fill, "two")
        )
      }))(),
      Markdown(
        """
          |# Flex items
          |
          |[By default][mdn], a flex item is allowed to shrink but not grow.
          |This usually leads to unexpected behaviour in practice and thus
          |should be avoided.
          |
          |Instead, it is recommended that all flex items should have one of
          |these styles applied:
          |
          |- `Style.flexbox.none` to prevent a flex item from growing or
          |shrinking from its initial size.
          |- `Style.flexbox.fill` to allow a flex item to grow and shrink as
          |needed, ignoring its initial size:
          |
          |[mdn]: https://developer.mozilla.org/en-US/docs/Web/CSS/flex
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        val box = Style.background.gray3.textAlign.center.padding.all8
        <.div(
          Style.flexbox.flex,
          <.div(Style.flexbox.none.margin.right8, box, "None"),
          <.div(Style.flexbox.fill, box, "Fill"),
        )
      }))(),
    )
  }
}
