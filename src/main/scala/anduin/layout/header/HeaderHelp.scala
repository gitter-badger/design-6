// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.layout.header

import anduin.component.icon.Icon
import anduin.component.portal.PositionBottomCenter
import anduin.component.tooltip.Tooltip
import anduin.layout.header.utils.MainHeaderUtils

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class HeaderHelp() {
  def apply(): VdomElement = HeaderHelp.component()
}

object HeaderHelp {
  private val renderTarget = <.a(
    ^.href := "https://help.anduintransact.com/",
    ^.target.blank,
    MainHeaderUtils.buttonStyles,
    Icon(name = Icon.Glyph.Question)()
  )

  private val content = Tooltip(
    renderTarget = renderTarget,
    renderContent = () => "Help",
    position = PositionBottomCenter
  )()

  private val component = ScalaComponent.static(this.getClass.getSimpleName)(content)
}
