// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.editor

import scala.scalajs.js

import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.TagOf
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.html.Div

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.scalajs.draftjs._
// scalastyle:on underscore.import

final case class RichEditor(
  editorState: EditorState,
  onChange: EditorState => Callback = _ => Callback.empty,
  classes: String = "editor-wrapper",
  placeholder: String = "",
  readOnly: Boolean = false
) {
  def apply(): ScalaComponent.Unmounted[_, _, _] = RichEditor.component(this)
}

object RichEditor {

  private val ComponentName = this.getClass.getSimpleName

  private val compositeDecorator = new CompositeDecorator(js.Array(LinkDecorator(), ImageDecorator(), LinkifyDecorator()))

  // A helper function to create a read-only instance of editor from given HTML
  def readOnlyFromHtml(html: String, maxLengthOpt: Option[Int] = None): Unmounted[_, _, _] = {
    RichEditor(
      classes = "",
      editorState = DraftUtils.createStateFromHtml(html, maxLengthOpt),
      readOnly = true
    )()
  }

  private case class Backend(scope: BackendScope[RichEditor, _]) {

    private def keyBinding(e: SyntheticKeyboardEvent) = {
      val defaultKeyBinding = Draft.getDefaultKeyBinding(e)

      if (KeyBindingUtil.hasCommandModifier(e)) {
        e.keyCode match {
          case KeyCode.B => BoldCommand.command
          case KeyCode.I => ItalicCommand.command
          case KeyCode.U => UnderlineCommand.command
          case KeyCode.Z => UndoCommand.command
          case KeyCode.Y => RedoCommand.command
          case _ => defaultKeyBinding
        }
      } else {
        defaultKeyBinding
      }
    }

    private def handleKeyCommand(command: String) = {
      for {
        props <- scope.props
        (newEditorState, handled) = command match {
          case BoldCommand.command => (RichUtils.toggleInlineStyle(props.editorState, BoldInlineStyle.style), true)
          case ItalicCommand.command => (RichUtils.toggleInlineStyle(props.editorState, ItalicInlineStyle.style), true)
          case UnderlineCommand.command => (RichUtils.toggleInlineStyle(props.editorState, UnderlineInlineStyle.style), true)
          case UndoCommand.command => (EditorState.undo(props.editorState), true)
          case RedoCommand.command => (EditorState.redo(props.editorState), true)
          case _ => (props.editorState, false)
        }
        _ <- Callback.when(handled) {
          props.onChange(newEditorState)
        }
      } yield {
        if (handled) KeyBindingCommand.Handled else KeyBindingCommand.NotHandled
      }
    }

    def render(props: RichEditor): TagOf[Div] = {
      // Hide the placeholder if user changes block type
      val currentContent = props.editorState.getCurrentContent()
      val hidePlaceholder = !currentContent.hasText() && currentContent.getBlockMap().first().getType() != "unstyled"
      val editorState = EditorState.set(props.editorState, EditorStateRecord(decorator = compositeDecorator))

      <.div(^.cls := props.classes,
        <.div(
          ^.classSet(
            "editor" -> true,
            "RichEditor-hidePlaceholder" -> hidePlaceholder
          ),
          DraftEditor(
            placeholder = props.placeholder,
            readOnly = props.readOnly,
            editorState = editorState,
            handleKeyCommand = handleKeyCommand,
            keyBindingFn = keyBinding,
            customStyleMapOpt = Some(StyleMap.TextAlignStyle),
            onChange = props.onChange
          )
        )
      )
    }
  }

  private val component = ScalaComponent.builder[RichEditor](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}
