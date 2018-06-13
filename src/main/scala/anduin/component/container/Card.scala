// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.container

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.style.Style
// scalastyle:on underscore.import

final case class Card() {
  def apply(children: VdomNode*): VdomElement = Card.component(this)(children: _*)
}

object Card {

  type Props = Card

  private def render(children: PropsChildren): VdomElement = {
    <.div(
      Style.backgroundColor.white.borderRadius.px2.padding.all16,
      Style.border.all.borderColor.gray3.borderWidth.px1,
      children
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_C(render)
    .build
}
