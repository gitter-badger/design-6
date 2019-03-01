// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor

import scala.scalajs.js

import org.scalajs.dom.Element
import org.scalajs.dom.raw.DOMParser

import anduin.component.editor.renderer.{ImageRenderer, LinkRenderer, MarkRenderer, TextAlignRenderer}
import anduin.component.editor.serializer.HtmlNormalizer
import anduin.component.util.NodeListSeq
import anduin.scalajs.caja.{Caja, URI}
import anduin.scalajs.slate.Slate.Value
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.scalajs.slate.HtmlSerializer._
// scalastyle:on underscore.import

// scalastyle:off multiple.string.literals
object Serializer {

  private val BlockTags = Map(
    "blockquote" -> BlockQuoteNode.nodeType,
    "p" -> ParagraphNode.nodeType,
    "pre" -> CodeNode.nodeType,
    "li" -> ListItemNode.nodeType,
    "ul" -> UnorderedListNode.nodeType,
    "ol" -> OrderedListNode.nodeType,
    "div" -> DivNode.nodeType
  )

  private val MarkTags = Map(
    "strong" -> BoldNode.nodeType,
    "em" -> ItalicNode.nodeType,
    "u" -> UnderlineNode.nodeType,
    "del" -> StrikeThroughNode.nodeType
  )

  private val TextAlignmentTags = BlockTags

  private val BlockQuoteAttrs = TagMod(
    ^.borderLeft := "2px solid #bfccd6",
    ^.margin := "0px",
    ^.padding := "10px"
  )

  // See https://docs.slatejs.org/walkthroughs/saving-and-loading-html-content
  private val blockHandler = Rule(
    deserializeParam = (ele: Element, next: DeserializeNextFn) => {
      BlockTags.get(ele.tagName.toLowerCase).fold[DeserializeOutputType](()) { tpe =>
        RuleDeserializeOutput(
          objectParam = "block",
          typeParam = tpe,
          nodesParam = next(ele.childNodes),
          dataParam = Some(
            js.Dynamic.literal(
              style = StyleParser.parseStyle(ele)
            )
          )
        )
      }
    },
    serializeParam = (obj: RuleSerializeInput, children: js.Object) => {
      val res: SerializeOutputType = if (obj.`object` != "block") {
        ()
      } else {
        val styleTagMod = StyleParser.getStyleTagMod(obj.data)
        obj.tpe match {
          case CodeNode.nodeType      => <.pre(styleTagMod, <.code(createChildren(children))).rawElement
          case ParagraphNode.nodeType => <.p(styleTagMod, Style.margin.bottom12, createChildren(children)).rawElement
          case BlockQuoteNode.nodeType =>
            <.blockquote(
              styleTagMod,
              BlockQuoteAttrs,
              createChildren(children)
            ).rawElement
          case ListItemNode.nodeType      => <.li(styleTagMod, createChildren(children)).rawElement
          case UnorderedListNode.nodeType => <.ul(styleTagMod, createChildren(children)).rawElement
          case OrderedListNode.nodeType   => <.ol(styleTagMod, createChildren(children)).rawElement
          case DivNode.nodeType           => <.div(styleTagMod, createChildren(children)).rawElement
        }
      }
      res
    }
  )

  private val textAlignmentHandler = Rule(
    deserializeParam = (ele: Element, next: DeserializeNextFn) => {
      TextAlignmentTags.get(ele.tagName.toLowerCase).fold[DeserializeOutputType](()) { tpe =>
        val textAlign = StyleParser.textAlign(ele)
        if (textAlign.isEmpty) {
          ()
        } else {
          RuleDeserializeOutput(
            objectParam = "block",
            typeParam = TextAlignNode.nodeType,
            dataParam = Some(
              js.Dynamic.literal(
                textAlign = textAlign,
                originalType = tpe,
                style = StyleParser.parseStyle(ele)
              )
            ),
            nodesParam = next(ele.childNodes)
          )
        }
      }
    },
    serializeParam = (obj: RuleSerializeInput, children: js.Object) => {
      val res: SerializeOutputType = if (obj.`object` != "block" || obj.tpe != TextAlignNode.nodeType) {
        ()
      } else {
        TextAlignRenderer(obj.data, children)
      }
      res
    }
  )

