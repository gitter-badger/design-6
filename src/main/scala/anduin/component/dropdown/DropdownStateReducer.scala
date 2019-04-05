// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.dropdown

import anduin.scalajs.downshift.{Downshift, DownshiftState, DownshiftStateChanges, DownshiftUtils}

import scala.scalajs.js

private[dropdown] class DropdownStateReducer[A] {

  private type Changes = DownshiftStateChanges[A]
  private val Types = Downshift.stateChangeTypes

  /* Downshift sometimes set the inputValue to selectedItem automatically. We
   * need to prevent this because we are using inputValue to filter options, so
   * setting it to selectedItem on first render would result in only 1 option
   * being shown
   */
  private def clearInputValue(changes: Changes): Changes = {
    if (changes.inputValue.isDefined && !changes.tpe.contains(Types.changeInput)) {
      val update = new Changes { override val inputValue: js.UndefOr[String] = js.defined("") }
      DownshiftUtils.mergeChanges(changes, update)
    } else {
      changes
    }
  }

  /* Downshift closes the menu when users click outside of it. However, its
   * [condition check][1] doesn't work with React's Portal, so we skip it and
   * close the menu ourselves (see Dropdown > PopoverContent > onOverlayClick)
   *
   * [1] https://github.com/downshift-js/downshift/blob/dddd76caa62b4011ff1f71f8709f08a82b952fb6/src/utils.js#L41-L48
   */
  private def preventDefaultOuterClick(changes: Changes): Changes = {
    if (changes.isOpen.isDefined && changes.tpe.contains(Types.mouseUp)) {
      val update = new Changes { override val isOpen: js.UndefOr[Boolean] = js.defined(true) }
      DownshiftUtils.mergeChanges(changes, update)
    } else {
      changes
    }
  }

  /* So far we don't need info from current state so we are just gonna skip
   * it here to simplify the composition. When we need it, simply have a
   * case class Input(state: State, changes: Changes) as the parameter
   */
  def get(state: DownshiftState[A], changes: Changes): Changes = {
    val _ = state
    (preventDefaultOuterClick _ andThen clearInputValue)(changes)
  }
}
