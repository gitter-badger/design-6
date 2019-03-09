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

    removeUnnecessaryWhitespace(doc)
    outputHtml(doc, content)
  }

  def hasQuotedHtml(content: String): Boolean = {
    val doc = parseHtml(content)
    findQuoteElements(doc).nonEmpty
  }

  // Remove unnecessary whitespace from document.
  // It removes the duplicated multiple <br> (such as <br><br>)
  @SuppressWarnings(Array("org.wartremover.warts.While"))
  private def removeUnnecessaryWhitespace(doc: Document) = {
    Option(doc.querySelector("body")).foreach { body =>
      NodeListSeq(body.childNodes)
        .filter { node =>
          node.nodeName == "BR" && Option(node.nextSibling).exists(_.nodeName == "BR")
        }
        .foreach { node =>
          node.parentNode.removeChild(node)
        }

      // Determine the deepest node
      // scalastyle:off var.field var.local while
      var deepestNode = body
      while (Option(deepestNode.lastElementChild).nonEmpty) {
        deepestNode = deepestNode.lastElementChild
      }

      // Traverse back to the root and remove "empty" node
      var topNode: Node = deepestNode
      while (Option(topNode.parentNode).nonEmpty) {
        topNode = topNode.parentNode
        removeTrailingWhitespaceChildren(topNode)
      }
    // scalastyle:on var.field var.local while
    }
  }

  private def removeTrailingWhitespaceChildren(node: Node): Unit = {
    Option(node.lastChild).foreach { lastChild =>
      if (lastChild.nodeType == Node.TEXT_NODE && lastChild.textContent.trim.isEmpty) {
        lastChild.parentNode.removeChild(lastChild)
        removeTrailingWhitespaceChildren(node)
      } else if (List("BR", "P", "DIV", "SPAN", "HR").contains(lastChild.nodeName)) {
        removeTrailingWhitespaceChildren(lastChild)
        if (!lastChild.hasChildNodes() || lastChild.textContent.trim.isEmpty) {
          lastChild.parentNode.removeChild(lastChild)
          removeTrailingWhitespaceChildren(node)
        }
      }
    }
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
