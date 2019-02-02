package anduin.guide.pages.home

import anduin.guide.app.main.Pages

import japgolly.scalajs.react.vdom.html_<^._

import anduin.style.Style

object PageHome {

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Style.backgroundColor.gray1,
      PageHomeList(ctl)()
    )
  }

}
