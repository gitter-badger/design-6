package design.anduin.docs.pages.home

import design.anduin.docs.app.main.Pages

import japgolly.scalajs.react.vdom.html_<^._

import design.anduin.style.Style

object PageHome {

  private val hero = <.div(
    // 280px is enough to show the first 2 lines of HomeList
    ^.height := "calc(100vh - 280px)",
    Style.flexbox.flex.flexbox.justifyCenter.flexbox.itemsCenter,
    <.h1(^.fontSize := "48px", Style.fontWeight.bold, "Anduin Design System")
  )

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Style.background.gray1,
      <.div(
        ^.maxWidth := "1600px",
        Style.margin.horAuto,
        <.div(hero),
        <.div(PageHomeList(ctl)())
      )
    )
  }

}
