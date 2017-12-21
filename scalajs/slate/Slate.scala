// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.scalajs.slate

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object Slate {

  @JSImport("slate", JSImport.Namespace, "Value")
  @js.native
  object Value extends js.Object {
    def fromJSON(json: js.Object): Value = js.native // linter:ignore UnusedParameter
  }

  trait Value extends js.Object
}
