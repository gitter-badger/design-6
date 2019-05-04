// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.components.progressindicators

import design.anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class BarIndicator(
  percent: Option[Double] = None
) {
  def apply(): VdomElement = BarIndicator.component(this)
}

object BarIndicator {

  private type Props = BarIndicator

  private def renderIndicator(props: Props): VdomElement = {
    <.div(
      Style.position.absolute.position.pinTop.position.pinLeft,
      Style.background.currentColor.height.pc100,
      props.percent.fold[TagMod] {
        Style.width.pc30.animation.translateX330
      } { percent =>
        ^.width := s"${percent * 100}%"
      }
    )
  }

  private val track = <.div(
    Style.position.absolute.position.pinAll,
    Style.background.currentColor.opacity.pc20
  )

  private val styles =
    TagMod(Style.overflow.hiddenX.position.relative, ^.height := "3px")

  private def render(props: Props): VdomElement = {
    // we don't use the native <progress> here due to difficult in
    // customization using atomic CSS
    <.div(styles, renderIndicator(props), track)
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
