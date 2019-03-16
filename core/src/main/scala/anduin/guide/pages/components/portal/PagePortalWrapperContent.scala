package anduin.guide.pages.components.portal

import anduin.component.button.Button
import anduin.component.icon.Icon
import anduin.component.table.Table
import anduin.component.tooltip.Tooltip
import anduin.component.truncate.TruncateMarkup
import anduin.guide.components.ExampleSimple
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class PagePortalWrapperContent() {
  def apply(): VdomElement = PagePortalWrapperContent.component()
}

object PagePortalWrapperContent {

  private type Props = PagePortalWrapperContent

  private val div = <.div(Style.margin.bottom8)

  private def renderTooltip(target: VdomNode) =
    Tooltip(renderTarget = target, renderContent = () => "Content")()

  private object TextExample {
    private def renderP(title: String, mod: TagMod = TagMod.empty): VdomElement = {
      <.p(
        Style.color.gray6,
        mod,
        <.span(Style.color.gray8, title),
        """
          |Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do 
          |eiusmod tempor incididunt ut labore et dolore magna aliqua.
        """.stripMargin
      )
    }

    val short: VdomElement = renderTooltip("Short text")
    val wrap: VdomElement = renderTooltip(
      renderP("Long text that is wrapped to new lines")
    )
    val css: VdomElement = renderTooltip(
      renderP("Long text that is truncated with CSS", Style.typography.truncate)
    )
    val js: VdomElement = renderTooltip(
      TruncateMarkup(renderTarget = renderP("Long text that is truncated with JS"))
    )

    val render: VdomElement =
      ExampleSimple("Text-based targets")(short, div, wrap, div, css, div, js)
  }

  private object TableExample {

    private val IntTable = (new Table[Int])()

    private def renderTable(tooltip: VdomElement) =
      IntTable(
        columns = List(
          Table.Column("Short", _ => Table.Cell(TextExample.short)),
          Table.Column("Long", _ => Table.Cell(tooltip)),
        ),
        rows = List(0),
        getKey = _.toString
      )()

    private val wrap = renderTable(TextExample.wrap)
    private val css = renderTable(TextExample.css)
    private val js = renderTable(TextExample.js)

    val render: VdomElement =
      ExampleSimple(
        """
          |Text-based targets inside tables. These tables all have
          |`table-layout: auto` defined. As you can see, CSS-based truncation
          |doesn't work properly in this type of table so it's recommended to
          |use JS-based truncation here.
        """.stripMargin
      )(
        <.div(wrap, div),
        <.div(Style.overflow.hidden, css, div),
        <.div(js)
      )
  }

  private object ComponentExample {
    val render: VdomElement =
      ExampleSimple("Component-based targets")(
        <.div(
          Style.flexbox.flex.flexbox.itemsCenter,
          renderTooltip(Button()("Target")),
          <.div(Style.margin.right16),
          renderTooltip(Icon(Icon.Glyph.Info)())
        )
      )
  }

  private val render = {
    <.div(
      ComponentExample.render,
      TextExample.render,
      TableExample.render
    )
  }

  private val component = ScalaComponent
    .static(this.getClass.getSimpleName)(render)
}
