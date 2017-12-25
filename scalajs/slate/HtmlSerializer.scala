// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.scalajs.slate

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}
import scala.scalajs.js.|

import org.scalajs.dom.Element

import anduin.scalajs.slate.Slate.{Mark, Node, Value}

// See https://github.com/ianstormtaylor/slate/blob/master/docs/reference/slate-html-serializer/index.md
@JSImport("slate-html-serializer", JSImport.Default, "Html")
@js.native
class HtmlSerializer(
  val rules: js.UndefOr[js.Array[Rule]] = js.undefined
) extends js.Object {
  def deserialize(html: String): Value = js.native // linter:ignore UnusedParameter
  def serialize(value: Value): String = js.native // linter:ignore UnusedParameter
}

class Rule(
  val deserialize: js.Function2[Element, js.Function1[js.Array[Element], js.Array[Node]], RuleDeserializeOutput | Unit],
  val serialize: js.Function2[Node | Mark | String, String, js.Object | Unit]
) extends js.Object

class RuleDeserializeOutput(
  val kind: String,
  @JSName("type")
  val nodeType: String,
  val nodes: js.Array[Node]
) extends js.Object
