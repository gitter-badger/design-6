// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.slate

import scala.scalajs.js

import org.scalajs.dom.Element
import org.scalajs.dom.raw.NodeList

import anduin.scalajs.slate.Slate.Value

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.scalajs.slate.HtmlSerializer._
// scalastyle:on underscore.import

object Serializer {

  private final val BlockTags = Map(
    "blockquote" -> BlockQuoteNode.nodeType,
    "p" -> ParagraphNode.nodeType,
    "pre" -> CodeNode.nodeType,
    "li" -> ListItemNode.nodeType,
    "ul" -> UnorderedListNode.nodeType,
    "ol" -> OrderedListNode.nodeType
  )

  private final val InlineTags = Map(
    "a" -> LinkNode.nodeType
  )

  private final val MarkTags = Map(
    "strong" -> BoldNode.nodeType,
    "em" -> ItalicNode.nodeType,
    "u" -> UnderlineNode.nodeType,
    "del" -> StrikeThroughNode.nodeType
  )

  // See https://docs.slatejs.org/walkthroughs/saving-and-loading-html-content
  private val blockHandler = new Rule(
    deserialize = (ele: Element, next: js.Function1[NodeList, NodeList]) => {
      BlockTags.get(ele.tagName.toLowerCase).fold[DeserializeOutputType](()) { tpe =>
        new RuleDeserializeOutput(
          kind = "block",
          tpe = tpe,
          nodes = next(ele.childNodes)
        )
      }
    },
    serialize = (obj: RuleSerializeInput, children: js.Object) => {
      val res: SerializeOutputType = if (obj.kind != "block") {
        ()
      } else {
        obj.tpe match {
          case CodeNode.nodeType => <.pre(<.code(createChildren(children))).rawElement
          case ParagraphNode.nodeType => <.p(createChildren(children)).rawElement
          case BlockQuoteNode.nodeType => <.blockquote(createChildren(children)).rawElement
          case ListItemNode.nodeType => <.li(createChildren(children)).rawElement
          case UnorderedListNode.nodeType => <.ul(createChildren(children)).rawElement
          case OrderedListNode.nodeType => <.ol(createChildren(children)).rawElement
        }
      }
      res
    }
  )
  private val inlineHandler = new Rule(
    deserialize = (ele: Element, next: js.Function1[NodeList, NodeList]) => {
      InlineTags.get(ele.tagName.toLowerCase).fold[DeserializeOutputType](()) { tpe =>
        new RuleDeserializeOutput(
          kind = "inline",
          tpe = tpe,
          data = js.defined(js.Dynamic.literal(href = ele.getAttribute("href"))),
          nodes = next(ele.childNodes)
        )
      }
    },
    serialize = (obj: RuleSerializeInput, children: js.Object) => {
      val res: SerializeOutputType = if (obj.kind != "inline") {
        ()
      } else {
        obj.tpe match {
          case LinkNode.nodeType =>
            val href = obj.data.get("href").toOption.map(_.asInstanceOf[String]).getOrElse("")
            <.a(^.href := href, createChildren(children)).rawElement
        }
      }
      res
    }
  )

  private val markHandler = new Rule(
    deserialize = (ele: Element, next: js.Function1[NodeList, NodeList]) => {
      MarkTags.get(ele.tagName.toLowerCase).fold[DeserializeOutputType](()) { nodeType =>
        new RuleDeserializeOutput(
          kind = "mark",
          tpe = nodeType,
          nodes = next(ele.childNodes)
        )
      }
    },
    serialize = (obj: RuleSerializeInput, children: js.Object) => {
      val res: SerializeOutputType = if (obj.kind != "mark") {
        ()
      } else {
        obj.tpe match {
          case BoldNode.nodeType => <.strong(createChildren(children)).rawElement
          case ItalicNode.nodeType => <.em(createChildren(children)).rawElement
          case UnderlineNode.nodeType => <.u(createChildren(children)).rawElement
          case StrikeThroughNode.nodeType => <.del(createChildren(children)).rawElement
        }
      }
      res
    }
  )

  private val htmlSerializer = new HtmlSerializer(new Options(js.Array(blockHandler, inlineHandler, markHandler)))

  private def createChildren(children: js.Object) = PropsChildren.fromRawProps(js.Dynamic.literal(children = children))

  def deserialize(rawHtml: String): Value = {
    htmlSerializer.deserialize(rawHtml)
  }

  def serialize(value: Value): String = {
    htmlSerializer.serialize(value)
  }
}
