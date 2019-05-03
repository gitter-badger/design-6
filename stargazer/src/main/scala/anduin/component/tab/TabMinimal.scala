// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.tab

import anduin.component.util.ComponentUtils
import anduin.style.{CssVar, Style}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[tab] final case class TabMinimal(
  titles: Seq[(VdomNode, Int)],
  content: VdomNode,
  active: Int,
  setActive: Int => Callback
) {
  def apply(): VdomElement = TabMinimal.component(this)
}

private[tab] object TabMinimal {

  private type Props = TabMinimal

  private val activeStyles = TagMod(
    Style.color.gray8.border.bottom.borderWidth.px2,
    ^.borderBottomColor := CssVar.Color.primary4
  )

  private val normalStyles = TagMod(
    Style.border.bottom.borderWidth.px2.borderColor.transparent,
    Style.color.gray7
  )

  private def renderButton(props: Props)(titleTup: (VdomNode, Int)): VdomElement = {
    val (title, index) = titleTup
    val isActive = props.active == index
    <.button(
      ComponentUtils.testId(this, s"${title.rawNode.toString.replaceAll("[ ']", "")}Button"),
      // === Styles
      Style.outline.focusLight.transition.allWithOutline.padding.hor12.padding.ver8.margin.hor4,
      if (isActive) activeStyles else normalStyles,
      // === Behaviours
      ^.tpe := "button",
      ^.onClick --> props.setActive(index),
      ^.disabled := isActive,
      title
    )
  }

  private def renderHeader(props: Props): VdomElement = {
    <.header(
      Style.flexbox.flex.flexbox.justifyCenter.lineHeight.px16.fontWeight.medium.margin.bottom24,
      props.titles.toTagMod { renderButton(props) }
    )
  }

  private def renderContent(props: Props): VdomElement = {
    <.div(
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
