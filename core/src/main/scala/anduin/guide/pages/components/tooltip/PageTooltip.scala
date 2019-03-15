package anduin.guide.pages.components.tooltip

import anduin.component.portal.PortalPosition
import anduin.component.tooltip.Tooltip
import anduin.guide.app.main.Pages
import anduin.guide.components._
import anduin.mcro.Source
import anduin.style.Style
import japgolly.scalajs.react.vdom.html_<^._

object PageTooltip {

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Tooltip", Some(Tooltip))(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        """
          |Tooltip a
        """.stripMargin
      )(),
      <.div(Style.height.px256),
      <.div(Style.height.px256),
      ExampleRich(Source.annotate({
        Tooltip(
          renderTarget = "Target",
          renderContent = () => "Content",
          position = PortalPosition.TopLeft
        )()
      }))(),
      <.div(Style.height.px256),
    )
  }
}
