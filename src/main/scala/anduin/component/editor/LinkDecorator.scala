// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.editor

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.facade.draftjs._
// scalastyle:on underscore.import

object LinkDecorator {

  def apply(): DraftDecorator = {
    DraftDecorator(
      (contentBlock: ContentBlock, cb: Draft.RangeCallback, contentState: ContentState) => {
        contentBlock.findEntityRanges(
          character => {
            DraftUtils.anyToString(character.getEntity()).exists { entityKey =>
              DraftUtils.contentStateGetEntity(contentState, entityKey).exists { draftEntityInstance =>
                draftEntityInstance.getType() == "LINK"
              }
            }
          },
          cb
        )
      },
      (p: DecoratorComponentProps) => {
        val anchorTag = for {
          entityKey <- DraftUtils.anyToString(p.entityKey)
          draftEntityInstance <- DraftUtils.contentStateGetEntity(p.contentState, entityKey)
        } yield {
          val url = DecoratorUtils.getAttribute(draftEntityInstance.getData(), "url")
          <.a(^.href := url, PropsChildren.fromRawProps(p)).rawElement
        }
        anchorTag.getOrElse(<.a().rawElement)
      }
    )
  }
}
