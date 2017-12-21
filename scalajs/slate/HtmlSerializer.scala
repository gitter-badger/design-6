// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.scalajs.slate

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import org.scalajs.dom.{Element, Node}

import anduin.scalajs.slate.Slate.Value

// See https://github.com/ianstormtaylor/slate/blob/master/docs/reference/slate-html-serializer/index.md
@JSImport("slate-html-serializer", JSImport.Default, "Html")
@js.native
class HtmlSerializer(
  val rules: js.UndefOr[js.Array[Rule]] = js.undefined
) extends js.Object {
  def deserialize(html: String): Value = js.native // linter:ignore UnusedParameter
  def serialize(value: Value): String = js.native // linter:ignore UnusedParameter
}

object HtmlSerializer extends js.Object

class Rule(
  val deserialize: js.Function2[Element, js.Function, js.Object],
  val serialize: js.Function2[Node, String, js.Object]
) extends js.Object
