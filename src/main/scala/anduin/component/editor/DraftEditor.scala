// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.editor

import scala.scalajs.js

import japgolly.scalajs.react.component.Js.UnmountedWithRawType

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import anduin.scalajs.draftjs._
// scalastyle:on underscore.import

private[editor] object DraftEditor {

  private val component = JsComponent[EditorProps, Children.None, Null](Editor)

  def apply(
    editorState: EditorState = EditorState.createEmpty(),
    placeholder: String = "",
    readOnly: Boolean = false,
    keyBindingFn: SyntheticKeyboardEvent => String = Draft.getDefaultKeyBinding,
    handleKeyCommand: String => CallbackTo[String] = _ => CallbackTo.pure(KeyBindingCommand.Handled),
    onChange: EditorState => Callback = _ => Callback.empty,
    customStyleMapOpt: Option[js.Object] = None
  ): UnmountedWithRawType[_, _, _] = {
    val p = js.Dynamic.literal(
      editorState = editorState,
      placeholder = placeholder,
      readOnly = readOnly,
      keyBindingFn = keyBindingFn,
      handleKeyCommand = (command: String) => handleKeyCommand(command).runNow(),
      onChange = (s: EditorState) => onChange(s).runNow()
    )
    val withStyle = customStyleMapOpt.fold(p) { customStyleMap =>
      p.updateDynamic("customStyleMap")(customStyleMap)
      p
    }

    val props = withStyle.asInstanceOf[EditorProps]
    component(props)
  }
}
