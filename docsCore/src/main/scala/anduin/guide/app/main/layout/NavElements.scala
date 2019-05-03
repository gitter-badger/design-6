package anduin.guide.app.main.layout

import anduin.component.toggle.Toggle
import anduin.guide.app.main.Pages
import anduin.guide.app.main.Pages.Page
import anduin.style.Style
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object NavElements {

  case class Props(ctl: Pages.Ctl, page: Page)

  sealed trait PageTarget
  object PageTarget {
    case class Internal(page: Page) extends PageTarget
    case class External(url: String) extends PageTarget
    object None extends PageTarget
  }

  case class Title(
    text: String,
    target: PageTarget,
    isExpanded: Option[Page => Boolean] = None
  )

  // ===

  private def renderLiIcon(children: VdomNode, isExpanded: Boolean): VdomElement = {
    val text = if (children == EmptyVdom) " " else if (isExpanded) "−" else "+"
    // don't use left here as the link will be lost in the middle space
    <.div(Style.position.absolute, ^.right := "100%", text, " ")
  }

  // This is similar to ScalaJS React's setOnLinkClick but we need it as a
  // callback for composition purpose
  private def setPage(page: Page, props: Props, e: ReactMouseEvent): Callback = {
    CallbackOption.unless(ReactMouseEvent targetsNewTab_? e) >> props.ctl.setEH(page)(e)
  }

  private def getColor(current: Page, target: PageTarget): TagMod = {
    target match {
      case PageTarget.None        => Style.color.inherit
      case _: PageTarget.External => Style.color.inherit.borderColor.transparent
      case internal: PageTarget.Internal =>
        if (internal.page.getClass.getSimpleName == current.getClass.getSimpleName) {
          Style.color.gray7.borderColor.gray3
        } else {
          Style.color.inherit.borderColor.transparent
        }
    }
  }

  private def renderLi(
    title: Title,
    children: VdomNode,
    props: Props
  )(toggle: Callback, isExpanded: Boolean): VdomElement = {
    val common = TagMod(
      Style.position.relative.color.hoverPrimary4.transition.all,
      getColor(props.page, title.target),
      renderLiIcon(children, isExpanded),
      title.text
    )
    val node = title.target match {
      case PageTarget.None => <.button(^.onClick --> toggle, common)
      case _ =>
        val linkMod = title.target match {
          case internal: PageTarget.Internal => TagMod(
            ^.href := props.ctl.urlFor(internal.page).value,
            ^.onClick ==> (e => setPage(internal.page, props, e) >> toggle)
          )
          case external: PageTarget.External => TagMod(
            ^.href := external.url,
            ^.target.blank,
            ^.rel := "noreferrer noopener"
          )
          case _ => TagMod.empty
        }
        val suffix = title.target match {
          case _: PageTarget.External => " ↗"
          case _ => ""
        }
        <.a(
          Style.textDecoration.hoverNone.borderColor.hoverPrimary3,
          Style.border.bottom.borderWidth.px2,
          linkMod,
          common,
          suffix
        )
    }
    <.li(
      Style.padding.ver8,
      node,
      TagMod.when(isExpanded) { children }
    )
  }

  def li(title: Title, children: VdomNode = EmptyVdom)(implicit props: Props): VdomElement = {
    Toggle(
      isExpanded = title.isExpanded.fold(false)(_(props.page)),
      render = renderLi(title, children, props)
    )()
  }

  // ===

  def h(content: String): VdomElement = {
    <.li(
      Style.color.gray4.padding.top24.padding.bottom8,
      ^.fontSize := "16px",
      content
    )
  }

  // ===

  def ul(content: VdomNode*): VdomElement = {
    <.ul(
      Style.listStyle.none.padding.left20.padding.ver8,
      React.Fragment(content: _*)
    )
  }

}
