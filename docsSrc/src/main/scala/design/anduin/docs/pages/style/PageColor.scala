package design.anduin.docs.pages.style

import design.anduin.docs.app.main.Pages
import design.anduin.docs.components._
import design.anduin.macros.Source
import design.anduin.style.Style
import japgolly.scalajs.react.vdom.html_<^._

object PageColor {

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Color", Some(Style.color))(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        """
          |Color expresses hierarchy and gives meaning:
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        val box = Style.width.px32.height.px32.margin.right16
        <.div(
          Style.flexbox.flex,
          <.div(box, Style.background.gray3),
          <.div(box, Style.background.primary3),
          <.div(box, Style.background.gray3),
          <.div(box, Style.background.gray3)
        )
      }))(),
      Markdown(
        """
          |# Usage
          |
          |Color is applied to an element via `Style.color`,
          |`Style.background` and `Style.borderColor`:
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        <.div(
          Style.background.gray2.color.primary4,
          Style.border.all.borderColor.gray4,
          Style.width.maxContent.padding.all8,
          "Text"
        )
      }))(),
      Markdown(
        """
          |Color names (e.g. `gray2`, `gray4`, `primary4`) are the same for all
          |properties and are divided into 2 sets: [gray](#gray-set) and
          |[purposeful](#purposeful-set). See their sections for the complete
          |name list.
          |
          |Color of hover or active state can be set by prefixing the color
          |name with `hover` or `active`:
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        <.div(
          Style.background.hoverPrimary4.color.hoverGray0,
          Style.width.maxContent.padding.all8,
          "Hover me"
        )
      }))(),
      Markdown(
        """
          |# Gray set
          |
          |The gray set contains 10 shades of gray to build app's interface,
          |from text to background:
        """.stripMargin
      )(),
      PageColorGray()(),
      Markdown(
        """
          |These gray colors should be used to create distinction between
          |elements, but not to highlight any of them. To highlight specific
          |elements, use the [purposeful set](#purposeful-set):
        """.stripMargin
      )(),
      Markdown(
        """
          |# Purposeful set
          |
          |In a gray scale interface, these colors highlight elements
          |and communicate their intentions. There are 4 purposeful colors,
          |with 5 shades of each:
        """.stripMargin
      )(),
      PageColorPurposeful()(),
      Markdown(
        s"""
           |Some components support these colors via a `color` prop, but they
           |might not support all of them. For example, [Button] doesn't
           |support the warning color, while [Well] does.
           |
           |[Button]: ${ctl.urlFor(Pages.ButtonBox("#color")).value}
           |[Well]: ${ctl.urlFor(Pages.Well("#color")).value}
        """.stripMargin
      )(),
    )
  }
}
