// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor

import scala.scalajs.js

import org.scalajs.dom.window

import anduin.component.icon.{Icon, Iconv2}
import anduin.component.modal.OpenModalButton
import anduin.component.popover.Popover
import anduin.component.util.JavaScriptUtils
import anduin.scalajs.slate.Slate.{Change, Value}
import anduin.stylesheet.tachyons.Tachyons

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Toolbar(
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

  private final val isMac = window.navigator.userAgent.matches(".*(Mac|iPod|iPhone|iPad).*")

  private case class State(formatActive: Boolean = true)

  private case class Backend(scope: BackendScope[Toolbar, State]) {

    private def hasLinks(value: Value) = {
      value.inlines.some(inline => inline.inlineType == LinkNode.nodeType)
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
    def render(props: Toolbar, state: State, children: PropsChildren): VdomElement = {
      val hasLink = hasLinks(props.value)
      <.div(
        ^.cls := "editor-toolbar flex padding-all-small items-center",
        TagMod.when(state.formatActive)(^.marginTop := "45px"),
        <.div(
          ^.cls := "btn-group flex items-center",
          props.attachmentButton,
          <.span(^.cls := "divider margin-horizontal-small", "-------"),
          // Undo button
          <.span(
            ^.cls := "tooltip -top",
            VdomAttr("data-tip") := "Undo",
            <.a(
              ^.classSet(
                "btn -plain -icon-only" -> true,
                "disabled" -> !props.value.hasUndos
              ),
              ^.href := JavaScriptUtils.voidMethod,
              ^.onClick --> props.onChange(props.value.change().undo()),
              Iconv2.undo()
            )
          ),
          // Redo button
          <.span(
            ^.cls := "tooltip -top",
            VdomAttr("data-tip") := "Redo",
            <.a(
              ^.classSet(
                "btn -plain -icon-only" -> true,
                "disabled" -> props.value.hasRedos
              ),
              ^.href := JavaScriptUtils.voidMethod,
              ^.onClick --> props.onChange(props.value.change().redo()),
              Iconv2.redo()
            )
          ),
          <.span(^.cls := "divider margin-horizontal-small", "-------"),
          <.span(
            ^.cls := "tooltip -top",
            VdomAttr("data-tip") := "Insert Link",
            OpenModalButton(
              buttonLabel = "",
              buttonClasses = "btn -plain -icon-only",
              modalTitle = "Add a link",
              modalBody = LinkModal(props.value, onAddLink, _)()
            )(Iconv2.link())
          ),
          <.span(
            ^.cls := "tooltip -top",
            VdomAttr("data-tip") := "Remove Link",
            <.a(
              ^.classSet(
                "btn -plain -icon-only" -> true,
                "disabled" -> !hasLink
              ),
              ^.href := JavaScriptUtils.voidMethod,
              ^.onClick --> onRemoveLink,
              Iconv2.unlink()
            )
          ),
          <.span(^.cls := "divider margin-horizontal-small", "-------"),
          Popover(
            toggler = <.span(
              ^.cls := "tooltip -top",
              VdomAttr("data-tip") := "Formatting options",
              <.a(
                ^.classSet(
                  "btn -plain -icon-only" -> true,
                  "-selected" -> state.formatActive
                ),
                ^.href := JavaScriptUtils.voidMethod,
                ^.onClick --> scope.modState(_.copy(formatActive = !state.formatActive)),
                Iconv2.format()
              )
            ),
            key = "format-popover",
            status = Popover.Status.Displayed,
            popoverBodyClasses = "format-popover",
            verticalOffset = -10,
            placement = Popover.Placement.Top,
            hideWhenClick = false
          )(
            _ =>
              <.div(
                Tachyons.flexbox.flex,
                MarkButtonBar(props.value, props.onChange)(),
                <.span(^.cls := "divider margin-horizontal-small", "-------"),
                AlignButtonBar(props.value, props.onChange)(),
                <.span(^.cls := "divider margin-horizontal-small", "-------"),
                BlockButtonBar(props.value, props.onChange)()
            )
          )()
        ),
        <.div(
          ^.cls := "flex margin-left-auto",
          <.span(
            ^.cls := "tooltip -top",
            VdomAttr("data-tip") := "Keyboard Shortcuts",
            OpenModalButton(
              buttonLabel = "",
              buttonClasses = "btn -plain -icon-only",
              modalTitle = "Keyboard Shortcuts",
              modalClasses = "editor-hotkeys-dialog",
              modalBody = _ => ShortcutModal(isMac)()
            )(Icon.info())
          ),
          children
        )
      )
    }
    // scalastyle:on method.length multiple.string.literals
  }

  private val component = ScalaComponent
    .builder[Toolbar](ComponentName)
    .initialState(State())
    .renderBackendWithChildren[Backend]
    .build
}
