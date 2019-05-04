package design.anduin.docs.pages.components.portal

import design.anduin.components.portal.PortalPosition
import design.anduin.components.portal.PortalPosition._
import design.anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class PagePortalPosition(
  render: PortalPosition => VdomElement,
  hint: String
) {
  def apply(): VdomElement = PagePortalPosition.component(this)
}

object PagePortalPosition {
  private type Props = PagePortalPosition

  private val positions = List(
    List(RightTop, BottomLeft, BottomCenter, BottomRight, LeftTop),
    List(RightCenter, LeftCenter),
    List(RightBottom, TopLeft, TopCenter, TopRight, LeftBottom)
  )

  private def renderOne(props: Props)(position: PortalPosition): VdomElement = {
    val name = position.getClass.getSimpleName
    <.div(
      Style.flexbox.none.width.pc20.flexbox.flex,
      name match {
        case s: String if s.startsWith("Left")  => Style.flexbox.justifyStart
        case s: String if s.startsWith("Right") => Style.flexbox.justifyEnd
        case _                                  => Style.flexbox.justifyCenter
      },
      ^.key := name,
      props.render(position)
    )
  }

  private def renderRow(props: Props)(
    tuple: (List[PortalPosition], Int)
  ) : VdomElement = {
    val (positions, index) = tuple
    <.div(
      Style.flexbox.flex.flexbox.justifyBetween,
      TagMod.when(index != 0)(Style.margin.top16),
      ^.key := positions.headOption.fold("")(_.getClass.getSimpleName),
      positions.toVdomArray(renderOne(props))
    )
  }

  private def renderHint(props: Props) = {
    <.p(
      Style.position.absolute.position.pinAll.zIndex.idx0,
      Style.width.maxContent.height.maxContent.margin.allAuto,
      props.hint
    )
  }

  private def render(props: Props) = {
    val nodes = positions.zipWithIndex.toVdomArray(renderRow(props))
    <.div(Style.position.relative, renderHint(props), nodes)
  }

  private val component = ScalaFnComponent(render)
}
