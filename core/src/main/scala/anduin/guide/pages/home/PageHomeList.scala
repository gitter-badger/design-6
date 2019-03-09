// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.guide.pages.home

import anduin.guide.app.main.Pages
import anduin.guide.pages.home.{PageHomeLink => Link}
import anduin.guide.pages.home.PageHomeLink.Target._
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[home] final case class PageHomeList(
  ctl: Pages.Ctl
) {
  def apply(): VdomElement = PageHomeList.component(this)
}

private[home] object PageHomeList {

  private type Props = PageHomeList

  private def h(content: VdomNode): VdomElement = {
    <.div(
      Style.flexbox.flex.flexbox.itemsBaseline.flexbox.justifyCenter,
      Style.padding.ver32,
      // To have the same height with PageLink
      Style.border.bottom.borderWidth.px2.borderColor.transparent,
      // the content will always be aligned with this h1
      // \u200B is a zero-width space
      <.h1(Style.flexbox.none.lineHeight.px32, "\u200B"),
      <.div(Style.flexbox.none, content),
    )
  }

  private def h1(text: String): VdomElement = h(
    <.h1(Style.fontSize.px30.lineHeight.px32.fontWeight.bold, text)
  )

  private def h2(text: String): VdomElement = h(
    <.h1(Style.fontSize.px23.lineHeight.px24.fontWeight.bold, text)
  )

  private val childWidth = ^.width := "25vw"
  private def child = <.div(Style.flexbox.none, childWidth)

  private def render(props: Props): VdomElement = {
    val ctl = props.ctl
    <.div(
      Style.flexbox.flex.flexbox.itemsStart,
      <.div(
        Style.flexbox.flex.flexbox.wrap.flexbox.justifyAround,
        ^.flex := "1.1 1 0px",
        ^.padding := "32px 0 96px",
        child(h1("Style")),
        child(Link(Page(ctl, Pages.Color(), isWIP = false))()),
        child(Link(Page(ctl, Pages.Typography(), isWIP = false))()),
        child(Link(Page(ctl, Pages.Layout(), isWIP = false))()),
        child(Link(Page(ctl, Pages.Logo(), isWIP = false))()),
        child(h1("Links")),
        child(Link(URL("GitHub", "https://github.com/anduintransaction/design"))()),
        child(Link(URL("Work with us", "https://www.anduintransact.com/careers"))()),
      ),
      <.div(
        Style.background.white,
        ^.flex := "2 1 0px",
        ^.padding := "32px 0 96px",
        ^.boxShadow := "0px 4px 16px 0px rgba(0, 0, 0, 0.06)",
        h1("Components"),
        <.div(
          Style.flexbox.flex.flexbox.justifyAround.flexbox.wrap,
          ^.justifyContent := "space-evenly",
          child(Link(Page(ctl, Pages.Button(), isWIP = false))()),
          child(Link(Page(ctl, Pages.Menu(), isWIP = true))()),
          child(Link(Page(ctl, Pages.Icon(), isWIP = false))()),
          child(Link(Page(ctl, Pages.Tree(), isWIP = true))()),
          child(Link(Page(ctl, Pages.Progress(), isWIP = false))()),
          child(Link(Page(ctl, Pages.Stepper(), isWIP = true))()),
          child(Link(Page(ctl, Pages.Tab(), isWIP = false))()),
          child(Link(Page(ctl, Pages.Table(), isWIP = false))()),
          child() // Number of children needs to be even
        ),
        h2("Container"),
        <.div(
          Style.flexbox.flex.flexbox.justifyAround.flexbox.wrap,
          ^.justifyContent := "space-evenly",
          child(Link(Page(ctl, Pages.Tooltip(), isWIP = true))()),
          child(Link(Page(ctl, Pages.Tag(), isWIP = false))()),
          child(Link(Page(ctl, Pages.Toast(), isWIP = true))()),
          child(Link(Page(ctl, Pages.Well(), isWIP = false))()),
          child(Link(Page(ctl, Pages.Popover(), isWIP = false))()),
          child(Link(Page(ctl, Pages.Card(), isWIP = false))()),
          child(Link(Page(ctl, Pages.Modal(), isWIP = false))()),
          child() // Number of children needs to be even
        ),
        h2("Form"),
        <.div(
          Style.flexbox.flex.flexbox.justifyAround.flexbox.wrap,
          ^.justifyContent := "space-evenly",
          child(Link(Page(ctl, Pages.Field(), isWIP = true))()),
          child(Link(Page(ctl, Pages.TextBox(), isWIP = true))()),
          child(Link(Page(ctl, Pages.Checkbox(), isWIP = false))()),
          child(Link(Page(ctl, Pages.Radio(), isWIP = false))()),
          child(Link(Page(ctl, Pages.Dropdown(), isWIP = false))()),
          child() // Number of children needs to be even
        ),
      )
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
