// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.dropdown

import anduin.scalajs.downshift.{Downshift, DownshiftState, DownshiftStateChanges, DownshiftUtils}

import scala.scalajs.js.{UndefOr, |}
import scala.scalajs.js.annotation.JSName

private[dropdown] class DropdownStateReducer[A] {

  private type State = DownshiftState[A]
  private type Changes = DownshiftStateChanges[A]
  private val Types = Downshift.stateChangeTypes

  case class Data(isInnerClick: Boolean)
  case class Input(state: State, changes: Changes, data: Data)

  // Clear inputValue when menu is just opened or closed
  private def clearInput(input: Input): Input = {
    if (input.changes.isOpen.exists(_ != input.state.isOpen)) {
      val update = new DownshiftStateChanges[A] { override val inputValue = "" }
      val nextChanges = DownshiftUtils.mergeChanges(input.changes, update)
      input.copy(changes = nextChanges)
    } else {
      input
    }
  }

  // Prevent closing when clicking on the area created by React's Portal
  // inside Dropdown's content e.g. Header and Footer.
  // - The reason we need this is because Downshift use native the
  //   element.contains methods to detect outer click, which does not work
  //   with React Portal.
  // - See:
  //   - https://stackoverflow.com/q/47865209
  //   - https://github.com/anduintransaction/stargazer/issues/18073
  //   - https://github.com/anduintransaction/stargazer/pull/18088
  private def preventClosing(input: Input): Input = {
    if (input.changes.tpe.contains(Types.mouseUp) &&
        input.changes.isOpen.exists(_ == false) &&
        input.data.isInnerClick) {
      input.copy(changes = new DownshiftStateChanges[A] {
        @JSName("type")
        override val tpe: UndefOr[String | Int] = input.changes.tpe
      })
    } else {
      input
    }
  }

  def get(input: Input): Changes = {
    val composed = clearInput _ andThen preventClosing
    composed(input).changes
  }
}