  private val linkHandler = Rule(
    deserializeParam = (ele: Element, next: DeserializeNextFn) => {
      if (ele.tagName.toLowerCase != "a") {
        ()
      } else {
        RuleDeserializeOutput(
          objectParam = "inline",
          typeParam = LinkNode.nodeType,
          dataParam = Some(
            js.Dynamic.literal(
              href = ele.getAttribute("href"),
              target = ele.getAttribute("target"),
              style = StyleParser.parseStyle(ele)
            )
          ),
          nodesParam = next(ele.childNodes)
        )
      }
    },
    serializeParam = (obj: RuleSerializeInput, children: js.Object) => {
      val res: SerializeOutputType = if (obj.`object` != "inline" || obj.tpe != LinkNode.nodeType) {
        ()
      } else {
        LinkRenderer(obj.data, children, true)
      }
      res
    }
  )

  private val imageHandler = Rule(
    deserializeParam = (ele: Element, next: DeserializeNextFn) => {
      if (ele.tagName.toLowerCase != "img") {
        ()
      } else {
        val source = ele.getAttribute("src")
        if (source != null && (source.startsWith("http://") || source.startsWith("https://"))) {
          RuleDeserializeOutput(
            objectParam = "inline",
            typeParam = ImageNode.nodeType,
            isVoidParam = true,
            dataParam = Some(
              js.Dynamic.literal(
                source = ele.getAttribute("src"),
                width = ele.getAttribute("width"),
                height = ele.getAttribute("height"),
                style = StyleParser.parseStyle(ele)
              )
            ),
            nodesParam = next(ele.childNodes)
          )
        } else {
          ()
        }
      }
    },
    serializeParam = (obj: RuleSerializeInput, _: js.Object) => {
      val res: SerializeOutputType = if (obj.`object` != "inline" || obj.tpe != ImageNode.nodeType) {
        ()
      } else {
        ImageRenderer(obj.data)
      }
      res
    }
  )

  private val markHandler = Rule(
    deserializeParam = (ele: Element, next: DeserializeNextFn) => {
      MarkTags.get(ele.tagName.toLowerCase).fold[DeserializeOutputType](()) { nodeType =>
        RuleDeserializeOutput(
          objectParam = "mark",
          typeParam = nodeType,
          nodesParam = next(ele.childNodes),
          dataParam = Some(
            js.Dynamic.literal(
              style = StyleParser.parseStyle(ele)
            )
          )
        )
      }
    },
    serializeParam = (obj: RuleSerializeInput, children: js.Object) => {
      val res: SerializeOutputType = if (obj.`object` != "mark") () else MarkRenderer(obj.tpe, children)
      res
    }
  )

  private val htmlSerializer = new HtmlSerializer(
    Options(
      rulesParam = js.Array(
        // The order of rules are important
        // We need to put the text alignment before block rule
        textAlignmentHandler,
        blockHandler,
        linkHandler,
        imageHandler,
        markHandler
      ),
      parseHtmlParam = html => {
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

  private val sanitizeUri: js.Function1[URI, String] = { uri =>
    uri.toString
  }

  def deserialize(rawHtml: String): Value = {
    val trim = rawHtml
      .replaceAll("(\n)+", "\n") // Reduce the number of new lines
      // We have to remove spaces between tags. Otherwise, it can't render the nested blocks
      // See
      // - Working version: https://jsfiddle.net/oj53q1n2/10/
      // - Not working version: https://jsfiddle.net/oj53q1n2/11/
      .replaceAll(">\\s+<", "><")
      .trim
    val sanitized = Caja.htmlSanitize(html = trim, urlTransformer = sanitizeUri)
    val valueJson = htmlSerializer.deserialize(sanitized, HtmlDeserializeOptions(toJsonParam = true))
    HtmlNormalizer(valueJson)
  }

  def serialize(value: Value): String = {
    htmlSerializer.serialize(value)
  }
}
// scalastyle:on multiple.string.literals
