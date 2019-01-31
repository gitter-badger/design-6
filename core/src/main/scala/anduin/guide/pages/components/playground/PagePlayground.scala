package anduin.guide.pages.components.playground

import japgolly.scalajs.react.Callback

import anduin.guide.app.main.Pages
import anduin.guide.components._
import anduin.mcro.Source
import japgolly.scalajs.react.vdom.html_<^._

import anduin.component.button.Button
import anduin.component.card.Card
import anduin.component.dropdown.Dropdown
import anduin.component.field.Field
import anduin.component.icon.Icon
import anduin.component.input.checkbox.Checkbox
import anduin.component.input.radio.Radio
import anduin.component.input.textbox.TextBox
import anduin.component.menu.{Menu, MenuItem}
import anduin.component.popover.Popover
import anduin.component.portal.PositionLeftCenter
import anduin.component.progressindicators.BarIndicator
import anduin.component.tab.Tab
import anduin.component.table.Table
import anduin.component.tag.Tag
import anduin.component.tooltip.Tooltip
import anduin.component.tree.Tree
import anduin.component.well.Well
import anduin.style.Style

object PagePlayground {

  private val width = Style.width.px128

  private case class Node(title: String, children: Seq[Node])
  private val TypedDropdown = (new Dropdown[String])()
  private val TypedTable = (new Table[(String, String)])()
  private val TypedTree = (new Tree[Node])()

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Playground", None)(),
      Toc(headings = Source.getTocHeadings)(),
      ExampleSimple()({
        <.div(
          Style.flexbox.flex,
          Button(style = Button.Style.Full(color = Button.Color.White))("Cancel"),
          <.div(Style.margin.right8),
          Button(style = Button.Style.Full(color = Button.Color.Blue))("Sign")
        )
      }),
      ExampleSimple()({
        <.div(
          Style.flexbox.flex.color.gray7,
          Icon(name = Icon.Glyph.Reply)(),
          <.div(Style.margin.right16),
          Icon(name = Icon.Glyph.Star)(),
          <.div(Style.margin.right16),
          Icon(name = Icon.Glyph.DataRoom)(),
          <.div(Style.margin.right16),
          Icon(name = Icon.Glyph.Vault)(),
        )
      }),
      ExampleSimple()({
        <.div(width, Style.color.primary4, BarIndicator()())
      }),
      ExampleSimple(bgColor = ExampleSimple.BgColor.Gray2)({
        <.div(width, Card()(<.span(Style.fontWeight.semiBold, "Deal Report")))
      }),
      ExampleSimple()({
        <.div(width, Well()("Message"))
      }),
      ExampleSimple()({
        <.div(
          width,
          Style.flexbox.flex.flexbox.justifyEnd,
          Tooltip(
            renderTarget = Button(style = Button.Style.Minimal(icon = Some(Icon.Glyph.Bold)))(),
            renderContent = () => "Bold (⌘B)",
            position = PositionLeftCenter
          )()
        )
      }),
      ExampleSimple()({
        <.div(
          width,
          Field(id = "a", layout = Field.Layout.Hor(), label = Some("Name"))(
            TextBox(value = "John", onChange = _ => Callback.empty)()
          )
        )
      }),
      ExampleSimple()({
        Popover(
          renderTarget = (open, _) => Button(onClick = open)("open"),
          renderContent = _ => {
            <.div(
              ^.width := "126px",
              ^.height := "30px",
              Style.flexbox.flex.flexbox.itemsCenter.flexbox.justifyCenter,
              "Target day"
            )
          }
        )()
      }),
      ExampleSimple()({
        Popover(
          renderTarget = (open, _) => Button(onClick = open)("open"),
          renderContent = _ => {
            Menu()(
              MenuItem(icon = Some(Icon.Glyph.Sign))("Sign"),
              MenuItem(icon = Some(Icon.Glyph.Download))("Download")
            )
          }
        )()
      }),
      ExampleSimple()({
        <.div(width, TextBox(value = "John", onChange = _ => Callback.empty)())
      }),
      ExampleSimple()({
        <.div(
          width,
          TypedDropdown(
            value = Some("John Doe"),
            options = List.empty[Dropdown.Opt[String]],
            onChange = _ => Callback.empty,
            getValueString = identity,
            isFullWidth = true
          )()
        )
      }),
      ExampleSimple()({
        <.div(
          width,
          TypedTable(
            columns = List(
              Table.Column("Id", r => Table.Cell(r._1), Table.ColumnOrdering(_._1)),
              Table.Column("Name", r => Table.Cell(r._2))
            ),
            rows = List(("1", "John")),
            getKey = _._1,
          )()
        )
      }),
      ExampleSimple()({
        <.div(
          Style.flexbox.flex,
          Tag()("9+"),
          <.div(Style.margin.right8),
          Tag(color = Tag.ColorBlue)("42"),
          <.div(Style.margin.right8),
          Tag(color = Tag.ColorBlue)("DONE"),
        )
      }),
      ExampleSimple()({
        <.div(
          Style.flexbox.flex,
          Checkbox(isChecked = false)(),
          <.div(Style.margin.right16),
          Checkbox(isChecked = true)("Auto-save"),
        )
      }),
      ExampleSimple()({
        <.div(
          Style.flexbox.flex,
          Radio("a", "a", _ => Callback.empty, isChecked = false)(),
          <.div(Style.margin.right8),
          Radio("a", "b", _ => Callback.empty, isChecked = true)("Orange"),
        )
      }),
      ExampleSimple()({
        TypedTree(
          node = Node(
            "Term sheet",
            List(
              Node("Clean", List(Node("Version 1", List.empty))),
              Node("Signed", List.empty)
            )
          ),
          loader = Tree.Loader.Sync(_.children),
          getKey = _.title,
          render = props => Some(<.p(Style.lineHeight.px24, props.node.title))
        )()
      }),
      ExampleSimple()({
        val panels = List(
          Tab.Panel("First", () => "First tab"),
          Tab.Panel("Last", () => "Second tab"),
        )
        <.div(width, Tab(panels = panels)())
      })
    )
  }
}
