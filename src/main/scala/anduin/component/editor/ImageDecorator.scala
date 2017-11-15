// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.editor

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._

import anduin.scalajs.draftjs._
// scalastyle:on underscore.import

object ImageDecorator {

  def apply(): DraftDecorator = {
    DraftDecorator(
      (contentBlock: ContentBlock, cb: Draft.RangeCallback, contentState: ContentState) => {
        contentBlock.findEntityRanges(
          character => {
            DraftUtils.anyToString(character.getEntity()).exists { entityKey =>
              DraftUtils.contentStateGetEntity(contentState, entityKey).exists { draftEntityInstance =>
                draftEntityInstance.getType() == "IMAGE"
              }
            }
          },
          cb
        )
      },
      (p: DecoratorComponentProps) => {
        val imageTag = for {
          entityKey <- DraftUtils.anyToString(p.entityKey)
          draftEntityInstance <- DraftUtils.contentStateGetEntity(p.contentState, entityKey)
        } yield {
          val data = draftEntityInstance.getData()
          val src = DecoratorUtils.getAttribute(data, "src")
          val width = DecoratorUtils.getAttribute(data, "width")
          val height = DecoratorUtils.getAttribute(data, "height")
          val title = DecoratorUtils.getAttribute(data, "alt")

          if (!src.startsWith("http://") && !src.startsWith("https://")) {
            // We will treat it as an attachment
            // Returning `null` here means that this decorator will not render anything
            null // scalastyle:ignore null
          } else {
            // TODO: Should we wrap the image inside a container which has a limit size
            <.img(
              ^.cls := "db",
              ^.src := src,
              TagMod.when(width.nonEmpty)(VdomAttr("width") := width),
              TagMod.when(height.nonEmpty)(VdomAttr("height") := height),
              TagMod.when(title.nonEmpty)(^.alt := title)
            ).rawElement
          }
        }
        imageTag.getOrElse(<.img().rawElement)
      }
    )
  }
}
