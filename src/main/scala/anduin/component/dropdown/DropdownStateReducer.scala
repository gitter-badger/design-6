// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.dropdown

import anduin.scalajs.downshift.{DownshiftState, DownshiftStateChangeTypes, DownshiftStateChanges}

import scala.scalajs.js

private[dropdown] class DropdownStateReducer[A] {

  private type State = DownshiftState[A]
  private type Changes = DownshiftStateChanges[A]
  private val Types = DownshiftStateChangeTypes

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

  def get(input: Input): Changes = {
    val composed = clearInput _
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
