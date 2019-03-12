package anduin.guide.pages.components.icon

import anduin.component.icon.Icon
import anduin.component.icon.Icon.Product._
import anduin.guide.app.main.Pages
import anduin.guide.components._
import anduin.mcro.Source
import anduin.style.Style
import japgolly.scalajs.react.vdom.html_<^._

object PageIconProduct {

  private def renderIcon(icon: Icon.Name): VdomElement = {
    val sName = icon.getClass.getSimpleName
    val sample = IconSample(icon, Icon.Size.Px40, Style.padding.all8)()
    <.div(
      ^.key := sName,
      Style.flexbox.fill,
      <.div(Style.margin.horAuto.width.maxContent, sample),
      <.p(Style.textAlign.center.fontWeight.medium.fontSize.px11, sName)
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
        Icon(name = Icon.Product.Signature, size = Icon.Size.Px40)()
      }))(),
      Markdown(
        s"""
           |Product Icons are designed to work best at 40 pixels. They all
           |have colorful, [squircle-shaped][ref] background built-in:
           |
           |[ref]: https://en.wikipedia.org/wiki/Squircle
           |""".stripMargin
      )(),
      ExampleSimple()({
        <.div(
          Style.flexbox.flex,
          List(Negotiation, LegalDiligence, Signature, Wiring, Closing).toVdomArray(renderIcon)
        )
      }),
      Markdown(
        """
          |Colors of Product Icons cannot be changed as they reflect each
          |product's identity.
        """.stripMargin
      )()
    )
  }
}
