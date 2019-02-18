// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.tag

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Tag(
  color: Tag.Color = Tag.ColorGray,
  size: Tag.Size = Tag.SizeMedium,
  isSolid: Boolean = false,
  onClick: Callback = Callback.empty
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
  case object ColorGray extends Color {
    val clear: Style = Style.background.gray3.color.gray7
    val solid: Style = Style.background.gray7.color.white
  }
  case object ColorBlue extends Color {
    val clear: Style = Style.background.blue1.color.blue5
    val solid: Style = Style.background.blue4.color.white
  }
  case object ColorGreen extends Color {
    val clear: Style = Style.background.green1.color.green5
    val solid: Style = Style.background.green4.color.white
  }
  case object ColorOrange extends Color {
    val clear: Style = Style.background.orange1.color.orange5
    val solid: Style = Style.background.orange4.color.white
  }
  case object ColorRed extends Color {
    val clear: Style = Style.background.red1.color.red5
    val solid: Style = Style.background.red4.color.white
  }

  sealed trait Size {
    val style: Style
  }
  case object SizeMedium extends Size {
    val style: Style = Style.fontSize.px11.padding.hor8.lineHeight.px20
  }
  case object SizeSmall extends Size {
    val style: Style = Style.fontSize.px10.padding.hor4.lineHeight.px16
  }

  def render(props: Props, children: PropsChildren): VdomElement = {
    <.div(
      if (props.isSolid) props.color.solid else props.color.clear,
      props.size.style,
      Style.borderRadius.px2.width.maxContent,
      Style.fontWeight.medium.whiteSpace.noWrap,
      ^.onClick --> props.onClick,
      children
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
