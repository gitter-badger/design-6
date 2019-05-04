// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.docs.app.main.layout

import design.anduin.docs.app.main.Pages
import design.anduin.docs.app.main.Pages._
import design.anduin.docs.app.main.Pages.{Layout => PagesLayout}
import design.anduin.style.Style
import design.anduin.docs.app.main.layout.NavElements.PageTarget._

// scalastyle:off underscore.import
import design.anduin.docs.app.main.layout.NavElements._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class LayoutNav(ctl: Pages.Ctl, page: Page) {
  def apply(): VdomElement = LayoutNav.component(this)
}

object LayoutNav {

  private type Props = LayoutNav

  private def render(props: Props) = {

    implicit val implicitProps: NavElements.Props =
      NavElements.Props(props.ctl, props.page)

    <.div(
      Style.color.gray4.color.hoverGray7.transition.all,
      // The padding was defined here instead of the parent to increase the
      // hit area for the above hover effect
      ^.padding := "0 56px",
      // eliminate the first ul
      ^.marginLeft := "-20px",
      ul(
        li(
          Title("Home", Internal(Home))
        ),
        h("Guide"),
        li(
          Title("Style", Internal(Pages.Style()), Some(_.isInstanceOf[StyleT])),
          ul(
            li(Title("Color", Internal(Color()))),
            li(Title("Typography", Internal(Typography()))),
            li(Title("Layout", Internal(PagesLayout()))),
            li(Title("Logo", Internal(Logo()))),
          )
        ),
        li(
          Title("Component", Internal(Component()), Some(_.isInstanceOf[ComponentT])),
          ul(
            li(
              Title("Button", Internal(Button()), Some(_.isInstanceOf[ButtonT])),
              ul(li(Title("Box", Internal(ButtonBox())))),
            ),
            li(
              Title("Icon", Internal(Icon()), Some(_.isInstanceOf[IconT])),
              ul(
                li(Title("Glyph", Internal(IconGlyph()))),
                li(Title("Negotiation", Internal(IconNego()))),
                li(Title("File", Internal(IconFile()))),
                li(Title("Folder", Internal(IconFolder()))),
                li(Title("Product", Internal(IconProduct()))),
              ),
            ),
            li(Title("Progress", Internal(Progress()))),
            li(Title("Tab", Internal(Tab()))),
            li(Title("Table", Internal(Table()))),
            h("Container"),
            li(Title("Tooltip", Internal(Tooltip()))),
            li(Title("Tag", Internal(Tag()))),
            li(Title("Well", Internal(Well()))),
            li(Title("Popover", Internal(Popover()))),
            li(Title("Card", Internal(Card()))),
            li(Title("Modal", Internal(Modal()))),
            li(Title("Portal Utilities", Internal(Portal()))),
            h("Form"),
            li(Title("Checkbox", Internal(Checkbox()))),
            li(Title("Dropdown", Internal(Dropdown()))),
            li(Title("Radio", Internal(Radio()))),
//            li(
//              Title("TextBox", Internal(TextBox()), Some(_.isInstanceOf[TextBoxT])),
//              ul(
//                li(Title("Value", Internal(TextBoxValue()))),
//                li(Title("Appearance", Internal(TextBoxAppearance()))),
//              ),
//            ),
          )
        ),
        h(""),
        li(Title("Github", External("https://github.com/anduintransaction/design"))),
        li(Title("Work with us", External("https://www.anduintransact.com/careers"))),
      )
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
