// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.container

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Card(
  header: VdomNode = EmptyVdom,
  isDimmed: Boolean = false,
  isFullHeight: Boolean = false,
  borderStyle: Style = Style.borderRadius.px2.padding.all20
) {
  def apply(children: VdomNode*): VdomElement = {
    Card.component(this)(children: _*)
  }
}

object Card {

  private type Props = Card

  private def renderHeader(props: Props): VdomNode = {
    if (props.header == EmptyVdom) {
      EmptyVdom
    } else {
      <.header(
        Style.color.gray7.fontSize.px12.fontWeight.medium.margin.bottom20.textTransform.uppercase,
        props.header
      )
    }
  }

  private def render(props: Props, children: PropsChildren): VdomElement = {
    <.div(
      props.borderStyle,
      if (props.isDimmed) {
        Style.backgroundColor.gray1
      } else {
        Style.backgroundColor.white
      },
      TagMod.when(props.isFullHeight)(Style.height.pc100),
      Style.shadow.blur1Light,
      renderHeader(props),
      children
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
