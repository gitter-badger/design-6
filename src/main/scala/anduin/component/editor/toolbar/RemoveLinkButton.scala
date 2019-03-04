// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.toolbar

import anduin.component.button.Button
import anduin.component.editor.LinkNode
import anduin.component.editor.utils.SlateUtil
import anduin.component.icon.Icon
import anduin.component.tooltip.Tooltip
import anduin.scalajs.slate.Slate.Value
import anduin.scalajs.slate.SlateReact

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[toolbar] final case class RemoveLinkButton(
  value: Value,
  editorRef: () => SlateReact.EditorComponentRef
) {
  def apply(): VdomElement = RemoveLinkButton.component(this)
}

private[toolbar] object RemoveLinkButton {

  private type Props = RemoveLinkButton

  private def onRemoveLink(props: Props) = {
    for {
      editorInstance <- props.editorRef().get
      editor = editorInstance.raw
      value = props.value
      _ <- Callback.when(SlateUtil.hasLinks(value)) {
        Callback {
          editor.unwrapInline(LinkNode.nodeType)
        }
      }
    } yield ()
  }

  private def render(props: Props) = {
    val hasLink = SlateUtil.hasLinks(props.value)

    Tooltip(
      targetTag = <.span,
      renderTarget = Button(
        style = Button.Style.Minimal(icon = Some(Icon.Glyph.Unlink)),
        onClick = onRemoveLink(props),
        isDisabled = !hasLink
      )(),
      renderContent = () => "Remove Link"
    )()
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
