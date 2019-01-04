// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.card

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Card(
  isDimmed: Boolean = false,
  isFullHeight: Boolean = false,
  style: Style = Style.borderRadius.px2.padding.all20
) {
  def apply(children: VdomNode*): VdomElement = {
    Card.component(this)(children: _*)
  }
}

object Card {

  private type Props = Card

  private def render(props: Props, children: PropsChildren): VdomElement = {
    <.div(
      props.style,
      if (props.isDimmed) Style.backgroundColor.gray1 else Style.backgroundColor.white,
      TagMod.when(props.isFullHeight)(Style.height.pc100),
      Style.shadow.blur1Light,
      children
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
