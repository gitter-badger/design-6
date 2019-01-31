package anduin.guide.pages.home

import anduin.guide.app.main.Pages
import anduin.style.Style

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

private[home] case class PageLink(
  status: PageLink.Status,
  ctl: Pages.Ctl,
  page: Pages.Page,
  description: String
) {
  def apply(): VdomElement = PageLink.component(this)
}

private[home] object PageLink {

  private type Props = PageLink

  sealed trait Status { def isBad: Boolean }
  object Status {
    object Good extends Status { def isBad: Boolean = false }
    object Bad extends Status { def isBad: Boolean = true }
  }

  private def renderPageStatus(props: Props): VdomNode = {
    if (props.status.isBad) <.span("\uD83D\uDEA7") else EmptyVdom
  }

  private def renderPageTitle(props: Props): VdomElement = {
    <.p(
      Style.fontSize.px24.lineHeight.px32,
      <.span(props.page.getClass.getSimpleName),
      <.span(Style.color.gray4, " ", props.description)
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

  private def render(props: Props): VdomElement = {
    props.ctl.link(props.page)(
      Style.flexbox.flex.flexbox.itemsStart,
      Style.backgroundColor.white.color.gray8,
      Style.padding.ver32.border.bottom.borderColor.gray3.borderWidth.px2,
      Style.position.relative,
      // ===
      Style.hover.colorPrimary4.hover.underlineNone.hover.borderPrimary3,
      Style.focus.outline.transition.allWithOutline,
      // ===
      <.div(
        Style.position.absolute,
        TagMod(^.right := "calc(100% + 8px)", ^.top := "32px"),
        renderPageStatus(props)
      ),
      <.div(Style.flexbox.fixed, renderPageTitle(props)),
      <.div(Style.flexbox.none.margin.left24, renderPageImg(props)),
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
