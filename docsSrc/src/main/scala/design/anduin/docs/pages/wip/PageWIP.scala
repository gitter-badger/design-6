package design.anduin.docs.pages.wip

import design.anduin.docs.components._
import design.anduin.docs.app.main.Pages
import anduin.mcro.Source
import japgolly.scalajs.react.vdom.html_<^._

import design.anduin.components.button.Button
import design.anduin.components.tooltip.Tooltip
import design.anduin.style.Style

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
