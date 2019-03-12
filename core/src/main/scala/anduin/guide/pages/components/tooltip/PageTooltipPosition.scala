package anduin.guide.pages.components.tooltip

import anduin.component.portal.PortalPosition
import anduin.component.portal.PortalPosition._
import anduin.component.tooltip.Tooltip
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class PageTooltipPosition() {
  def apply(): VdomElement = PageTooltipPosition.component(this)
}

object PageTooltipPosition {
  private type Props = PageTooltipPosition

  private val positions = List(
    List(RightTop, BottomLeft, BottomCenter, BottomRight, LeftTop),
    List(RightCenter, LeftCenter),
    List(RightBottom, TopLeft, TopCenter, TopRight, LeftBottom)
  )

  private def renderOne(position: PortalPosition): VdomElement = {
    val name = position.getClass.getSimpleName
    <.div(
      Style.flexbox.none.width.pc20.flexbox.flex,
      name match {
        case s: String if s.startsWith("Left")  => Style.flexbox.justifyStart
        case s: String if s.startsWith("Right") => Style.flexbox.justifyEnd
        case _                                  => Style.flexbox.justifyCenter
      },
      ^.key := name,
      Tooltip(
        renderTarget = <.div(
          Style.background.gray3.height.px20.padding.hor4,
          Style.fontWeight.semiBold.fontSize.px11.borderRadius.px2,
          name
        ),
        renderContent = () => "Content",
        position = position
      )()
    )
  }

  private def renderRow(tuple: (List[PortalPosition], Int)): VdomElement = {
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
