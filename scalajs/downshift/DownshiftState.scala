// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.downshift

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName
import scala.scalajs.js.|

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

trait DownshiftStateChangeTypes extends js.Object {
  val unknown: String | Int
  val mouseUp: String | Int
  val itemMouseEnter: String | Int
  val keyDownArrowUp: String | Int
  val keyDownArrowDown: String | Int
  val keyDownEscape: String | Int
  val keyDownEnter: String | Int
  val clickItem: String | Int
  val blurInput: String | Int
  val changeInput: String | Int
  val keyDownSpaceButton: String | Int
  val clickButton: String | Int
  val blurButton: String | Int
  val controlledPropUpdatedSelectedItem: String | Int
  val touchStart: String | Int
}
