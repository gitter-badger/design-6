// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.toolbar

import anduin.component.button.Button
import anduin.component.icon.Icon
import anduin.component.menu.VerticalDivider
import anduin.component.popover.Popover
import anduin.component.portal.PositionTopCenter
import anduin.component.tooltip.Tooltip
import anduin.scalajs.slate.Slate.Value
import anduin.scalajs.slate.SlateReact
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[toolbar] final case class FormatButton(
  value: Value,
  editorRef: () => SlateReact.EditorComponentRef
) {
  def apply(): VdomElement = FormatButton.component(this)
}

private[toolbar] object FormatButton {

  private type Props = FormatButton

  private def render(props: Props) = {
    Popover(
      position = PositionTopCenter,
      verticalOffset = -8,
      isClosable = None,
      renderTarget = (toggle, isOpened) => {
        Tooltip(
          targetTag = <.span,
          renderTarget = Button(
            style = Button.Style.Minimal(isSelected = isOpened, icon = Some(Icon.Glyph.TextStyle)),
            onClick = toggle
          )(),
          renderContent = () => "Formatting options"
        )()
      },
      renderContent = _ => {
        <.div(
          Style.flexbox.flex.flexbox.itemsCenter.padding.all4,
          MarkButtonBar(props.value, props.editorRef)(),
          VerticalDivider()(),
          AlignButtonBar(props.value, props.editorRef)(),
          VerticalDivider()(),
          BlockButtonBar(props.value, props.editorRef)()
        )
      }
    )()
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
