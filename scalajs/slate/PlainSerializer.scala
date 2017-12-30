// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.slate

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import anduin.scalajs.slate.Slate.Value

// See https://github.com/ianstormtaylor/slate/blob/master/docs/reference/slate-plain-serializer/index.md
@JSImport("slate-plain-serializer", JSImport.Default, "Plain")
@js.native
object PlainSerializer extends js.Object {
  def deserialize(str: String): Value = js.native // linter:ignore UnusedParameter
  def serialize(value: Value): String = js.native // linter:ignore UnusedParameter
}
