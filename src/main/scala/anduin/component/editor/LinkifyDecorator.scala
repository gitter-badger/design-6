// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.editor

import anduin.facade.linkifyit.LinkifyIt
import anduin.facade.tlds.Tlds

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._

import anduin.facade.draftjs._
// scalastyle:on underscore.import

object LinkifyDecorator {

  private val linkify = LinkifyIt().tlds(Tlds.TLDS)

  // scalastyle:off null
  def apply(): DraftDecorator = {
    DraftDecorator(
      (contentBlock: ContentBlock, cb: Draft.RangeCallback, contentState: ContentState) => {
        val links = linkify.matches(contentBlock.getText())
        if (links != null) {
          0.until(links.length).foreach { index =>
            cb(links(index).index, links(index).lastIndex)
          }
        }
        ()
      },
      (p: DecoratorComponentProps) => {
        val links = linkify.matches(p.decoratedText)
        if (links == null || links.length == 0) {
          null
        } else {
          <.a(^.href := links(0).url, p.decoratedText).rawElement
        }
      }
    )
  }
  // scalastyle:on null
}
