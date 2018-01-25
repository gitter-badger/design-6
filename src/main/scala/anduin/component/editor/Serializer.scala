// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor

import scala.scalajs.js

import org.scalajs.dom.Element
import org.scalajs.dom.raw.{DOMParser, NodeList}

import anduin.component.editor.renderer.{ImageRenderer, LinkRenderer, MarkRenderer, TextAlignRenderer}
import anduin.component.util.NodeListSeq
import anduin.scalajs.slate.Slate.Value

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.scalajs.slate.HtmlSerializer._
// scalastyle:on underscore.import

// scalastyle:off multiple.string.literals
object Serializer {

  private final val BlockTags = Map(
    "blockquote" -> BlockQuoteNode.nodeType,
    "p" -> ParagraphNode.nodeType,
    "pre" -> CodeNode.nodeType,
    "li" -> ListItemNode.nodeType,
    "ul" -> UnorderedListNode.nodeType,
    "ol" -> OrderedListNode.nodeType
  )

  private final val MarkTags = Map(
    "strong" -> BoldNode.nodeType,
    "em" -> ItalicNode.nodeType,
    "u" -> UnderlineNode.nodeType,
    "del" -> StrikeThroughNode.nodeType
  )

  private final val TextAlignmentTags = Map("div" -> DivNode.nodeType) ++ BlockTags

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
          case CodeNode.nodeType          => <.pre(<.code(createChildren(children))).rawElement
          case ParagraphNode.nodeType     => <.p(createChildren(children)).rawElement
          case BlockQuoteNode.nodeType    => <.blockquote(createChildren(children)).rawElement
          case ListItemNode.nodeType      => <.li(createChildren(children)).rawElement
          case UnorderedListNode.nodeType => <.ul(createChildren(children)).rawElement
          case OrderedListNode.nodeType   => <.ol(createChildren(children)).rawElement
        }
      }
      res
    }
  )

  private val textAlignmentHandler = new Rule(
    deserialize = (ele: Element, next: js.Function1[NodeList, NodeList]) => {
      TextAlignmentTags.get(ele.tagName.toLowerCase).fold[DeserializeOutputType](()) { tpe =>
        val textAlign = StyleParser.textAlign(ele)
        if (textAlign.isEmpty) {
          ()
        } else {
          new RuleDeserializeOutput(
            kind = "block",
            tpe = TextAlignNode.nodeType,
            data = js.defined(
              js.Dynamic.literal(
                textAlign = textAlign,
                originalType = tpe
              )
            ),
            nodes = next(ele.childNodes)
          )
        }
      }
    },
    serialize = (obj: RuleSerializeInput, children: js.Object) => {
      val res: SerializeOutputType = if (obj.kind != "block" || obj.tpe != TextAlignNode.nodeType) {
        ()
      } else {
        TextAlignRenderer(obj.data, children)
      }
      res
    }
  )

  private val linkHandler = new Rule(
    deserialize = (ele: Element, next: js.Function1[NodeList, NodeList]) => {
      if (ele.tagName.toLowerCase != "a") {
        ()
      } else {
        new RuleDeserializeOutput(
          kind = "inline",
          tpe = LinkNode.nodeType,
          data = js.defined(js.Dynamic.literal(href = ele.getAttribute("href"))),
          nodes = next(ele.childNodes)
        )
      }
    },
    serialize = (obj: RuleSerializeInput, children: js.Object) => {
      val res: SerializeOutputType = if (obj.kind != "inline" || obj.tpe != LinkNode.nodeType) {
        ()
      } else {
        LinkRenderer(obj.data, children)
      }
      res
    }
  )

  private val imageHandler = new Rule(
    deserialize = (ele: Element, next: js.Function1[NodeList, NodeList]) => {
      if (ele.tagName.toLowerCase != "img") {
        ()
      } else {
        val source = ele.getAttribute("src")
        if (source != null && (source.startsWith("http://") || source.startsWith("https://"))) {
          new RuleDeserializeOutput(
            kind = "inline",
            tpe = ImageNode.nodeType,
            isVoid = true,
            data = js.defined(
              js.Dynamic.literal(
                source = ele.getAttribute("src"),
                width = ele.getAttribute("width"),
                height = ele.getAttribute("height")
              )
            ),
            nodes = next(ele.childNodes)
          )
        } else {
          ()
        }
      }
    },
    serialize = (obj: RuleSerializeInput, _: js.Object) => {
      val res: SerializeOutputType = if (obj.kind != "inline" || obj.tpe != ImageNode.nodeType) {
        ()
      } else {
        ImageRenderer(obj.data)
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
      val res: SerializeOutputType = if (obj.kind != "mark") () else MarkRenderer(obj.tpe, children)
      res
    }
  )

  private val htmlSerializer = new HtmlSerializer(
    new Options(
      rules = js.Array(
        // The order of rules are important
        // We need to put the text alignment before block rule
        textAlignmentHandler,
        blockHandler,
        linkHandler,
        imageHandler,
        markHandler
      ),
      parseHtml = js.defined { (html: String) =>
        val parsed = new DOMParser().parseFromString(html, "text/html")
        val body = parsed.querySelector("body")

        // List of tags which will be removed from the body
        // TODO: Use Slate's schema (https://docs.slatejs.org/guides/schemas)
        val removedTags = List("meta", "link", "style")
        removedTags.foreach { tag =>
          NodeListSeq(body.querySelectorAll(tag)).foreach { node =>
            node.parentNode.removeChild(node)
          }
        }

        body
      }
    )
  )

  private def createChildren(children: js.Object) =
    PropsChildren.fromRawProps(js.Dynamic.literal(children = children))

  def deserialize(rawHtml: String): Value = {
    val trim = rawHtml
      .replaceAll("(\n)+", "\n") // Reduce the number of new lines
      // We have to remove spaces between tags. Otherwise, it can't render the nested blocks
      // See
      // - Working version: https://jsfiddle.net/oj53q1n2/10/
      // - Not working version: https://jsfiddle.net/oj53q1n2/11/
      .replaceAll(">\\s+<", "><")
      .trim

    htmlSerializer.deserialize(trim)
  }

  def serialize(value: Value): String = {
    htmlSerializer.serialize(value)
  }
}
// scalastyle:on multiple.string.literals
