// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.components.card

import design.anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Card(
  isDimmed: Boolean = false,
  isFullHeight: Boolean = false
) {
  def apply(children: VdomNode*): VdomElement = {
    Card.component(this)(children: _*)
  }
}

object Card {

  private type Props = Card

  private def render(props: Props, children: PropsChildren): VdomElement = {
    <.div(
      if (props.isDimmed) Style.background.gray1 else Style.background.gray0,
      TagMod.when(props.isFullHeight)(Style.height.pc100),
      Style.shadow.px4.borderRadius.px2.padding.all20,
      children
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
