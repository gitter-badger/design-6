// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.scalajs.slate

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import anduin.scalajs.slate.Slate.Value

object SlateReact {

  @JSImport("slate-react", JSImport.Namespace, "Editor")
  @js.native
  object EditorComponent extends js.Object

  final class Props(
    val value: Value,
    val onChange: js.UndefOr[Value => Unit] = js.undefined
  ) extends js.Object
}
