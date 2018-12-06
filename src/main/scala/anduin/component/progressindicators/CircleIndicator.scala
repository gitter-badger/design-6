// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.progressindicators

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class CircleIndicator() {
  def apply(): VdomElement = CircleIndicator.component(this)
}

object CircleIndicator {

  private type Props = CircleIndicator

  private val size: TagMod = Style.width.px16.height.px16

  private val common: TagMod = TagMod(
    size,
    Style.position.absolute.coordinate.fill,
    Style.border.all.borderRadius.pc100,
    ^.borderWidth := "3px"
  )

  private val animated: VdomElement = {
    <.div(
      common,
      Style.animation.circle.borderColor.transparent,
      ^.borderTopColor := "currentColor"
    )
  }

  private val static: VdomElement = {
    <.div(common, Style.opacity.pc20)
  }

  private val render: VdomElement = {
    <.div(size, Style.position.relative, static, animated)
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderStatic(render)
    .build
}
