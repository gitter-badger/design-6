// Copyright (C) 2014-2017 Anduin Transactions Inc.

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
    "blockquote" -> "quote",
    "p" -> "paragraph",
    "pre" -> "code"
  )

  private final val InlineTags = Map(
    "a" -> "link"
  )

  private final val MarkTags = Map(
    "strong" -> BoldAction,
    "em" -> ItalicAction,
    "u" -> UnderlineAction
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
        val p = js.Dynamic.literal(children = children)
        obj.tpe match {
          case "code" => <.pre(<.code(PropsChildren.fromRawProps(p))).rawElement
          case "paragraph" => <.p(PropsChildren.fromRawProps(p)).rawElement
          case "quote" => <.blockquote(PropsChildren.fromRawProps(p)).rawElement
          case _ => null // scalastyle:ignore null
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
        val p = js.Dynamic.literal(children = children)
        obj.tpe match {
          case "link" => {
            val href = obj.data.get("href").toOption.map(_.asInstanceOf[String]).getOrElse("")
            <.a(^.href := href, PropsChildren.fromRawProps(p)).rawElement
          }
          case _ => null // scalastyle:ignore null
        }
      }
      res
    }
  )

  private val markHandler = new Rule(
    deserialize = (ele: Element, next: js.Function1[NodeList, NodeList]) => {
      MarkTags.get(ele.tagName.toLowerCase).fold[DeserializeOutputType](()) { action =>
        new RuleDeserializeOutput(
          kind = "mark",
          tpe = action.markType,
          nodes = next(ele.childNodes)
        )
      }
    },
    serialize = (obj: RuleSerializeInput, children: js.Object) => {
      val res: SerializeOutputType = if (obj.kind != "mark") {
        ()
      } else {
        val p = js.Dynamic.literal(children = children)
        obj.tpe match {
          case BoldAction.markType => <.strong(PropsChildren.fromRawProps(p)).rawElement
          case ItalicAction.markType => <.em(PropsChildren.fromRawProps(p)).rawElement
          case UnderlineAction.markType => <.u(PropsChildren.fromRawProps(p)).rawElement
          case _ => null // scalastyle:ignore null
        }
      }
      res
    }
  )

  private val htmlSerializer = new HtmlSerializer(new Options(js.Array(blockHandler, inlineHandler, markHandler)))

  def deserialize(rawHtml: String): Value = {
    htmlSerializer.deserialize(rawHtml)
  }

  def serialize(value: Value): String = {
    htmlSerializer.serialize(value)
  }
}
