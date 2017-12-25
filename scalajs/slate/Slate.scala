// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.scalajs.slate

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}

// scalastyle:off multiple.string.literals
object Slate {

  class Value extends js.Object

  @JSImport("slate", "Value")
  @js.native
  object Value extends js.Object {
    def fromJSON(json: js.Object): Value = js.native // linter:ignore UnusedParameter
  }

  // See https://docs.slatejs.org/slate-core/change
  @JSImport("slate", "Change")
  @js.native
  class Change(val value: Value) extends js.Object {
    def addMark(mark: String): Change = js.native // linter:ignore UnusedParameter
    def toggleMark(mark: String): Change = js.native // linter:ignore UnusedParameter
  }

  // See https://docs.slatejs.org/slate-core/mark
  @JSImport("slate", "Mark")
  @js.native
  class Mark(
    @JSName("type")
    val markType: String,
    val kind: String
  ) extends js.Object

  // See https://docs.slatejs.org/slate-core/node
  @js.native
  trait Node extends js.Object {
    val key: String = js.native
    val kind: String = js.native
    val nodes: js.Array[Node] = js.native
  }
}
// scalastyle:on multiple.string.literals
