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
  type DeserializeNextFn = NodeList => NodeList

  /* HtmlSerializer */

  // See https://github.com/ianstormtaylor/slate/blob/master/docs/reference/slate-html-serializer/index.md
  // and https://docs.slatejs.org/other-packages/slate-html-serializer
  @JSImport("slate-html-serializer", JSImport.Default, "Html")
  @js.native
  class HtmlSerializer extends js.Object {
    val options: js.UndefOr[Options] = js.native

    def deserialize(html: String, options: HtmlDeserializeOptions): ValueJson = js.native
    def deserialize(html: String): Value = js.native
    def serialize(value: Value): String = js.native
  }

  object HtmlSerializer {
    def apply(
      optionsParam: Options
    ): HtmlSerializer = {
      new HtmlSerializer {
        override val options = js.defined(optionsParam)
      }
    }
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
    val deserialize: (Element, DeserializeNextFn) => DeserializeOutputType
    val serialize: (RuleSerializeInput, js.Object) => SerializeOutputType
  }

  object Rule {
    def apply(
      deserializeParam: (Element, DeserializeNextFn) => DeserializeOutputType,
      serializeParam: (RuleSerializeInput, js.Object) => SerializeOutputType
    ): Rule = {
      new Rule {
        override val deserialize = deserializeParam
        override val serialize = serializeParam
      }
    }
  }

  final class RuleSerializeInput(
    val `object`: String,
    @JSName("type") val tpe: String,
    val data: Data
  ) extends js.Object

  final class RuleDeserializeOutput(
    // Can be either `block`, `inline`, `mark` or `text`
    val `object`: String,
    val data: js.UndefOr[js.Object] = js.undefined,
    @JSName("type") val tpe: String,
    val nodes: NodeList,
    val isVoid: Boolean = false
  ) extends js.Object
}
