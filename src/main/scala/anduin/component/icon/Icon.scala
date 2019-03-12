// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.icon

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.svg_<^._
// scalastyle:on underscore.import

final case class Icon(
  name: Icon.Name,
  size: Icon.Size = Icon.Size.Px16
) {
  def apply(): VdomElement = Icon.component(this)
}

object Icon {

  private type Props = Icon

  sealed trait Size {
    def px: Int
    private def pxS: String = px.toString
    // "dimension" is the target width and height to display the icon at
    // - ie. this is the size that the icon will be rendered
    final def dimension: TagMod = TagMod(^.width := pxS, ^.height := pxS)
    // "viewBox" is the original size that the icon's name is designed at
    // - ie. this is the size that the icon is designed
    final def viewBox: TagMod = ^.viewBox := s"0 0 $pxS $pxS"
  }

  object Size {
    object Px16 extends Size { val px = 16 }
    object Px24 extends Size { val px = 24 }
    object Px32 extends Size { val px = 32 }
    case class Custom(px: Int) extends Size
  }

  private[icon] case class SizeMod(size: Size, mod: TagMod)
  trait Name { def sizeMods: Seq[SizeMod] }

  val File: IconFile.type = IconFile
  val Glyph: IconGlyph.type = IconGlyph
  val Nego: IconNego.type = IconNego
  val Product: IconProduct.type = IconProduct

  case class Folder(
    color: IconFolder.Color = Icon.Folder.Brown,
    glyph: Option[IconGlyph] = None
  ) extends IconFolder
  object Folder {
    val Brown: IconFolder.Color = IconFolder.Color.Brown
    val Gray: IconFolder.Color = IconFolder.Color.Gray
    val Red: IconFolder.Color = IconFolder.Color.Red
    val Orange: IconFolder.Color = IconFolder.Color.Orange
    val Green: IconFolder.Color = IconFolder.Color.Green
    val Blue: IconFolder.Color = IconFolder.Color.Blue
  }

  // getContent must return 3 things:
  // - SVG's body (props.name > closest SizeMod.mod)
  // - SVG's viewBox (props.name > closest SizeMod.size.viewBox)
  // - SVG's width & height (props.size.dimension)
  private def getContent(props: Props): TagMod = {
    val closestSizeMod = props.name.sizeMods.foldLeft[(Int, Option[SizeMod])] {
      (Int.MaxValue, None)
    } { (prev, sizeMod) =>
      val distance = Math.abs(props.size.px - sizeMod.size.px)
      if (distance < prev._1) (distance, Some(sizeMod)) else prev
    }
    closestSizeMod._2.fold(TagMod.empty) { sizeMod =>
      TagMod(sizeMod.mod, sizeMod.size.viewBox, props.size.dimension)
    }
  }

  private def render(props: Props): VdomElement = {
    <.svg(
      ^.xmlns := "http://www.w3.org/2000/svg",
      // style
      Style.display.block, // https://codepen.io/dvkndn/pen/wmQmbm
      ^.fill := "none",
      // content
      getContent(props)
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
