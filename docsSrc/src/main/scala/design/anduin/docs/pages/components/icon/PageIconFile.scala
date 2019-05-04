package design.anduin.docs.pages.components.icon

import design.anduin.components.icon.Icon
import design.anduin.components.icon.Icon.File._
import design.anduin.docs.app.main.Pages
import design.anduin.docs.components._
import anduin.mcro.Source
import design.anduin.style.Style
import japgolly.scalajs.react.vdom.html_<^._

object PageIconFile {

  private def renderIcon(name: Icon.Name): VdomElement = {
    <.div(
      ^.key := name.getClass.getSimpleName,
      Style.flexbox.none.padding.ver16,
      ^.width := "25%",
      <.div(Style.width.maxContent, IconSizeSample(name)())
    )
  }

  private def renderIcons(names: Icon.Name*): VdomElement = {
    <.div(Style.flexbox.flex.flexbox.wrap, names.toVdomArray(renderIcon))
  }

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("File Icons")(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        s"""
           |`Icon.File` contains [Icon's names] that are specially designed for
           |[files and folders][ff]:
           |
           |[Icon's names]: ${ctl.urlFor(Pages.Icon("#name")).value}
           |[ff]: ${ctl.urlFor(Pages.IconGlyph("#files-and-folders")).value}
           |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        Icon(name = Icon.File.Signed, size = Icon.Size.Px32)()
      }))(),
      Markdown(
        s"""
           |File Icons usually have several colors and include different
           |designs for each [size]:
           |
           |[size]: ${ctl.urlFor(Pages.Icon("#size")).value}
           |""".stripMargin
      )(),
      <.div(
        Style.padding.ver16,
        renderIcons(Pdf, Word, Text, Archive),
        renderIcons(Generic, Categorized, Draft, Redline, Final, Signed),
        renderIcons(Folder, FolderEmpty, Box, BoxEmpty)
      ),
      Markdown(
        """
          |Unlike Glyph, the colors of File Icons are fixed and cannot be
          |changed by their parent.
          |""".stripMargin
      )(),
      Markdown(
        """
          |## By Extension
          |
          |```scala
          |Icon.File.ByExtension(extension: String)
          |```
          |
          |The `ByExtension` case class returns the correct icon for an
          |extension. It accepts both the extension or the whole file name:
          |""".stripMargin
      )(),
      ExampleRich(Source.annotate({
        <.div(
          Style.flexbox.flex,
          Icon(name = Icon.File.ByExtension("pdf"), size = Icon.Size.Px32)(),
          Icon(name = Icon.File.ByExtension("sample.docx"), size = Icon.Size.Px32)(),
          Icon(name = Icon.File.ByExtension("foo.xyz"), size = Icon.Size.Px32)(),
        )
      }))()
    )
  }
}
