package anduin.guide.pages.home

import anduin.guide.app.main.Pages
import anduin.guide.components._
import anduin.mcro.Source
import anduin.guide.pages.home.PageLink.Status._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.style.Style

object PageHome {

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Anduin Design", None)(),
      Toc(headings = Source.getTocHeadings)(),
      <.div(Style.height.px32),
      Markdown(
        """
          |# Style
        """.stripMargin
      )(),
      PageLink(Good, ctl, Pages.Color(), "/ Text, Background & Border")(),
      PageLink(Good, ctl, Pages.Typography(), "/ Typeface & Font")(),
      PageLink(Good, ctl, Pages.Space(), "/ Padding & Margin")(),
      PageLink(Good, ctl, Pages.Layout(), "with Flex Box")(),
      PageLink(Good, ctl, Pages.Logo(), "")(),
      <.div(Style.height.px40),
      Markdown(
        """
          |# Components
        """.stripMargin
      )(),
      PageLink(Good, ctl, Pages.Button(), "lets users take actions")(),
      PageLink(Good, ctl, Pages.Icon(), "symbolizes actions & objects")(),
      PageLink(Good, ctl, Pages.ProgressIndicator(), "")(),
      PageLink(Bad, ctl, Pages.Menu(), "lists actions in a popover")(),
      PageLink(Bad, ctl, Pages.Tree(), "displays hierarchical contents")(),
      <.div(Style.height.px40),
      Markdown(
        """
          |## Container
        """.stripMargin
      )(),
      PageLink(Bad, ctl, Pages.Tag(), "for amounts & attributes")(),
      PageLink(Good, ctl, Pages.Well(), "for important messages")(),
      PageLink(Good, ctl, Pages.Card(), "for mixed content types")(),
      PageLink(Good, ctl, Pages.Table(), "for data sets")(),
      PageLink(Bad, ctl, Pages.Tooltip(), "for simple info")(),
      PageLink(Bad, ctl, Pages.Popover(), "for complex content")(),
      PageLink(Good, ctl, Pages.Modal(), "")(),
      PageLink(Good, ctl, Pages.Tab(), "")(),
      PageLink(Bad, ctl, Pages.Stepper(), "")(),
      <.div(Style.height.px40),
      Markdown(
        """
          |## Form
        """.stripMargin
      )(),
      PageLink(Bad, ctl, Pages.Field(), "groups inputs with labels")(),
      PageLink(Good, ctl, Pages.Dropdown(), "to select an option")(),
      PageLink(Bad, ctl, Pages.TextBox(), "to enter or edit text")(),
      PageLink(Bad, ctl, Pages.Checkbox(), "to choose yes or no")(),
      PageLink(Bad, ctl, Pages.Radio(), "to select an option")(),
    )
  }

}
