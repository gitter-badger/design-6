// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.scalajs.slate

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import anduin.scalajs.slate.Slate.{Change, Value}

object SlateReact {

  @JSImport("slate-react", "Editor")
  @js.native
  object EditorComponent extends js.Object

  final class Props(
    val value: Value,
    val onChange: js.UndefOr[js.Function1[Change, Unit]] = js.undefined
  ) extends js.Object
}
