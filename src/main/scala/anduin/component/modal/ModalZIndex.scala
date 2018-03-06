// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.modal

sealed trait ModalZIndex {
  def overlayStyles: Map[String, String]
}

// Set a very big z-index to display this modal on top of other modals
object ModalZIndex {

  case object SetLevelWrapperModal extends ModalZIndex {
    val overlayStyles: Map[String, String] = Map("zIndex" -> "9999")
  }

  case object EntitySuggestionModal extends ModalZIndex {
    val overlayStyles: Map[String, String] = Map("zIndex" -> "9990")
  }

  case object DefaultModal extends ModalZIndex {
    val overlayStyles: Map[String, String] = Map.empty
  }
}
