package anduin.guide.pages.wip

import anduin.guide.components._
import anduin.guide.app.main.Pages
import anduin.mcro.Source
import japgolly.scalajs.react.vdom.html_<^._

import anduin.component.button.Button
import anduin.component.tooltip.Tooltip
import anduin.style.Style

object PageWIP {
  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Working in Progress", None)(),
      Toc(headings = Source.getTocHeadings)(),
      <.div("WIP"),
      ExampleSimple()({
        <.div(
          Style.width.maxContent,
        )
      })
    )
  }
}
