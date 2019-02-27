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
import anduin.scalajs.slate.Slate.{Editor, Value}
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
  onChange: Editor => Callback,
  attachmentButton: TagMod
) {
  def apply(children: VdomNode*): VdomElement = {
    Toolbar.component(this)(children: _*)
  }
}

object Toolbar {

  private val ComponentName = this.getClass.getSimpleName

  private val isMac = window.navigator.userAgent.matches(".*(Mac|iPod|iPhone|iPad).*")

  private class Backend(scope: BackendScope[Toolbar, _]) {

    private def hasLinks(value: Value) = {
      value.inlines.exists(inline => inline.inlineType == LinkNode.nodeType)
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
        editor = editorInstance.raw.editor
        value = props.value
        // If there's a link inside the current selection, then remove it
        _ <- Callback.when(hasLinks(value)) {
          Callback {
            editor.unwrapInline(LinkNode.nodeType)
          }
        }
        _ <- {
          if (!value.isExpanded) {
            // If there's no selected text, create a link with the same text and href
            editor.insertText(standardLink).extend(0 - standardLink.length)
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
          editor.collapseToEnd()
          props.onChange(editor)
        }
      } yield ()
    }

    private def onRemoveLink = {
      for {
        props <- scope.props
        value = props.value
        _ <- Callback.when(hasLinks(value)) {
          props.onChange(value.change().unwrapInline(LinkNode.nodeType))
        }
      } yield ()
    }

    // scalastyle:off method.length multiple.string.literals
    def render(props: Toolbar, children: PropsChildren): VdomElement = {
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
              onClick = props.onChange(props.value.change().undo()),
              isDisabled = !props.value.hasUndos
            )(),
            renderContent = () => "Undo"
          )(),
          // Redo button
          Tooltip(
            targetTag = <.span,
            renderTarget = Button(
              style = Button.Style.Minimal(icon = Some(Icon.Glyph.Redo)),
              onClick = props.onChange(props.value.change().redo()),
              isDisabled = props.value.hasRedos
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
                MarkButtonBar(props.editorRef, props.value, props.onChange)(),
                VerticalDivider()(),
                AlignButtonBar(props.editorRef, props.value, props.onChange)(),
                VerticalDivider()(),
                BlockButtonBar(props.editorRef, props.value, props.onChange)()
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
    .builder[Toolbar](ComponentName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}
