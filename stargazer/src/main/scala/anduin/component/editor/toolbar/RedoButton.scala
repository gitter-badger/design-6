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

private[toolbar] final case class RedoButton(
  value: Value,
  editorRef: () => SlateReact.EditorComponentRef
) {
  def apply(): VdomElement = RedoButton.component(this)
}

private[toolbar] object RedoButton {

  private type Props = RedoButton

  private def onClickRedo(props: Props) = {
    for {
      editorInstance <- props.editorRef().get
      editor = editorInstance.raw
      _ <- Callback {
        editor.redo()
      }
    } yield ()
  }

  private def render(props: Props) = {
    Tooltip(
      targetWrapper = PortalWrapper.Inline,
      renderTarget = Button(
        style = Button.Style.Minimal(icon = Some(Icon.Glyph.Redo)),
        onClick = onClickRedo(props),
        isDisabled = !SlateUtil.hasRedo(props.value)
      )(),
      renderContent = () => "Redo"
    )()
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
