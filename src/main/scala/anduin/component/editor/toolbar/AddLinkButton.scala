// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.toolbar

import scala.scalajs.js

import anduin.component.button.Button
import anduin.component.editor.LinkNode
import anduin.component.editor.utils.SlateUtil
import anduin.component.icon.Icon
import anduin.component.modal.Modal
import anduin.component.tooltip.Tooltip
import anduin.scalajs.slate.Slate.Value
import anduin.scalajs.slate.SlateReact

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[toolbar] final case class AddLinkButton(
  value: Value,
  editorRef: () => SlateReact.EditorComponentRef
) {
  def apply(): VdomElement = AddLinkButton.component(this)
}

private[toolbar] object AddLinkButton {

  private type Props = AddLinkButton

  private def onAddLink(props: Props)(link: String, openInNewTab: Boolean) = {
    // Prepend `http://` if the link doesn't start with it
    val standardLink = if (!link.startsWith("http://") && !link.startsWith("https://")) {
      s"http://$link"
    } else {
      link
    }

    for {
      editorInstance <- props.editorRef().get
      editor = editorInstance.raw
      value = props.value
      // If there's a link inside the current selection, then remove it
      _ <- Callback {
        if (SlateUtil.hasLinks(value)) {
          editor.unwrapInline(LinkNode.nodeType)
        } else {
          if (!value.selection.isExpanded) {
            // If there's no selected text, create a link with the same text and href
            editor.insertText(standardLink).moveFocusBackward(standardLink.length)
          }
          val data = js.Dynamic.literal(href = standardLink)
          if (openInNewTab) {
            data.updateDynamic("target")("_blank")
          }
          editor.wrapInline(
            js.Dynamic.literal(
              `type` = LinkNode.nodeType,
              data = data
            )
          )
          editor.moveToEnd()
        }
      }
    } yield ()
  }

  private def render(props: Props) = {
    Tooltip(
      targetTag = <.span,
      renderTarget = Modal(
        title = "Add a link",
        renderTarget = open => {
          Button(
            onClick = open,
            style = Button.Style.Minimal(icon = Some(Icon.Glyph.Link))
          )()
        },
        renderContent = LinkModal(props.value, onAddLink(props), _)()
      )(),
      renderContent = () => "Insert Link"
    )()
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
