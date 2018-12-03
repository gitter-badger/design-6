// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.progressindicators

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class RippleIndicator() {
  def apply(): VdomElement = RippleIndicator.component(this)
}

object RippleIndicator {

  private type Props = RippleIndicator

  private val size = TagMod(^.width := "48px", ^.height := "48px")

  private val wave = TagMod(
    size,
    Style.animation.ripple.borderRadius.pc100,
    Style.position.absolute.coordinate.fill,
    Style.border.all.borderColor.primary4,
    Style.opacity.pc100,
    ^.borderWidth := "6px",
    ^.transform := "scale(0)"
  )

  private val render: VdomElement = {
    <.div(
      Style.position.relative,
      size,
      <.div(wave),
      <.div(wave, ^.animationDelay := "0.5s")
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderStatic(render)
    .build
}
