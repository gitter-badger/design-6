// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.container

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[container] final case class TabFull(
  titles: List[(VdomNode, Int)],
  content: VdomNode,
  active: Int,
  setActive: Int => Callback
) {
  def apply(): VdomElement = { TabFull.component(this) }
}

private[container] object TabFull {

  type Props = TabFull

  private val activeStyles = TagMod(
    Style.backgroundColor.white.color.gray8.borderRadius.px2.borderRadius.top,
    ^.borderBottomColor := "transparent",
    ^.borderTopColor := "var(--color-primary-4)",
    ^.boxShadow := "var(--color-primary-4) 0 -1px 0 0"
  )

  private val normalStyles = TagMod(
    Style.backgroundColor.gray1.color.gray7,
    Style.hover.backgroundWhite.active.backgroundGray2
  )

  private def renderButton(props: Props)(titleTup: (VdomNode, Int)): VdomElement = {
    val (title, index) = titleTup
    val isActive = props.active == index
    <.button(
      // === Styles
      Style.focus.outline.transition.allWithOutline.padding.hor16.padding.ver12,
      Style.border.all.borderColor.gray3,
      if (isActive) activeStyles else normalStyles,
      TagMod.when(index != 0) { ^.marginLeft := "-1px" },
      // === Behaviours
      ^.tpe := "button",
      ^.onClick --> props.setActive(index),
      ^.disabled := isActive,
      title
    )
  }

  private def renderHeader(props: Props): VdomElement = {
    <.header(
      ^.marginBottom := "-1px",
      Style.flexbox.flex.lineHeight.px16.fontWeight.medium,
      props.titles.toTagMod { renderButton(props) }
    )
  }

  private def renderContent(props: Props): VdomElement = {
    <.div(
      Style.backgroundColor.white.padding.all16.borderRadius.px2.borderRadius.bottom,
      Style.border.all.borderColor.gray3,
      props.content
    )
  }

  private def render(props: Props): VdomElement = {
    <.div(renderHeader(props), renderContent(props))
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build

}
