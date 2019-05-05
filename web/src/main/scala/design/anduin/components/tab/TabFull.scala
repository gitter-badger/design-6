// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.components.tab

import design.anduin.style.{CssVar, Style}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[tab] final case class TabFull(
  titles: Seq[(VdomNode, Int)],
  content: VdomNode,
  active: Int,
  setActive: Int => Callback,
  style: Tab.StyleFull
) {
  def apply(): VdomElement = TabFull.component(this)
}

private[tab] object TabFull {

  private type Props = TabFull

  private val activeStyles = TagMod(
    Style.background.gray0.color.gray8.borderRadius.px2.borderRadius.top,
    ^.borderBottomColor := "transparent",
    ^.borderTopColor := CssVar.Color.primary4,
    ^.boxShadow := s"${CssVar.Color.primary4} 0 -1px 0 0"
  )

  private val normalStyles = TagMod(
    Style.background.gray1.color.gray7,
    Style.background.hoverGray0.background.activeGray2
  )

  private def renderButton(props: Props)(titleTup: (VdomNode, Int)): VdomElement = {
    val (title, index) = titleTup
    val isActive = props.active == index
    <.button(
      // === Styles
      Style.outline.focusLight.transition.allWithOutline.padding.hor16.padding.ver12,
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
      TagMod.when(props.style.isFullHeight)(Style.flexbox.none),
      TagMod.when(props.style.headerIsCentered)(Style.flexbox.justifyCenter),
      props.titles.toTagMod(renderButton(props))
    )
  }

  private def renderContent(props: Props): VdomElement = {
    <.div(
      Style.background.gray0.borderRadius.px2.borderRadius.bottom,
      Style.border.all.borderColor.gray3,
      TagMod.when(props.style.isFullHeight)(Style.flexbox.fill),
      TagMod.when(!props.style.bodyHasNoPadding)(Style.padding.all16),
      props.content
    )
  }

  private def render(props: Props): VdomElement = {
    <.div(
      TagMod.when(props.style.isFullHeight)(
        Style.flexbox.flex.flexbox.column.height.pc100
      ),
      renderHeader(props),
      renderContent(props)
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build

}
