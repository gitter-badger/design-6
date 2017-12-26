// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.slate

import scala.scalajs.js

import org.scalajs.dom.{FileList, window}

import anduin.component.icon.{Icon, Iconv2}
import anduin.component.modal.OpenModalButton
import anduin.component.popover.Popover
import anduin.component.tooltip.Tooltip
import anduin.component.uploader.BrowseFileButton
import anduin.component.util.JavaScriptUtils
import anduin.scalajs.slate.Slate.{Change, Inline, Value}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Toolbar(
  value: Value,
  onChange: Change => Callback,
  onSelectFilesOpt: Option[FileList => Callback],
  shareDocsFromWorkspaceButton: Option[TagMod],
  showAttachmentIcon: Boolean
) {
  def apply(children: VdomNode*): ScalaComponent.Unmounted[_, _, _] = Toolbar.component(this)(children: _*)
}

object Toolbar {

  private val ComponentName = this.getClass.getSimpleName

  private final val isMac = window.navigator.userAgent.matches(".*(Mac|iPod|iPhone|iPad).*")

  private case class Backend(scope: BackendScope[Toolbar, _]) {

    private def hasLinks(value: Value) = {
      value.inlines.some(inline => inline.inlineType == Inline.LinkType)
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
            change.unwrapInline(Inline.LinkType)
          }
        }
        _ <- {
          if (!value.isExpanded) {
            // If there's no selected text, create a link with the same text and href
            change.insertText(standardLink).extend(0 - standardLink.length)
          }
          change.wrapInline(js.Dynamic.literal(
            `type` = Inline.LinkType,
            data = js.Dynamic.literal(href = standardLink)
          ))
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
          props.onChange(value.change().unwrapInline(Inline.LinkType))
        }
      } yield ()
    }

    // scalastyle:off method.length multiple.string.literals
    def render(props: Toolbar, children: PropsChildren): VdomElement = {
      val hasLink = hasLinks(props.value)

      <.div(^.cls := "editor-toolbar flex padding-all-small items-center",
        <.div(^.cls := "btn-group flex items-center",
          TagMod.when(props.showAttachmentIcon) {
            TagMod(
              if (props.shareDocsFromWorkspaceButton.isDefined) {
                Popover(
                  toggler = Tooltip(
                    tip = "Upload documents"
                  )(Iconv2.clippie()),
                  placement = Popover.Placement.BottomRight,
                  classes = "pointer"
                )(
                  _ => <.ul(^.cls := "no-bullet",
                    <.li(^.cls := "item mb1",
                      BrowseFileButton(
                        classes = s"popover-menu-item ${if (props.onSelectFilesOpt.isEmpty) "disabled" else ""}",
                        onBrowse = fileList => Callback.traverseOption(props.onSelectFilesOpt)(_ (fileList))
                      )(<.span("Upload documents"))
                    ),
                    props.shareDocsFromWorkspaceButton.whenDefined { component =>
                      <.li(^.cls := "item", component)
                    }
                  )
                )()
              } else {
                Tooltip(
                  tip = "Upload documents",
                  offset = 4
                )(BrowseFileButton(
                  classes = s"btn -plain -icon-only ${if (props.onSelectFilesOpt.isEmpty) "disabled" else ""}",
                  onBrowse = fileList => Callback.traverseOption(props.onSelectFilesOpt)(_ (fileList))
                )(Iconv2.clippie()))
              },
              <.span(^.cls := "divider margin-horizontal-small", "-------")
            )
          },

          MarkButtonBar(props.value, props.onChange)(),

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
            VdomAttr("data-tip") := "Add a link",
            OpenModalButton(
              buttonLabel = "",
              buttonClasses = "btn -plain -icon-only",
              modalTitle = "Add a link",
              modalBody = LinkModal(props.value, onAddLink, _)()
            )(Iconv2.link())
          ),

          <.span(
            ^.cls := "tooltip -top",
            VdomAttr("data-tip") := "Remove link",
            <.a(
              ^.classSet(
                "btn -plain -icon-only" -> true,
                "disabled" -> !hasLink
              ),
              ^.href := JavaScriptUtils.voidMethod,
              ^.onClick --> onRemoveLink,
              Iconv2.unlink()
            )
          )
        ),

        <.div(^.cls := "flex margin-left-auto",
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

  private val component = ScalaComponent.builder[Toolbar](ComponentName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}
