// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.editor

sealed trait KeyBindingCommand {
  def command: String
}

object KeyBindingCommand {
  // Do not change these values which are defined by DraftJS to determine a key command will be handled or not
  final val Handled = "handled"
  final val NotHandled = "not-handled"
}

case object BoldCommand extends KeyBindingCommand { override val command = "BOLD" }
case object ItalicCommand extends KeyBindingCommand { override val command = "ITALIC" }
case object UnderlineCommand extends KeyBindingCommand { override val command = "UNDERLINE" }
case object UndoCommand extends KeyBindingCommand { override val command = "UNDO" }
case object RedoCommand extends KeyBindingCommand { override val command = "REDO" }
