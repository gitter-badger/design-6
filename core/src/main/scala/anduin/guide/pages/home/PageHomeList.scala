// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.guide.pages.home

import anduin.guide.app.main.Pages
import anduin.guide.pages.home.{PageHomeLink => Link}
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
    <.h1(Style.fontSize.px32.lineHeight.px32.fontWeight.bold, text)
  )

  private def h2(text: String): VdomElement = h(
    <.h1(Style.fontSize.px24.lineHeight.px24.fontWeight.bold, text)
  )

  private val panelStyles = TagMod(

  )

  private def render(props: Props): VdomElement = {
    val ctl = props.ctl
    <.div(
      Style.flexbox.flex.flexbox.justifyCenter,
      <.div(
        Style.flexbox.flex.flexbox.justifyCenter,
        ^.flex := "1 1 0px",
        ^.padding := "32px 0 96px",
        <.div(
          TagMod(Style.flexbox.none, ^.width := "75%"), // 1/4 / 1/3
          h1("Style"),
          Link(ctl, Pages.Color(), isWIP = false)(),
          Link(ctl, Pages.Typography(), isWIP = false)(),
          Link(ctl, Pages.Space(), isWIP = false)(),
          Link(ctl, Pages.Layout(), isWIP = false)(),
          Link(ctl, Pages.Logo(), isWIP = false)()
        )
      ),
      <.div(
        Style.flexbox.flex.flexbox.justifyAround,
        Style.backgroundColor.white,
        ^.flex := "2 1 0px",
        ^.padding := "32px 0 96px",
        ^.boxShadow := "0px 4px 16px 0px rgba(0, 0, 0, 0.06)",
        <.div(
          TagMod(Style.flexbox.none, ^.width := "37.5%"), // 1/4 / 2/3
          h1("Components"),
          Link(ctl, Pages.Button(), isWIP = false)(),
          Link(ctl, Pages.Icon(), isWIP = false)(),
          Link(ctl, Pages.Progress(), isWIP = false)(),
          Link(ctl, Pages.Menu(), isWIP = true)(),
          Link(ctl, Pages.Tree(), isWIP = true)(),
          h2("Form"),
          Link(ctl, Pages.Field(), isWIP = true)(),
          Link(ctl, Pages.Dropdown(), isWIP = false)(),
          Link(ctl, Pages.TextBox(), isWIP = true)(),
          Link(ctl, Pages.Checkbox(), isWIP = true)(),
          Link(ctl, Pages.Radio(), isWIP = true)()
        ),
        <.div(
          Style.flexbox.none,
          TagMod(Style.flexbox.none, ^.width := "37.5%"), // 1/4 / 2/3
          h2("Container"),
          Link(ctl, Pages.Tag(), isWIP = true)(),
          Link(ctl, Pages.Well(), isWIP = false)(),
          Link(ctl, Pages.Card(), isWIP = false)(),
          Link(ctl, Pages.Table(), isWIP = false)(),
          Link(ctl, Pages.Tooltip(), isWIP = true)(),
          Link(ctl, Pages.Popover(), isWIP = true)(),
          Link(ctl, Pages.Modal(), isWIP = false)(),
          Link(ctl, Pages.Tab(), isWIP = false)(),
          Link(ctl, Pages.Stepper(), isWIP = true)()
        )
      )
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
