package anduin.guide.pages.home

import anduin.guide.app.main.Pages

import japgolly.scalajs.react.vdom.html_<^._

import anduin.style.Style

object PageHome {

  private val hero = <.div(
    // 280px is enough to show the first 2 lines of HomeList
    ^.height := "calc(100vh - 280px)",
    Style.flexbox.flex.flexbox.justifyCenter.flexbox.itemsCenter,
    <.h1(Style.fontSize.px48.fontWeight.bold, "Anduin Design System")
  )

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Style.backgroundColor.gray1,
      <.div(
        ^.maxWidth := "1600px",
        <.div(hero),
        <.div(PageHomeList(ctl)())
      )
    )
  }

}
