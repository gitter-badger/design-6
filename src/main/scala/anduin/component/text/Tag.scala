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
    val clear: Style
    val solid: Style
  }
  case object ColorWhite extends Color {
    val clear: Style = Style.backgroundColor.gray3.color.gray7
    val solid: Style = Style.backgroundColor.gray7.color.white
  }
  case object ColorPrimary extends Color {
    val clear: Style = Style.backgroundColor.primary1.color.primary5
    val solid: Style = Style.backgroundColor.primary4.color.white
  }
  case object ColorSuccess extends Color {
    val clear: Style = Style.backgroundColor.success1.color.success5
    val solid: Style = Style.backgroundColor.success4.color.white
  }
  case object ColorWarning extends Color {
    val clear: Style = Style.backgroundColor.warning1.color.warning5
    val solid: Style = Style.backgroundColor.warning4.color.white
  }
  case object ColorDanger extends Color {
    val clear: Style = Style.backgroundColor.danger1.color.danger5
    val solid: Style = Style.backgroundColor.danger4.color.white
  }

  def render(props: Props, children: PropsChildren): VdomElement = {
    <.div(
      if (props.isSolid) props.color.solid else props.color.clear,
      Style.padding.hor8.borderRadius.px2.width.maxContent,
      Style.fontWeight.medium.fontSize.px12.whiteSpace.noWrap,
      children
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
