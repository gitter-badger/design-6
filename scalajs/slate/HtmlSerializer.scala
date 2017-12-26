// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.scalajs.slate

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}
import scala.scalajs.js.|

import org.scalajs.dom.Element
import org.scalajs.dom.raw.NodeList

import anduin.scalajs.slate.Slate.{Data, Value}

object HtmlSerializer {

  type SerializeOutputType = js.Object | Unit | Null
  type DeserializeOutputType = RuleDeserializeOutput | Unit

  // See https://github.com/ianstormtaylor/slate/blob/master/docs/reference/slate-html-serializer/index.md
  // and https://docs.slatejs.org/other-packages/slate-html-serializer
  @JSImport("slate-html-serializer", JSImport.Default, "Html")
  @js.native
  final class HtmlSerializer(
    val options: js.UndefOr[Options] = js.undefined
  ) extends js.Object {
    def deserialize(html: String): Value = js.native // linter:ignore UnusedParameter
    def serialize(value: Value): String = js.native // linter:ignore UnusedParameter
  }

  final class Options(
    val rules: js.UndefOr[js.Array[Rule]] = js.undefined
  ) extends js.Object

  final class Rule(
    val deserialize: js.Function2[Element, js.Function1[NodeList, NodeList], DeserializeOutputType],
    val serialize: js.Function2[RuleSerializeInput, js.Object, SerializeOutputType]
  ) extends js.Object

  final class RuleSerializeInput(
    val kind: String,
    @JSName("type") val tpe: String,
    val data: Data
  ) extends js.Object

  final class RuleDeserializeOutput(
    // Can be either `block`, `inline`, `mark` or `text`
    val kind: String,
    val data: js.UndefOr[js.Object] = js.undefined,
    @JSName("type") val tpe: String,
    val nodes: NodeList
  ) extends js.Object
}
