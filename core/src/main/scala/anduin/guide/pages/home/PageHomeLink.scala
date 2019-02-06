package anduin.guide.pages.home

import anduin.guide.app.main.Pages
import anduin.style.Style

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

private[home] case class PageHomeLink(
  target: PageHomeLink.Target
) {
  def apply(): VdomElement = PageHomeLink.component(this)
}

private[home] object PageHomeLink {

  private type Props = PageHomeLink

  sealed trait Target
  object Target {
    case class Page(ctl: Pages.Ctl, value: Pages.Page, isWIP: Boolean) extends Target
    case class URL(title: String, value: String) extends Target
  }

  private def renderTitle(props: Props): VdomElement = {
    <.p(
      Style.fontSize.px24.lineHeight.px32,
      props.target match {
        case page: Target.Page =>
          React.Fragment(
            <.span(page.value.getClass.getSimpleName),
            if (page.isWIP) <.span(" \uD83D\uDEA7") else EmptyVdom
          )
        case url: Target.URL => <.span(url.title, " ↗")
      }
    )
  }

  // Currently only Target.Page has img
  private def renderPageImg(page: Target.Page): VdomElement = {
    val src = page.value.getClass.getSimpleName.toLowerCase
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
    Style.color.gray8.color.hoverBlue4,
    Style.borderColor.gray2.borderColor.hoverBlue3,
    Style.focus.outlineLight,
    // interaction
    Style.hover.underlineNone.transition.allWithOutline
  )

  private def render(props: Props): VdomElement = {
    val title = <.div(Style.flexbox.fill, renderTitle(props))
    props.target match {
      case page: Target.Page =>
        val img = <.div(Style.flexbox.none, renderPageImg(page))
        page.ctl.link(page.value)(styles, title, img)
      case url: Target.URL =>
        <.a(^.href := url.value, ^.target.blank, ^.rel := "noreferrer noopener", styles, title)
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
