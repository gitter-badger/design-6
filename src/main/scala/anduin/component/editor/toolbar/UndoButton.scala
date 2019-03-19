// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.toolbar

import anduin.component.button.Button
import anduin.component.editor.utils.SlateUtil
import anduin.component.icon.Icon
import anduin.component.portal.PortalWrapper
import anduin.component.tooltip.Tooltip
import anduin.scalajs.slate.Slate.Value
import anduin.scalajs.slate.SlateReact

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[toolbar] final case class UndoButton(
  value: Value,
  editorRef: () => SlateReact.EditorComponentRef
) {
  def apply(): VdomElement = UndoButton.component(this)
}

private[toolbar] object UndoButton {

  private type Props = UndoButton

  private def onClickUndo(props: Props) = {
    for {
      editorInstance <- props.editorRef().get
      editor = editorInstance.raw
      _ <- Callback {
        editor.undo()
      }
    } yield ()
  }

  private def render(props: Props) = {
    Tooltip(
      targetWrapper = PortalWrapper.Inline,
      renderTarget = Button(
        style = Button.Style.Minimal(icon = Some(Icon.Glyph.Undo)),
        onClick = onClickUndo(props),
        isDisabled = !SlateUtil.hasUndo(props.value)
      )(),
      renderContent = () => "Undo"
    )()
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
