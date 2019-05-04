package design.anduin.docs.pages.components.card

import design.anduin.docs.components._
import design.anduin.components.card.Card
import design.anduin.components.icon.Icon
import design.anduin.docs.app.main.Pages
import anduin.mcro.Source
import design.anduin.style.Style
import japgolly.scalajs.react.vdom.html_<^._

object PageCard {
  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Card", Some(Card))(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        """
          |Card is a simple container to group content:
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        val card = Card()("Card's content")
        <.div(card)
      }), isBgGray = true)(),
      Markdown(
        """
          |Due to the appearance of a Card, avoid using it over a white
          |background as it will result in poor visual contrast. Instead,
          |Card should be used over gray background, especially `gray-1` and
          |`gray-2`.
          |
        """.stripMargin
      )()
    )
  }
}
