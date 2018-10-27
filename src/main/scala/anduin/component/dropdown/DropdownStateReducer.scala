// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.dropdown

import anduin.scalajs.downshift.{Downshift, DownshiftState, DownshiftStateChanges}

import scala.scalajs.js
import scala.scalajs.js.UndefOr
import scala.scalajs.js.annotation.JSName

private[dropdown] class DropdownStateReducer[A] {

  private type State = DownshiftState[A]
  private type Changes = DownshiftStateChanges[A]
  private val Types = Downshift.stateChangeTypes

  case class Data(isInnerClick: Boolean)
  case class Input(state: State, changes: Changes, data: Data)

  // Clear inputValue when menu opens
  private def clearInput(input: Input): Input = {
    if (!input.changes.tpe.contains(Types.changeInput) &&
        input.changes.isOpen.exists(_ == true)) {
      val nextChanges = merge(input.changes, new DownshiftStateChanges[A] {
        override val inputValue = ""
      })
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
  //   - and the PR of this change
  private def preventClosing(input: Input): Input = {
    if (input.changes.tpe.contains(Types.mouseUp) &&
      input.changes.isOpen.exists(_ == false) &&
      input.data.isInnerClick) {
      input.copy(changes = new DownshiftStateChanges[A] {
        @JSName("type")
        override val tpe: UndefOr[String] = input.changes.tpe
      })
    } else {
      input
    }
  }

  def get(input: Input): Changes = {
    val composed = clearInput _ andThen preventClosing
    composed(input).changes
  }

  // Utils ===

  // Because Scala.js does not support ES2015 yet and
  // we cannot "copy" a ScalaJS-defined class
  @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf")) // scalastyle:ignore
  private def merge(prev: Changes, next: Changes): Changes = {
    val empty = js.Dynamic.literal()
    js.Dynamic.global.Object.assign(empty, prev, next).asInstanceOf[Changes]
  }
}
