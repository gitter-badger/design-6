// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.button

import anduin.component.icon.Icon
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class ButtonLink(
  // Common styling via ButtonStyle
  color: ButtonStyle.Color = ButtonStyle.ColorWhite,
  size: ButtonStyle.Size = ButtonStyle.Size32,
  style: ButtonStyle.Style = ButtonStyle.StyleFull,
  isFullWidth: Boolean = false,
  isSelected: Boolean = false,
  // Specific behaviours for ButtonLink
  icon: Option[Icon.Name] = None,
  onClick: Callback = Callback.empty, // link can still has onClick
  href: String = "",
  target: TagMod = TagMod.empty
) {
  def apply(children: VdomNode*): VdomElement = {
    ButtonLink.component(this)(children: _*)
  }
}

object ButtonLink {

  type Props = ButtonLink

  def render(props: Props, children: PropsChildren): VdomElement = <.a(
    // styles
    ButtonStyle.getStyles(
      color = props.color,
      size = props.size,
      style = props.style,
      isSelected = props.isSelected,
      isFullWidth = props.isFullWidth
    ),
    Style.hover.underlineNone,
    // behaviours
    ^.href := props.href,
    props.target,
    TagMod.when(!props.onClick.isEmpty_?) { ^.onClick --> props.onClick },
    // content
    ButtonStyle.renderIcon(props.icon, children),
    children
  )

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
