// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.downshift

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

trait DownshiftState[A] extends js.Object {
  val highlightedIndex: js.UndefOr[Int] = js.undefined
  val inputValue: js.UndefOr[String] = js.undefined
  val isOpen: js.UndefOr[Boolean] = js.undefined
  val selectedItem: js.UndefOr[A] = js.undefined
}

trait DownshiftStateChanges[A] extends js.Object with DownshiftState[A] {
  @JSName("type")
  val tpe: js.UndefOr[String] = js.undefined
}

object DownshiftStateChangeTypes {
  val unknown = "__autocomplete_unknown__"
  val mouseUp = "__autocomplete_mouseup__"
  val itemMouseEnter = "__autocomplete_item_mouseenter__"
  val keyDownArrowUp = "__autocomplete_keydown_arrow_up__"
  val keyDownArrowDown = "__autocomplete_keydown_arrow_down__"
  val keyDownEscape = "__autocomplete_keydown_escape__"
  val keyDownEnter = "__autocomplete_keydown_enter__"
  val clickItem = "__autocomplete_click_item__"
  val blurInput = "__autocomplete_blur_input__"
  val changeInput = "__autocomplete_change_input__"
  val keyDownSpaceButton = "__autocomplete_keydown_space_button__"
  val clickButton = "__autocomplete_click_button__"
  val blurButton = "__autocomplete_blur_button__"
  val controlledPropUpdatedSelectedItem = "__autocomplete_controlled_prop_updated_selected_item__"
  val touchStart = "__autocomplete_touchstart__"
}
