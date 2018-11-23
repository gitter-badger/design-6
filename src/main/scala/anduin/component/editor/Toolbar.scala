// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor

import anduin.component.button.{Button, ButtonStyle}
import anduin.component.icon.Icon
import anduin.component.menu.VerticalDivider
import anduin.component.modal.Modal
import anduin.component.popover.Popover
import anduin.component.tooltip.Tooltip
import anduin.component.util.ComponentUtils
import anduin.scalajs.slate.Slate.{Change, Value}
import anduin.style.Style
import org.scalajs.dom.raw.Element
import org.scalajs.dom.window

import scala.scalajs.js

// scalastyle:off underscore.import
import anduin.component.portal._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Toolbar(
  editorRef: () => CallbackOption[Element],
  value: Value,
  onChange: Change => Callback,
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

    private def onAddLink(link: String) = {
      // Prepend `http://` if the link doesn't start with it
      val standardLink = if (!link.startsWith("http://") && !link.startsWith("https://")) {
        s"http://$link"
      } else {
        link
      }

      for {
        props <- scope.props
        value = props.value
        change = value.change()
        // If there's a link inside the current selection, then remove it
        _ <- Callback.when(hasLinks(value)) {
          Callback {
            change.unwrapInline(LinkNode.nodeType)
          }
        }
        _ <- {
          if (!value.isExpanded) {
            // If there's no selected text, create a link with the same text and href
            change.insertText(standardLink).extend(0 - standardLink.length)
          }
          change.wrapInline(
            js.Dynamic.literal(
              `type` = LinkNode.nodeType,
              data = js.Dynamic.literal(href = standardLink)
            )
          )
          change.collapseToEnd()
          props.onChange(change)
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
        ^.cls := "editor-toolbar flex pa1 items-center",
        <.div(
          Style.flexbox.flex.flexbox.itemsCenter,
          props.attachmentButton,
          VerticalDivider(),
          // Undo button
          Tooltip(
            targetTag = <.span,
            renderTarget = Button(
              style = ButtonStyle.StyleMinimal,
              size = ButtonStyle.SizeIcon,
              onClick = props.onChange(props.value.change().undo()),
              isDisabled = !props.value.hasUndos,
              icon = Some(Icon.NameUndo)
            )(),
            renderContent = () => "Undo"
          )(),
          // Redo button
          Tooltip(
            targetTag = <.span,
            renderTarget = Button(
              style = ButtonStyle.StyleMinimal,
              size = ButtonStyle.SizeIcon,
              onClick = props.onChange(props.value.change().redo()),
              isDisabled = props.value.hasRedos,
              icon = Some(Icon.NameRedo)
            )(),
            renderContent = () => "Redo"
          )(),
          VerticalDivider(),
          Tooltip(
            targetTag = <.span,
            renderTarget = Modal(
              title = "Add a link",
              renderTarget = open => {
                Button(
                  onClick = open,
                  size = ButtonStyle.SizeIcon,
                  style = ButtonStyle.StyleMinimal,
                  icon = Some(Icon.NameLink)
                )()
              },
              renderContent = LinkModal(props.value, onAddLink, _)()
            )(),
            renderContent = () => "Insert Link"
          )(),
          Tooltip(
            targetTag = <.span,
            renderTarget = Button(
              style = ButtonStyle.StyleMinimal,
              size = ButtonStyle.SizeIcon,
              onClick = onRemoveLink,
              isDisabled = !hasLink,
              icon = Some(Icon.NameUnlink)
            )(),
            renderContent = () => "Remove Link"
          )(),
          VerticalDivider(),
          Popover(
            position = PositionTopCenter,
            verticalOffset = -8,
            isClosable = None,
            renderTarget = (toggle, isOpened) => {
              Tooltip(
                targetTag = <.span,
                renderTarget = Button(
                  style = ButtonStyle.StyleMinimal,
                  size = ButtonStyle.SizeIcon,
                  onClick = toggle,
                  isSelected = isOpened,
                  icon = Some(Icon.NameTextStyle)
                )(),
                renderContent = () => "Formatting options"
              )()
            },
            renderContent = _ => {
              <.div(
                Style.flexbox.flex.flexbox.itemsCenter.padding.all4,
                MarkButtonBar(props.value, props.onChange)(),
                VerticalDivider(),
                AlignButtonBar(props.value, props.onChange)(),
                VerticalDivider(),
                BlockButtonBar(props.value, props.onChange)()
              )
            }
          )()
        ),
        <.div(
          ^.cls := "flex m-la",
          Tooltip(
            targetTag = <.span,
            renderTarget = Modal(
              title = "Keyboard Shortcuts",
              renderTarget = open => {
                Button(
                  onClick = open,
                  size = ButtonStyle.SizeIcon,
                  style = ButtonStyle.StyleMinimal,
                  icon = Some(Icon.NameInfo)
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
