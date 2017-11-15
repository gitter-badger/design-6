// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.editor

import scala.scalajs.js

import japgolly.scalajs.react.vdom.TagOf
import org.scalajs.dom.html.Div
import org.scalajs.dom.{FileList, window}

import anduin.component.icon.{Icon, Iconv2}
import anduin.component.modal.OpenModalButton
import anduin.component.tooltip.Tooltip
import anduin.component.uploader.BrowseFileButton
import anduin.component.util.JavaScriptUtils
import anduin.scalajs.draftjs.{EditorState, RichUtils}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Toolbar(
  editorState: EditorState,
  onChange: EditorState => Callback,
  onSelectFilesOpt: Option[FileList => Callback] = None
) {
  def apply(children: VdomNode*): ScalaComponent.Unmounted[_, _, _] = Toolbar.component(this)(children: _*)
}

object Toolbar {

  private val ComponentName = this.getClass.getSimpleName

  private final val isMac = window.navigator.userAgent.matches(".*(Mac|iPod|iPhone|iPad).*")

  private case class Backend(scope: BackendScope[Toolbar, _]) {

    private def onAddLink(link: String) = {
      // Prepend `http://` if the link doesn't start with it
      val standardLink = if (!link.startsWith("http://") && !link.startsWith("https://")) {
        s"http://$link"
      } else {
        link
      }

      for {
        props <- scope.props
        editorState = props.editorState
        contentState = editorState.getCurrentContent()
        newContentState = contentState.createEntity("LINK", "MUTABLE", js.Dynamic.literal(url = standardLink))
        entityKey = newContentState.getLastCreatedEntityKey()
        newEditorState = EditorState.push(editorState, newContentState, "add-link")
        _ <- props.onChange(RichUtils.toggleLink(newEditorState, newEditorState.getSelection(), entityKey))
      } yield ()
    }

    private def onRemoveLink = {
      for {
        props <- scope.props
        editorState = props.editorState
        selection = editorState.getSelection()
        _ <- Callback.when(!selection.isCollapsed()) {
          props.onChange(RichUtils.toggleLink(editorState, selection, null)) // scalastyle:ignore null
        }
      } yield ()
    }

    // scalastyle:off method.length multiple.string.literals
    def render(props: Toolbar, children: PropsChildren): TagOf[Div] = {
      <.div(^.cls := "editor-toolbar flex padding-all-small items-center",
        <.div(^.cls := "btn-group flex items-center",
          Tooltip(
            tip = "Upload documents",
            offset = 4
          )(BrowseFileButton(
            classes = s"btn -plain -icon-only ${if (props.onSelectFilesOpt.isEmpty) "disabled" else ""}",
            onBrowse = fileList => Callback.traverseOption(props.onSelectFilesOpt)(_(fileList))
          )(Iconv2.clippie())),
          <.span(^.cls := "divider margin-horizontal-small", "-------"),

          InlineStyleBar(props.editorState, props.onChange)(),
          <.span(^.cls := "divider margin-horizontal-small", "-------"),
          BlockStyleBar(props.editorState, props.onChange)(),

          <.span(^.cls := "divider margin-horizontal-small", "-------"),

          // Undo button
          <.span(
            ^.cls := "tooltip -top",
            VdomAttr("data-tip") := "Undo",
            <.a(
              ^.classSet(
                "btn -plain -icon-only" -> true,
                "disabled" -> props.editorState.getUndoStack().isEmpty()
              ),
              ^.href := JavaScriptUtils.VoidMethod,
              ^.onClick --> props.onChange(EditorState.undo(props.editorState)),
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
                "disabled" -> props.editorState.getRedoStack().isEmpty()
              ),
              ^.href := JavaScriptUtils.VoidMethod,
              ^.onClick --> props.onChange(EditorState.redo(props.editorState)),
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
              modalBody = LinkModal(props.editorState, onAddLink, _)()
            )(Iconv2.link())
          ),

          <.span(
            ^.cls := "tooltip -top",
            VdomAttr("data-tip") := "Remove link",
            <.a(
              ^.cls := "btn -plain -icon-only",
              ^.href := JavaScriptUtils.VoidMethod,
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
