// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.docs.pages.components.button

import design.anduin.components.button.Button
import design.anduin.components.icon.Icon
import design.anduin.components.menu.{Menu, MenuDivider, MenuItem}
import design.anduin.components.popover.Popover
import design.anduin.components.portal.PortalPosition
import design.anduin.docs.components.ExampleSimple

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class BoxExampleMenu() {
  def apply(): VdomElement = BoxExampleMenu.component(this)
}

object BoxExampleMenu {

  private type Props = BoxExampleMenu

  private def renderContent(close: Callback): VdomElement = {
    val Item = MenuItem(onClick = close)
    Menu()(
      Item("Open"),
      Item("Open With…"),
      MenuDivider()(),
      Item("Get Info"),
      Item("Compress"),
      Item("Copy"),
      MenuDivider()(),
      Item.copy(color = MenuItem.ColorDanger)("Delete"),
    )
  }

  private def renderTarget(toggle: Callback, isSelected: Boolean): VdomElement = {
    Button(
      style = Button.Style.Ghost(
        icon = Some(Icon.Glyph.EllipsisHorizontal),
        isSelected = isSelected
      ),
      onClick = toggle
    )()
  }

  private def render(props: Props): VdomElement = {
    ExampleSimple()(
      Popover(
        renderTarget = renderTarget,
        renderContent = renderContent,
        position = PortalPosition.BottomLeft
      )()
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
