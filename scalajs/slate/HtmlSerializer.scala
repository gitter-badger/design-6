// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.slate

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}
import scala.scalajs.js.|

import org.scalajs.dom.raw.NodeList
import org.scalajs.dom.{Element, Node}

import anduin.scalajs.slate.Slate.{Data, Value, ValueJson}

object HtmlSerializer {

  type SerializeOutputType = js.Object | Unit
  type DeserializeOutputType = RuleDeserializeOutput | Unit
  type DeserializeNextFn = js.Function1[NodeList, NodeList]

  /* HtmlSerializer */

  // See https://github.com/ianstormtaylor/slate/blob/master/docs/reference/slate-html-serializer/index.md
  @JSImport("slate-html-serializer", JSImport.Default, "Html")
  @js.native
  class HtmlSerializer(val options: Options) extends js.Object {
    def deserialize(html: String, options: HtmlDeserializeOptions): ValueJson = js.native
    def deserialize(html: String): Value = js.native
    def serialize(value: Value): String = js.native
  }

  /* HtmlDeserializeOptions */

  trait HtmlDeserializeOptions extends js.Object {
    val toJSON: Boolean
  }

  object HtmlDeserializeOptions {
    def apply(toJsonParam: Boolean): HtmlDeserializeOptions = {
      new HtmlDeserializeOptions {
        override val toJSON = toJsonParam
      }
    }
  }

  /* Options */

  trait Options extends js.Object {
    val rules: js.UndefOr[js.Array[Rule]] = js.undefined
    val parseHtml: js.UndefOr[js.Function1[String, Node]] = js.undefined
  }

  object Options {
    def apply(
      rulesParam: js.Array[Rule],
      parseHtmlParam: String => Node
    ): Options = {
      new Options {
        override val rules = js.defined(rulesParam)
        override val parseHtml = js.defined(parseHtmlParam)
      }
    }
  }

  /* Rule */

  trait Rule extends js.Object {
    val deserialize: js.Function2[Element, DeserializeNextFn, DeserializeOutputType]
    val serialize: js.Function2[RuleSerializeInput, js.Object, SerializeOutputType]
  }

  object Rule {
    def apply(
      deserializeParam: js.Function2[Element, DeserializeNextFn, DeserializeOutputType],
      serializeParam: js.Function2[RuleSerializeInput, js.Object, SerializeOutputType]
    ): Rule = {
      new Rule {
        override val deserialize = deserializeParam
        override val serialize = serializeParam
      }
    }
  }

  /* RuleSerializeInput */

  trait RuleSerializeInput extends js.Object {
    val `object`: String
    @JSName("type") val tpe: String
    val data: Data
  }

  /* RuleDeserializeOutput */

  trait RuleDeserializeOutput extends js.Object {
    // Can be either `block`, `inline`, `mark` or `text`
    val `object`: String
    val data: js.UndefOr[js.Object] = js.undefined
    val `type`: String
    val nodes: NodeList
    val isVoid: Boolean
  }

  object RuleDeserializeOutput {
    def apply(
      typeParam: String,
      objectParam: String,
      nodesParam: NodeList,
      dataParam: Option[js.Object] = None,
      isVoidParam: Boolean = false
    ): RuleDeserializeOutput = {
      new RuleDeserializeOutput {
        override val `type` = typeParam
        override val `object` = objectParam
        override val nodes = nodesParam
        override val data = dataParam.map(js.defined(_)).getOrElse(js.undefined)
        override val isVoid = isVoidParam
      }
    }
  }
}
