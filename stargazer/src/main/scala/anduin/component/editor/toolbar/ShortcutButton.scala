// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.toolbar

import org.scalajs.dom
import anduin.component.button.Button
import anduin.component.icon.Icon
import anduin.component.modal.Modal
import anduin.component.portal.PortalWrapper
import anduin.component.tooltip.Tooltip

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[toolbar] final case class ShortcutButton() {
  def apply(): VdomElement = ShortcutButton.component()
}

private[toolbar] object ShortcutButton {

  private val isMac = dom.window.navigator.userAgent.matches(".*(Mac|iPod|iPhone|iPad).*")

  private def render = {
    Tooltip(
      targetWrapper = PortalWrapper.Inline,
      renderTarget = Modal(
        title = "Keyboard Shortcuts",
        renderTarget = open => {
          Button(
            onClick = open,
            style = Button.Style.Minimal(icon = Some(Icon.Glyph.Info))
          )()
        },
        renderContent = _ => {
          <.div(
            ^.cls := "editor-hotkeys-dialog",
            ShortcutModal(isMac)()
          )
        }
      )(),
      renderContent = () => "Keyboard Shortcuts"
    )()
  }

  private val component = ScalaComponent.static(this.getClass.getSimpleName)(render)
}
