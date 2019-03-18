// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.email

import scala.collection.mutable.ListBuffer

import org.scalajs.dom
import org.scalajs.dom.raw.{HTMLDocument, NodeFilter}

import anduin.scalajs.linkifyit.LinkifyIt
import anduin.scalajs.tlds.Tlds

private[email] object AutoLink {

  private val linkFinder = LinkifyIt().tlds(Tlds.TLDS)

  // Find all the text nodes inside given document that looks like a link.
  // Wrap them in an `a` element
  def linkify(doc: HTMLDocument): Unit = {
    val treeWalker = dom.document.createTreeWalker(
      doc.body,
      NodeFilter.SHOW_TEXT,
      null, // scalastyle:ignore null
      false
    )

    // scalastyle:off while
    val ranges = new ListBuffer[(dom.Range, String)]()
    while (Option(treeWalker.nextNode()).nonEmpty) {
      val currentNode = treeWalker.currentNode
      val links = linkFinder.matches(currentNode.textContent)
      Option(links).foreach { links =>
        links.foreach { link =>
          val range = dom.document.createRange()
          range.setStart(currentNode, link.index)
          range.setEnd(currentNode, link.lastIndex)
          ranges += ((range, link.url))
        }
      }
    }
    // scalastyle:on while

    // We need to change the DOM outside of the loop walking through the tree
    // to avoid an infinitive loop
    ranges.foreach {
      case (range, url) =>
        val textNode = range.extractContents()
        val link = dom.document.createElement("a")
        link.setAttribute("href", url)
        link.setAttribute("rel", "noopener")
        link.setAttribute("target", "_blank")
        link.appendChild(textNode)
        range.insertNode(link)
    }
  }
}
