// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.text

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Tag(
  color: Tag.Color = Tag.ColorWhite,
  isSolid: Boolean = false
) {
  def apply(children: VdomNode*): VdomElement = {
    Tag.component(this)(children: _*)
  }
}

object Tag {

  type Props = Tag

  sealed trait Color {
    private[Tag] val normal: Style
    private[Tag] val solid: Style
  }
  case object ColorWhite extends Color {
    private[Tag] override val normal = Style.backgroundColor.gray3.color.gray7
    private[Tag] override val solid = Style.backgroundColor.gray7.color.white
  }
  case object ColorPrimary extends Color {
    private[Tag] override val normal = Style.backgroundColor.primary1.color.primary5
    private[Tag] override val solid = Style.backgroundColor.primary4.color.white
  }
  case object ColorSuccess extends Color {
    private[Tag] override val normal = Style.backgroundColor.success1.color.success5
    private[Tag] override val solid = Style.backgroundColor.success4.color.white
  }
  case object ColorWarning extends Color {
    private[Tag] override val normal = Style.backgroundColor.warning1.color.warning5
    private[Tag] override val solid = Style.backgroundColor.warning4.color.white
  }
  case object ColorDanger extends Color {
    private[Tag] override val normal = Style.backgroundColor.danger1.color.danger5
    private[Tag] override val solid = Style.backgroundColor.danger4.color.white
  }

  def render(props: Props, children: PropsChildren): VdomElement = {
    <.div(
      if (props.isSolid) props.color.solid else props.color.normal,
      Style.padding.hor8.borderRadius.px2.width.maxContent,
      Style.fontWeight.medium.fontSize.px12,
      children
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
