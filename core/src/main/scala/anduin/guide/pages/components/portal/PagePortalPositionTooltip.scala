// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.guide.pages.components.portal

import anduin.component.tooltip.Tooltip
import anduin.component.portal.PortalPosition
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class PagePortalPositionTooltip() {
  def apply(): VdomElement = PagePortalPositionTooltip.component()
}

object PagePortalPositionTooltip {

  private def renderTooltip(position: PortalPosition): VdomElement = {
    Tooltip(
      renderTarget = <.div(
        Style.background.gray3.height.px20.padding.hor4.borderRadius.px2,
        Style.fontWeight.semiBold.fontSize.px11,
        position.getClass.getSimpleName
      ),
      renderContent = () => "Lorem ipsum dolor\nsit amet",
      position = position
    )()
  }

  private val render: VdomElement = {
    PagePortalPosition(
      render = renderTooltip,
      hint = "Hover on a position to see its Tooltip"
    )()
  }

  private val component = ScalaComponent
    .static(this.getClass.getSimpleName)(render)
}
