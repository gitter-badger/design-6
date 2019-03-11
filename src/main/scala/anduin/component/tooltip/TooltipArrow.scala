// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.tooltip

import anduin.scalajs.popper.PopperPlacement
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class TooltipArrow(placement: PopperPlacement) {
  def apply(): VdomElement = TooltipArrow.component(this)
}

object TooltipArrow {

  private type Props = TooltipArrow

  // Arrow's size / 2
  private val offset = "-4px"

  private def getPosition(props: Props): TagMod = props.placement match {
    case _: PopperPlacement.Top    => ^.bottom := offset
    case _: PopperPlacement.Right  => ^.left := offset
    case _: PopperPlacement.Bottom => ^.top := offset
    case _: PopperPlacement.Left   => ^.right := offset
    case _                         => TagMod.empty
  }

  private val staticMod = TagMod(
    // Required by Popper
    VdomAttr("x-arrow") := "",
    Style.position.absolute,
    // Tooltip style
    Style.width.px8.height.px8.background.gray9,
    ^.transform := "rotate(45deg)"
  )

  private def render(props: Props): VdomElement = {
    <.div(staticMod, getPosition(props))
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
