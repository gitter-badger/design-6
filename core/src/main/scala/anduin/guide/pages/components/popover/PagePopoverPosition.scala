package anduin.guide.pages.components.popover

import anduin.component.icon.Icon
import anduin.component.popover.PopoverPosition._
import anduin.component.popover.{Popover, PopoverPosition}
import anduin.component.tag.Tag
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class PagePopoverPosition() {
  def apply(): VdomElement = PagePopoverPosition.component(this)
}

object PagePopoverPosition {
  private type Props = PagePopoverPosition

  private val positions = List(
    List(RightTop, BottomLeft, BottomCenter, BottomRight, LeftTop),
    List(RightCenter, LeftCenter),
    List(RightBottom, TopLeft, TopCenter, TopRight, LeftBottom)
  )

  private def renderOne(position: PopoverPosition): VdomElement = {
    val name = position.getClass.getSimpleName
    <.div(
      Style.flexbox.none.width.pc20.flexbox.flex,
      name match {
        case s: String if s.startsWith("Left")  => Style.flexbox.justifyStart
        case s: String if s.startsWith("Right") => Style.flexbox.justifyEnd
        case _                                  => Style.flexbox.justifyCenter
      },
      ^.key := name,
      Popover(
        renderTarget = (open, isOpened) => {
          val color = if (isOpened) Tag.Bold.Gray else Tag.Light.Gray
          Tag(color, target = Tag.Target.Button(open))(name)
        },
        renderContent = _ => {
          <.div(
            Style.flexbox.flex.padding.all8,
            Icon(name = Icon.File.Final, size = Icon.Size.Px32)(),
            Icon(name = Icon.File.Archive, size = Icon.Size.Px32)(),
          )
        },
        position = position
      )()
    )
  }

  private def renderRow(tuple: (List[PopoverPosition], Int)): VdomElement = {
    val (positions, index) = tuple
    <.div(
      Style.flexbox.flex.flexbox.justifyBetween,
      TagMod.when(index != 0)(Style.margin.top16),
      ^.key := positions.headOption.fold("")(_.getClass.getSimpleName),
      positions.toVdomArray(renderOne)
    )
  }

  private val hint = {
    <.p(
      Style.position.absolute.position.pinAll.zIndex.idx0,
      Style.width.maxContent.height.maxContent.margin.allAuto,
      "Click on a position to see its Popover"
    )
  }

  private val render = {
    val nodes = positions.zipWithIndex.toVdomArray(renderRow)
    <.div(Style.position.relative, hint, nodes)
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderStatic(render)
    .build
}
