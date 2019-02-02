package anduin.guide.pages.home

import anduin.guide.app.main.Pages
import anduin.style.Style

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.guide.app.main.Pages.Ctl

private[home] case class PageHomeLink(
  ctl: Ctl,
  page: Pages.Page,
  isWIP: Boolean
) {
  def apply(): VdomElement = PageHomeLink.component(this)
}

private[home] object PageHomeLink {

  private type Props = PageHomeLink

  private def renderPageTitle(props: Props): VdomElement = {
    <.p(
      Style.fontSize.px24.lineHeight.px32,
      <.span(props.page.getClass.getSimpleName),
      if (props.isWIP) <.span(" \uD83D\uDEA7") else EmptyVdom
    )
  }

  private def renderPageImg(props: Props): VdomElement = {
    val src = props.page.getClass.getSimpleName.toLowerCase
    <.div(
      Style.overflow.hidden,
      ^.maxWidth := "192px",
      ^.margin := "-8px",
      <.img(
        Style.display.block.width.auto,
        ^.src := s"/imgs/home/$src.png",
        ^.height := "48px"
      )
    )
  }

  private val styles: TagMod = TagMod(
    Style.flexbox.flex.flexbox.itemsStart,
    Style.padding.ver32.border.bottom.borderWidth.px2,
    // color
    Style.color.gray8.borderColor.gray2,
    Style.hover.colorPrimary4.hover.borderPrimary3.focus.outline,
    // interaction
    Style.hover.underlineNone.transition.allWithOutline
  )

  private def render(props: Props): VdomElement = {
    props.ctl.link(props.page)(
      styles,
      <.div(Style.flexbox.fill, renderPageTitle(props)),
      <.div(Style.flexbox.none.margin.left24, renderPageImg(props)),
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
