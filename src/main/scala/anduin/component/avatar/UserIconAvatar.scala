// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.avatar

import anduin.component.icon.Icon
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class UserIconAvatar(avatarSize: AvatarSize = Size32) {
  def apply(): VdomElement = UserIconAvatar.component(this)
}

object UserIconAvatar {

  private type Props = UserIconAvatar

  private def render(props: Props) = {
    <.span(
      Style.flexbox.flex.flexbox.itemsCenter.flexbox.justifyCenter.fontWeight.bold.color.white.borderRadius.pill.background.gray4,
      ^.width := s"${props.avatarSize.size}px",
      ^.height := s"${props.avatarSize.size}px",
      Icon(name = Icon.Glyph.UserSingle)()
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
