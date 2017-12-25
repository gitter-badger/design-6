// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.slate

sealed trait MarkAction {
  def markType: String
}

case object BoldAction extends MarkAction {
  override val markType = "bold"
}

case object ItalicAction extends MarkAction {
  override val markType = "italic"
}

case object UnderlineAction extends MarkAction {
  override val markType = "underline"
}

case object CodeAction extends MarkAction {
  override val markType = "code"
}

case object StrikeThroughAction extends MarkAction {
  override val markType = "strikethrough"
}
