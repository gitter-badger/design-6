// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.email

import org.scalajs.dom.raw.{DOMParser, Document, Node}

import anduin.component.util.NodeListSeq

// scalastyle:off multiple.string.literals
private[email] object QuoteTransformer {

  def removeQuotedHtml(content: String): String = {
    val doc = parseHtml(content)

    // Remove blockquotes
    findQuoteElements(doc).foreach { node =>
      Option(node.parentNode).map(_.removeChild(node))
    }

    // Maybe all content was quoted and removed completely
    val bodyOpt = doc.querySelector("body")
    val wasRemovedCompletely = doc.childNodes.length == 0 || Option(bodyOpt).fold(true)(_.textContent.trim.isEmpty)
    if (wasRemovedCompletely) {
      outputHtml(parseHtml(content), content)
    }

    if (Option(bodyOpt).isEmpty) {
      outputHtml(parseHtml(""), content)
    }

    outputHtml(doc, content)
  }

  def hasQuotedHtml(content: String): Boolean = {
    val doc = parseHtml(content)
    findQuoteElements(doc).nonEmpty
  }

  private def outputHtml(doc: Document, initialHtml: String): String = {
    val theDoc = if (Option(doc.querySelector("body")).isEmpty) {
      parseHtml("")
    } else {
      doc
    }

    if (initialHtml.contains("<head>") || initialHtml.contains("<body>")) {
      theDoc.firstElementChild.innerHTML
    } else {
      theDoc.querySelector("body").innerHTML
    }
  }

  private def parseHtml(content: String): Document = {
    new DOMParser().parseFromString(content, "text/html")
  }

  private def findQuoteElements(doc: Document): List[Node] = {
    findGmailQuotes(doc) ++ findBlockquoteQuotes(doc)
  }

  private def findGmailQuotes(doc: Document): List[Node] = {
    NodeListSeq(doc.querySelectorAll(".gmail_quote")).toList
  }

  private def findBlockquoteQuotes(doc: Document): List[Node] = {
    NodeListSeq(doc.querySelectorAll("blockquote")).toList
  }
}
// scalastyle:on multiple.string.literals
