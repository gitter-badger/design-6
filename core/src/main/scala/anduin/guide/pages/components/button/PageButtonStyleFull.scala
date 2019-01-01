// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.guide.pages.components.button

import anduin.component.button.Button
import anduin.component.button.Button.Color
import anduin.component.icon.Icon
import anduin.guide.app.main.Pages
import anduin.guide.components.{ExampleRich, ExampleSimple, Markdown, SimpleState}
import anduin.mcro.Source
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[button] final case class PageButtonStyleFull(ctl: Pages.Ctl) {
  def apply(): VdomElement = PageButtonStyleFull.component(this)
}

private[button] object PageButtonStyleFull {

  private type Props = PageButtonStyleFull

  def getHeadings: List[(Int, String)] = Source.getTocHeadings

  private def render(props: Props): VdomElement = {
    <.div(
      Markdown(
        """
          |## Full
          |
          |```scala
          |case class Full(/* further customization */)
          |```
          |
          |Button's default style, `Full`, has the highest emphasis. They
          |have background color, border and shadow all defined.
          |
          |`Full` should be used to make your Button stands out from nearby
          |elements, or even attracts attention:
          |
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        <.div(
          Style.flexbox.flex,
          Button(
            style = Button.Style.Full(color = Button.Color.Blue)
          )("Submit"),
          <.div(Style.margin.left8),
          Button(
            style = Button.Style.Full()
          )("Cancel")
        )
      }))(),
      Markdown(
        """
          |### Color [full-color]
          |
          |```scala
          |color: Button.Color = Button.Color.White
          |```
          |
          |The `color` parameter controls Full Button's background color and
          |other color-related properties:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Button(
          style = Button.Style.Full(color = Button.Color.Blue)
        )("Submit")
      }))(),
      Markdown(
        """
          |There are 4 options to choose from, with the name of each option
          |indicates its background color. `White` is the default one:
          |
          |""".stripMargin
      )(),
      CommonColorExample(
        bgColor = ExampleSimple.BgColor.White,
        getStyle = color => Button.Style.Full(color = color),
        colors = List(Color.White, Color.Black, Color.Blue, Color.Red),
        default = Some(Color.White)
      )(),
      Markdown(
        """
          |### Color Usage
          |
          |On light background, `White` should be used for most cases, with a
          |`Blue` to highlight the primary one when necessary:
          |""".stripMargin
      )(),
      FullColorExample(ExampleSimple.BgColor.White, Color.Blue, Color.White)(),
      Markdown(
        """
          |`Black` can be used as an alternative primary on light background:
         """.stripMargin
      )(),
      FullColorExample(ExampleSimple.BgColor.White, Button.Color.Black, Button.Color.White)(),
      Markdown(
        """
          |…or as secondary ones on dark background:
         """.stripMargin
      )(),
      FullColorExample(ExampleSimple.BgColor.Gray8, Color.Blue, Color.Black)(),
      Markdown(
        """
          |`Red` should be used for destructive actions, such as archiving
          |deals. Usually only the final actions, like those in confirmation
          |Modals, need the color `Red`:
          |""".stripMargin
      )(),
      ExampleSimple()(FullArchiveExample()()),
      Markdown(
        """
          |### Size
          |
          |```scala
          |size: Button.Size = Button.Size.Fix32
          |```
          |
          |The `size` parameter controls Button's height, with matching font
          |size and padding:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Button(
          style = Button.Style.Full(size = Button.Size.Fix40)
        )("Big Button")
      }))(),
      Markdown(
        """
          |There are 2 types of `size`. The first one are fixed values,
          |named by their height in pixel. These are suitable for single-line
          |Buttons, which should be most cases:
          |""".stripMargin
      )(),
      FullSizeExample(size => Button.Style.Full(size = size))(),
      Markdown(
        """
          |The other one is `Free`, which has no pre-defined styles. It allows
          |having multiple lines or other complex layout inside Button's body:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        val content = <.span(
          Style.flexbox.flex.flexbox.column.flexbox.itemsCenter,
          Style.padding.ver8.padding.hor12,
          Icon(name = Icon.NameLightBolt)(),
          <.span(Style.margin.top4, "Strike")
        )
        Button(
          style = Button.Style.Full(size = Button.Size.Free)
        )(content)
      }))(),
      Markdown(
        """
          |Note that Button will be rendered as an `a` or a `button` tag, so
          |ensure your custom body is valid as their's children. For
          |example, [avoid having `div` inside Button's body][so].
          |
          |[so]: https://stackoverflow.com/q/12982269
          |
          |""".stripMargin
      )(),
      Markdown(
        """
          |### Width [full-width]
          |
          |By default, Button's width depends on its content, similar to
          |[`width: max-content`][csswg]. To make Button's width the same as
          |its container's, set `isFullWidth` to `true`:
          |
          |[csswg]: https://drafts.csswg.org/css-sizing/#valdef-width-max-content
          |
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        <.div(
          Style.width.px256,
          Button(
            style = Button.Style.Full(isFullWidth = true)
          )("Full Width Button")
        )
      }))(),
      Markdown(
        """
          |This allows you to define a custom width for your Button safely via
          |its container.
          |""".stripMargin
      )(),
      Markdown(
        s"""
           |### Icon [full-icon]
           |
           |```scala
           |icon: Option[Icon.Name] = None
           |```
           |
           |The `icon` parameter provides an [Icon][icon] on the left side of
           |Button's body:
           |
           |[icon]: ${props.ctl.urlFor(Pages.Icon()).value}
           |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Button(
          style = Button.Style.Full(icon = Some(Icon.NameHome))
        )("Home")
      }))(),
      Markdown(
        s"""
           |Button will be an exact square if icon is its only content (i.e.
           |Button's body is not defined):
           |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Button(
          style = Button.Style.Full(icon = Some(Icon.NameHome))
        )()
      }))(),
      Markdown(
        s"""
           |### Selected [full-selected]
           |
           |```scala
           |isSelected: Boolean = false
           |```
           |
           |Set `isSelected = true` to always show Button in its active/pressed
           |state:
           |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Button(
          style = Button.Style.Full(isSelected = true)
        )("Selected")
      }))(),
      Markdown(
        s"""
           |This is useful for on-off actions, like text formatting:
           |""".stripMargin
      )(),
      FullFormatExample()(),
      Markdown(
        s"""
           |Or to indicate the connection between elements, such as Popover's
           |content (e.g. a Menu) and its target (e.g. a Button):
           |""".stripMargin
      )(),
      FullMenuExample()(),
      Markdown(
        s"""
           |### Busy [full-busy]
           |
           |```scala
           |isBusy: Boolean = false
           |```
           |
           |Set the `isBusy` parameter to `true` to replace Button's body
           |with a process indicator:
           |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Button(
          style = Button.Style.Full(isBusy = true)
        )("Title")
      }))(),
      Markdown(
        s"""
           |When `isBusy == true`, the Button will also be
           |[disabled](#disabled) and have the appearance of its active/pressed
           |state, because in practice `isBusy` is usually set via Button's
           |`onClick`:
           |""".stripMargin
      )(),
      ExampleSimple()({
        SimpleState.Bool(
          initialValue = false,
          render = (isBusy, setIsBusy) => {
            Button(
              style = Button.Style.Full(isBusy = isBusy, icon = Some(Icon.NameDownload)),
              onClick = for {
                _ <- setIsBusy(true)
                _ <- setIsBusy(false).delayMs(3000)
              } yield ()
            )()
          }
        )()
      })
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}