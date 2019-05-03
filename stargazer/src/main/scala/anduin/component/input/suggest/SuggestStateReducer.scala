// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.suggest

import anduin.scalajs.downshift.{Downshift, DownshiftState, DownshiftStateChanges, DownshiftUtils}

import scala.scalajs.js

private[suggest] class SuggestStateReducer[A] {

  private type Changes = DownshiftStateChanges[A]
  private type Data = (DownshiftState[A], DownshiftStateChanges[A])

  private def preventClearingInputValueOnBlur(data: Data): Data = {
    val (state, changes) = data
    val newChanges =
      if (changes.tpe.contains(Downshift.stateChangeTypes.blurInput) ||
          changes.tpe.contains(Downshift.stateChangeTypes.mouseUp)) {
        val update = new DownshiftStateChanges[A] {
          override val inputValue: js.UndefOr[String] = state.inputValue
        }
        DownshiftUtils.mergeChanges(changes, update)
      } else {
        changes
      }
    (state, newChanges)
  }

  def get(state: DownshiftState[A], changes: Changes): Changes = {
    preventClearingInputValueOnBlur((state, changes))._2
  }
}
