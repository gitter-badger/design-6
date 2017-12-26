// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.scalajs.slate

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}

import anduin.scalajs.immutable.{ImmutableList, ImmutableSet}

// scalastyle:off multiple.string.literals
object Slate {

  // See https://docs.slatejs.org/slate-core/value
  @JSImport("slate", "Value")
  @js.native
  object Value extends js.Object {
    def fromJSON(json: js.Object): Value = js.native // linter:ignore UnusedParameter
  }

  @js.native
  trait Value extends js.Object {
    def activeMarks: ImmutableSet[Mark] = js.native // linter:ignore UnusedParameter
    def change(): Change = js.native
    def hasUndos: Boolean = js.native
    def hasRedos: Boolean = js.native
  }

  // See https://docs.slatejs.org/slate-core/change
  @JSImport("slate", "Change")
  @js.native
  class Change(val value: Value) extends js.Object {
    def addMark(mark: String): Change = js.native // linter:ignore UnusedParameter
    def toggleMark(mark: String): Change = js.native // linter:ignore UnusedParameter
    def undo(): Change = js.native
    def redo(): Change = js.native
  }

  // See https://docs.slatejs.org/slate-core/mark
  @JSImport("slate", "Mark")
  @js.native
  class Mark(
    @JSName("type") val markType: String,
    val kind: String
  ) extends js.Object

  // See https://docs.slatejs.org/slate-core/node
  @js.native
  trait Node extends js.Object {
    val key: String = js.native
    val kind: String = js.native
    @JSName("type") val nodeType: String = js.native
    val nodes: ImmutableList[Node] = js.native
  }
}
// scalastyle:on multiple.string.literals
