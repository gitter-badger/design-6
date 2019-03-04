// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor

import scala.scalajs.js

import org.scalajs.dom.window

import anduin.component.button.Button
import anduin.component.icon.Icon
import anduin.component.menu.VerticalDivider
import anduin.component.modal.Modal
import anduin.component.popover.Popover
import anduin.component.tooltip.Tooltip
import anduin.component.util.ComponentUtils
import anduin.scalajs.slate.Slate.Value
import anduin.scalajs.slate.SlateReact
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.component.portal._
// scalastyle:on underscore.import

final case class Toolbar(
  value: Value,
  editorRef: () => SlateReact.EditorComponentRef,
  attachmentButton: TagMod
) {
  def apply(children: VdomNode*): VdomElement = {
    Toolbar.component(this)(children: _*)
  }
}

object Toolbar {

  private val isMac = window.navigator.userAgent.matches(".*(Mac|iPod|iPhone|iPad).*")

  private type Props = Toolbar

  private class Backend(scope: BackendScope[Props, _]) {

    private def hasLinks(value: Value) = {
      value.inlines.exists(_.inlineType == LinkNode.nodeType)
    }

    private def onAddLink(link: String, openInNewTab: Boolean) = {
      // Prepend `http://` if the link doesn't start with it
      val standardLink = if (!link.startsWith("http://") && !link.startsWith("https://")) {
        s"http://$link"
      } else {
        link
      }

      for {
        props <- scope.props
        editorInstance <- props.editorRef().get
        editor = editorInstance.raw
        value = props.value
        // If there's a link inside the current selection, then remove it
        _ <- Callback {
          if (hasLinks(value)) {
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

    private def onRemoveLink = {
      for {
        props <- scope.props
        editorInstance <- props.editorRef().get
        editor = editorInstance.raw
        value = props.value
        _ <- Callback.when(hasLinks(value)) {
          Callback {
            editor.unwrapInline(LinkNode.nodeType)
          }
        }
      } yield ()
    }

    private def onClickUndo = {
      for {
        props <- scope.props
        editorInstance <- props.editorRef().get
        editor = editorInstance.raw
        _ <- Callback {
          editor.undo()
        }
      } yield ()
    }

    private def onClickRedo = {
      for {
        props <- scope.props
        editorInstance <- props.editorRef().get
        editor = editorInstance.raw
        _ <- Callback {
          editor.redo()
        }
      } yield ()
    }

    // scalastyle:off method.length multiple.string.literals
    def render(props: Props, children: PropsChildren): VdomElement = {
      val hasLink = hasLinks(props.value)

      <.div(
        ComponentUtils.testId(this, "ToolbarContainer"),
        ^.cls := "editor-toolbar",
        Style.flexbox.flex.flexbox.itemsCenter.padding.all4,
        <.div(
          Style.flexbox.flex.flexbox.itemsCenter,
          props.attachmentButton,
          VerticalDivider()(),
          // Undo button
          Tooltip(
            targetTag = <.span,
            renderTarget = Button(
              style = Button.Style.Minimal(icon = Some(Icon.Glyph.Undo)),
              onClick = onClickUndo,
              isDisabled = !SlateUtil.hasUndo(props.value)
            )(),
            renderContent = () => "Undo"
          )(),
          // Redo button
          Tooltip(
            targetTag = <.span,
            renderTarget = Button(
              style = Button.Style.Minimal(icon = Some(Icon.Glyph.Redo)),
              onClick = onClickRedo,
              isDisabled = !SlateUtil.hasRedo(props.value)
            )(),
            renderContent = () => "Redo"
          )(),
          VerticalDivider()(),
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
              renderContent = LinkModal(props.value, onAddLink, _)()
            )(),
            renderContent = () => "Insert Link"
          )(),
          Tooltip(
            targetTag = <.span,
            renderTarget = Button(
              style = Button.Style.Minimal(icon = Some(Icon.Glyph.Unlink)),
              onClick = onRemoveLink,
              isDisabled = !hasLink
            )(),
            renderContent = () => "Remove Link"
          )(),
          VerticalDivider()(),
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
        ),
        <.div(
          Style.flexbox.flex.margin.leftAuto,
          Tooltip(
            targetTag = <.span,
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
          )(),
          children
        )
      )
    }
    // scalastyle:on method.length multiple.string.literals
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}
