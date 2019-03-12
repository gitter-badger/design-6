package anduin.guide.pages.components.icon

import anduin.component.icon.{Icon, IconProduct}
import anduin.component.icon.Icon.Product._
import anduin.guide.app.main.Pages
import anduin.guide.components._
import anduin.mcro.Source
import anduin.style.Style
import japgolly.scalajs.react.vdom.html_<^._

object PageIconProduct {

  private def renderIcon(icon: IconProduct): VdomElement = {
    <.div(
      ^.key := icon.getClass.getSimpleName,
      Style.flexbox.fill,
      <.div(
        Style.margin.horAuto.width.maxContent.margin.bottom8,
        IconSample(
          icon,
          Icon.Size.Px32,
          TagMod(icon.bgMod, Style.padding.all4.borderRadius.px2.margin.all4)
        )()
      ),
      <.p(
        Style.textAlign.center.fontWeight.medium.fontSize.px11,
        icon.getClass.getSimpleName
      )
    )
  }

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Product Icons")(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        s"""
           |`Icon.Product` contains [Icon's names] that represent the
           |products of Anduin platform:
           |
           |[Icon's names]: ${ctl.urlFor(Pages.Icon("#name")).value}
           |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        <.div(
          Style.padding.all4.borderRadius.px2.width.maxContent,
          Icon.Product.Signature.bgMod,
          Icon(name = Icon.Product.Signature, size = Icon.Size.Px32)()
        )
      }))(),
      Markdown(
        s"""
           |Product Icons are designed to work best at 32 pixels and on a
           |matching background color. These background colors are provided
           |via the `bgColor: String` and `bgMod: TagMod` properties of each
           |name:
           |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        <.div(
          Style.padding.ver4.padding.hor8,
          Icon.Product.Negotiation.bgMod,
          <.span(
            Style.fontWeight.medium.color.white,
            "Negotiation bg color should be: ",
            Icon.Product.Negotiation.bgColor
          )
        )
      }))(),
      Markdown(
        """
          |There are 5 product icons at the moment:
        """.stripMargin
      )(),
      ExampleSimple()({
        <.div(
          Style.flexbox.flex,
          List(Negotiation, LegalDiligence, Signature, Wiring, Closing).toVdomArray(renderIcon)
        )
      }),
      Markdown(
        """
          |Obviously the colors of product icons cannot be changed as they
          |reflect each product's identity. In fact, the consumers must even
          |provide a matching background as described above.
        """.stripMargin
      )()
    )
  }
}
