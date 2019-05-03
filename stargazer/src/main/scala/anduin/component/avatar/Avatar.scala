// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.avatar

import anduin.style.Style
import com.anduin.stargazer.util.avatar.AvatarColor

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Avatar(
  id: String,
  borderRadius: Style,
  avatarSize: AvatarSize = Size32
) {
  def apply(children: VdomNode*): VdomElement = Avatar.component(this)(children: _*)
}

object Avatar {

  private type Props = Avatar

  // scalastyle:off multiple.string.literals
  private def render(props: Props, children: PropsChildren) = {
    <.span(
      Style.flexbox.flex.flexbox.itemsCenter.flexbox.justifyCenter.fontWeight.bold.color.gray0.flexbox.none,
      props.borderRadius,
      ^.width := s"${props.avatarSize.size}px",
      ^.height := s"${props.avatarSize.size}px",
      ^.fontSize := s"${props.avatarSize.fontSize}px",
      ^.backgroundColor := AvatarColor(props.id),
      children
    )
  }
  // scalastyle:on multiple.string.literals

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
