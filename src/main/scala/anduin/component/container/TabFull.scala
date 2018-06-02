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

  private def renderButton(props: Props)(titleTup: (VdomNode, Int)): VdomElement = {
    val (title, index) = titleTup
    val isActive = props.active == index
    <.button(
      // === Styles
      Style.focus.outline.transition.allWithOutline.padding.hor16.padding.ver12,
      if (isActive) {
        TagMod(Style.backgroundColor.white.color.gray8, ^.boxShadow := "white 0px 1px 0 0")
      } else {
        Style.backgroundColor.gray1.color.gray7.hover.backgroundWhite.active.backgroundGray2
      },
      TagMod.when(index != 0) { Style.border.left.borderColor.gray3 },
      // === Behaviours
      ^.tpe := "button",
      ^.onClick --> props.setActive(index),
      ^.disabled := isActive,
      title
    )
  }

  private def renderHeader(props: Props): VdomElement = {
    <.header(
      Style.border.bottom.borderColor.gray3,
      <.div(
        ^.marginBottom := "-1px",
        Style.flexbox.inlineFlex.lineHeight.px16.fontWeight.medium,
        Style.borderRadius.px2.borderRadius.top.border.all.borderColor.gray3,
        props.titles.toTagMod { renderButton(props) }
      )
    )
  }

  private def renderContent(props: Props): VdomElement = {
    <.div(
      Style.backgroundColor.white.padding.all16.borderRadius.px2.borderRadius.bottom,
      Style.border.left.border.bottom.border.right.borderColor.gray3,
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
