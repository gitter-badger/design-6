package design.anduin.docs.pages.components.playground

import japgolly.scalajs.react.Callback
import design.anduin.docs.app.main.Pages
import design.anduin.docs.components._
import design.anduin.macros.Source
import japgolly.scalajs.react.vdom.html_<^._
import design.anduin.components.button.Button
import design.anduin.components.card.Card
import design.anduin.components.dropdown.Dropdown
import design.anduin.components.field.Field
import design.anduin.components.icon.Icon
import design.anduin.components.input.checkbox.Checkbox
import design.anduin.components.input.radio.Radio
import design.anduin.components.input.textbox.TextBox
import design.anduin.components.menu.{Menu, MenuItem}
import design.anduin.components.popover.Popover
import design.anduin.components.portal.PortalPosition
import design.anduin.components.progressindicators.BarIndicator
import design.anduin.components.tab.Tab
import design.anduin.components.table.Table
import design.anduin.components.tag.Tag
import design.anduin.components.tooltip.Tooltip
import design.anduin.components.tree.Tree
import design.anduin.components.well.Well
import design.anduin.style.Style

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
      ExampleRich(Source.annotate({
        // Structure of this implementation:
        // div
        // - body
        // - link <~ this is what you asked
        //   - icon
        //   - title
        //   - arrow

        // This spanBlock is not a part of the structure. It's purely a helper
        // - We can't use "div" because we will be inside an "a". We can only
        //   use "span".
        // - However we should still have the "block" display
        val spanBlock = <.span(Style.display.block)
        val icon = spanBlock(
          Icon(name = Icon.File.Final, size = Icon.Size.Px32)()
        )
        val title = spanBlock(
          Style.margin.top8,
          Style.fontSize.px11.fontWeight.bold.lineHeight.px16,
          // Break line at "\n"
          Style.whiteSpace.preWrap,
          "Term Sheet\nWorkspace"
        )
        val arrow = spanBlock(
          Style.opacity.pc50.margin.top8,
          Icon(name = Icon.Glyph.ArrowRight, size = Icon.Size.Px32)()
        )
        val styles = TagMod(
          Style.color.gray0.padding.hor8,
          Style.outline.focusLight.transition.allWithOutline,
          ^.width := "120px",
          // Use parent's height (this will stretch the link to be as tall as
          // the card's body on the left side)
          Style.height.pc100,
          // No underline when hover
          Style.textDecoration.hoverNone,
          // Center its children, both x and y axis
          Style.flexbox.flex.flexbox.column,
          Style.flexbox.itemsCenter.flexbox.justifyCenter,
          // Shamefully control background with CSS
          ^.cls := "shame-term-sheet-bg"
        )
        val link = <.a(^.href := "https://anduin.design", styles, icon, title, arrow)
        val body = {
          <.p(
            Style.height.px256.padding.all16,
            """
              |This is the card's body. This should be filled with tasks. The
              |height this body will be the height of the whole card. To demo
              |we set it to "256px" explicitly, but in reality it should be
              |based on the content (i.e. how many tasks are there)
            """.stripMargin
          )
        }
        <.div(
          Style.flexbox.flex.overflow.hidden,
          Style.borderRadius.px4.shadow.px8,
          <.div(Style.flexbox.fill, body),
          <.div(Style.flexbox.none, link)
        )
      }))(),
      ExampleSimple()({
        <.div(
          Style.flexbox.flex,
          Button(style = Button.Style.Full(color = Button.Color.Gray0))("Cancel"),
          <.div(Style.margin.right8),
          Button(style = Button.Style.Full(color = Button.Color.Primary))("Sign")
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
            position = PortalPosition.LeftCenter
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
        <.div(width, TextBox(value = "john@anduin.co", onChange = _ => Callback.empty)())
      }),
      ExampleSimple()({
        <.div(
          width,
          TypedDropdown(
            value = Some("Engineer"),
            options = List("Engineer", "Doctor", "Student", "Designer").map(a => Dropdown.Opt(a)),
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
          Tag(Tag.Light.Gray)("9+"),
          <.div(Style.margin.right8),
          Tag(Tag.Light.Primary)("42"),
          <.div(Style.margin.right8),
          Tag(Tag.Light.Primary)("DONE"),
        )
      }),
      ExampleSimple()({
        <.div(
          Style.flexbox.flex,
          Checkbox(isChecked = false)(),
          <.div(Style.margin.right24),
          Checkbox(isChecked = true)("Remember"),
        )
      }),
      ExampleSimple()({
        <.div(
          Style.flexbox.flex,
          Radio(isChecked = false, Callback.empty)(),
          <.div(Style.margin.right24),
          Radio(isChecked = true, Callback.empty)("Remember"),
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
