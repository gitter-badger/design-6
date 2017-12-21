// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.scalajs.slate

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object Slate {

  class Value extends js.Object

  @JSImport("slate", "Value")
  @js.native
  object Value extends js.Object {
    def fromJSON(json: js.Object): Value = js.native // linter:ignore UnusedParameter
  }

  @JSImport("slate", "Change")
  @js.native
  class Change(val value: Value) extends js.Object
}
