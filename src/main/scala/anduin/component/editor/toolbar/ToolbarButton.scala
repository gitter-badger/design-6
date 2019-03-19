// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.toolbar

import anduin.component.button.Button
import anduin.component.portal.PortalWrapper
import anduin.component.tooltip.Tooltip

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[toolbar] final case class ToolbarButton(
  key: String,
  tip: String,
  active: Boolean,
  onClick: Callback
) {
  def apply(children: VdomNode*): VdomElement = {
    ToolbarButton.component.withKey(key)(this)(children: _*)
  }
}

private[toolbar] object ToolbarButton {

  private type Props = ToolbarButton

  private def render(props: ToolbarButton, children: PropsChildren): VdomElement = {
    Tooltip(
      targetWrapper = PortalWrapper.Inline,
      renderTarget = Button(
        style = Button.Style.Minimal(isSelected = props.active),
        onClick = props.onClick
      )(children),
      renderContent = () => props.tip,
      isDisabled = props.tip.nonEmpty
    )()
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
