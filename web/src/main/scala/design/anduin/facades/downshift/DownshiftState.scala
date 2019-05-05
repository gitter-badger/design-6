// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.facades.downshift

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName
import scala.scalajs.js.|

trait DownshiftState[A] extends js.Object {
  val highlightedIndex: Int | Null
  val inputValue: String
  val isOpen: Boolean
  val selectedItem: A | Null
}

trait DownshiftStateChanges[A] extends js.Object {
  // This is `UndefOr` since this trait is used in 2 ways:
  // - As provided parameter of stateReducer
  // - As returned result of stateReducer
  // The latter requires the `UndefOr`
  @JSName("type")
  val tpe: js.UndefOr[String | Int] = js.undefined
  // These are similar to DownshiftState but may be undefined
  val highlightedIndex: js.UndefOr[Int | Null] = js.undefined
  val inputValue: js.UndefOr[String] = js.undefined
  val isOpen: js.UndefOr[Boolean] = js.undefined
  val selectedItem: js.UndefOr[A | Null] = js.undefined
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
