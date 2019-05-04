package design.anduin.docs.pages.components.tag

import design.anduin.components.icon.Icon
import design.anduin.components.menu.{Menu, MenuDivider, MenuItem}
import design.anduin.components.popover.Popover
import design.anduin.components.tag.{Tag, TagColor}
import design.anduin.docs.app.main.Pages
import design.anduin.docs.components._
import anduin.mcro.Source
import design.anduin.style.Style
import japgolly.scalajs.react.Callback
import japgolly.scalajs.react.vdom.html_<^._

object PageTag {

  private def renderColors(colors: List[TagColor]) = {
    val nodes = colors.toReactFragment { color =>
      val tag = Tag(color = color)(color.getClass.getSimpleName)
      <.div(Style.margin.right8, tag)
    }
    ExampleSimple()(<.div(Style.flexbox.flex, nodes))
  }

  private val sampleFileDetail: VdomElement = {
    Menu()(
      <.div(
        Style.flexbox.flex.padding.all8,
        <.div(Style.margin.right8, Icon(name = Icon.File.Pdf, size = Icon.Size.Px32)()),
        <.div(
          Style.lineHeight.px16,
          <.p("Sample.pdf"),
          <.p(Style.color.gray7.fontSize.px11, "42KB")
        )
      ),
      MenuDivider()(),
      MenuItem(icon = Some(Icon.Glyph.Eye))("Preview"),
      MenuItem(icon = Some(Icon.Glyph.Download))("Download")
    )
  }

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Tag", Some(Tag))(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        """
          |Tags wrap words in compact boxes for better recognition:
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        Tag()("In Progress")
      }))(),
      Markdown(
        """
          |# Label
          |
          |```scala
          |children: String
          |```
          |
          |The label of a tag is defined via the usual `children` prop.
          |However, instead of `VdomNode*`, Tags allow only a single `String`
          |as its "children".
          |
          |In practice, this should be all you need, as additional content
          |types should only be provided via relevant props, such as
          |[`icon`](#icon) or [`close`](#close).
          |""".stripMargin
      )(),
      Markdown(
        """
          |## Truncate
          |
          |In general, try to keep tags' labels short and simple. When it is
          |unavoidable, like to represent users' custom input, a simple [CSS
          |truncate][r] will automatically happen to avoid overflow:
          |
          |[r]: https://css-tricks.com/snippets/css/truncate-string-with-ellipsis/
          |
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Tag()("wolfgang_amadeus_mozart@gmail.com")
      }))(),
      Markdown(
        """
          |## Icon
          |
          |```scala
          |icon: Option[Icon.Name] = None
          |```
          |
          |Tags support having a left icon via the `icon` prop:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Tag(icon = Some(Icon.Glyph.Lock))("Locked")
      }))(),
      Markdown(
        """
          |# Color
          |
          |```scala
          |color: TagColor = Tag.Light.Gray
          |```
          |
          |The `color` prop defines the colors of a tag. There are 2 sets of
          |colors to choose from:
          |""".stripMargin
      )(),
      Markdown(
        """
          |## Light
          |
          |`Tag.Light` provides lightweight appearances:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Tag(color = Tag.Light.Success)("Success")
      }))(),
      Markdown(
        """
          |There are 5 colors available in this set:
          |""".stripMargin
      )(), {
        import Tag.Light._
        renderColors(List(Gray, Primary, Success, Warning, Danger))
      },
      Markdown(
        """
          |## Bold
          |
          |`Tag.Bold` is a stronger alternative:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Tag(color = Tag.Bold.Success)("Success")
      }))(),
      Markdown(
        """
          |Similar to `Light`, `Bold` also has 5 colors:
          |""".stripMargin
      )(), {
        import Tag.Bold._
        renderColors(List(Gray, Primary, Success, Warning, Danger))
      },
      Markdown(
        """
          |# Target
          |
          |```scala
          |target: TagTarget = Tag.Target.None
          |```
          |
          |The `target` prop allows users to interact with a tag, like click to
          |see more information or navigate to another page. There are 3
          |options to choose from:
          |
          |## None
          |
          |`Tag.Target.None` is the default value, which simply renders a
          |static text. This is usually used for flags or simple statuses.
          |
          |## Button
          |
          |```scala
          |case class Button(onClick: Callback)
          |```
          |
          |`Tag.Target.Button` renders the tag as a [`button`][ref] element,
          |allow attaching event listeners such as an `onClick` callback.
          |This can be used to show additional information on a popover:
          |
          |[ref]: https://developer.mozilla.org/en-US/docs/Web/HTML/Element/button
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        Popover(
          renderTarget = (toggle, _) => {
            Tag(
              target = Tag.Target.Button(toggle),
              icon = Option(Icon.Glyph.FilePdf)
            )("Sample.pdf")
          },
          renderContent = _ => sampleFileDetail
        )()
      }))(),
      Markdown(
        """
          |## Link
          |
          |```scala
          |case class Link(href: String)
          |```
          |
          |`Tag.Target.Link` renders the tag as an [`a`][ref] element, allow
          |creating link to other places:
          |
          |[ref]: https://developer.mozilla.org/en-US/docs/Web/HTML/Element/a
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        Tag(
          target = Tag.Target.Link("https://anduin.design")
        )("anduin.design ↗")
      }))(),
      Markdown(
        """
          |# Close
          |
          |```scala
          |close: Option[Callback] = None
          |```
          |
          |Tags can have a close/remove button on the right side. This button
          |will be displayed when its handler/callback is provided at the
          |`close` prop:
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        Tag(
          close = Some(Callback.alert("Closed"))
        )("Closable tag")
      }))(),
      Markdown(
        """
          |When tags are used to represent a list (e.g. a multiple email
          |input), this allows users to easily remove an item:
        """.stripMargin
      )(),
      ExampleSimple()(TagExampleClose()()),
      Markdown(
        """
          |The `close` prop also works well with an interactive `target`
          |(i.e. [`Button`](#button) or [`Link`](#link)). The hit areas will
          |not overlap each other:
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        Tag(
          target = Tag.Target.Button(Callback.alert("Click on body")),
          close = Some(Callback.alert("Clicked on close"))
        )("Sample")
      }))()
    )
  }
}
