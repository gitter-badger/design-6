// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.email

import org.scalajs.dom.raw.{Document, Node}

import anduin.component.util.NodeListSeq

// scalastyle:off multiple.string.literals
private[email] object WhitespaceCleaner {

  // Remove unnecessary whitespace from document.
  // It removes the duplicated multiple <br> (such as <br><br>)
  @SuppressWarnings(Array("org.wartremover.warts.While"))
  def removeUnnecessaryWhitespace(doc: Document): Unit = {
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
}
// scalastyle:on multiple.string.literals
