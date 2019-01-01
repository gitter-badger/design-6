// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.slate

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object SlateReact {

  @JSImport("slate-react", "Editor")
  @js.native
  object EditorComponent extends js.Object

  @js.native
  trait EditorComponent extends js.Object {
    def focus(): Unit = js.native
  }
}
