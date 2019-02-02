package anduin.guide.pages.style
import anduin.guide.app.main.Pages
import anduin.guide.components._
import anduin.mcro.Source
import anduin.style.Style
import japgolly.scalajs.react.vdom.html_<^._

object PageColor {

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Color", Some(Style.color))(),
      Toc(headings = Source.getTocHeadings)(),
    )
  }
}
