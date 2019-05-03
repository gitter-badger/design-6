// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.guide.pages.components.portal

import anduin.component.icon.Icon
import anduin.component.popover.Popover
import anduin.component.portal.PortalPosition
import anduin.component.tag.Tag
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class PagePortalPositionPopover() {
  def apply(): VdomElement = PagePortalPositionPopover.component()
}

object PagePortalPositionPopover {

  private def renderPopover(position: PortalPosition): VdomElement = {
    Popover(
      renderTarget = (open, isOpened) => {
        Tag(
          color = if (isOpened) Tag.Bold.Gray else Tag.Light.Gray,
          target = Tag.Target.Button(open)
        )(position.getClass.getSimpleName)
      },
      renderContent = _ => {
        <.div(
          Style.flexbox.flex.padding.all16,
          Icon(name = Icon.File.Final, size = Icon.Size.Px32)(),
          Icon(name = Icon.File.Archive, size = Icon.Size.Px32)(),
        )
      },
      position = position
    )()
  }

  private val render: VdomElement = {
    PagePortalPosition(
      render = renderPopover,
      hint = "Click on a position to see its Popover"
    )()
  }

  private val component = ScalaComponent
    .static(this.getClass.getSimpleName)(render)
}
