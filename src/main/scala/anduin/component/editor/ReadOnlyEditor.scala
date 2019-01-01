// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor

import scala.scalajs.js

import japgolly.scalajs.react.vdom.VdomElement

import anduin.scalajs.linkifyit.LinkifyIt
import anduin.scalajs.tlds.Tlds

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.scalajs.slate.Slate._
// scalastyle:on underscore.import

final case class ReadOnlyEditor(
  html: String,
  maxLengthOpt: Option[Int] = None
) {
  def apply(): ScalaComponent.Unmounted[_, _, _] = ReadOnlyEditor.component(this)
}

object ReadOnlyEditor {

  private val ComponentName = this.getClass.getSimpleName

  private val linkFinder = LinkifyIt().tlds(Tlds.TLDS)

  private case class State(value: Value)

  private class Backend(scope: BackendScope[ReadOnlyEditor, State]) {

    def render(state: State): VdomElement = {
      <.div(
        RichEditor(
          placeholder = "",
          value = state.value,
          readOnly = true,
          onChange = _ => Callback.empty
        )()
      )
    }

    // TODO: @nghuuphuoc Fix it. It doesn't work properly in case there're more than one link in same text element
    def findLinks: Callback = {
      for {
        state <- scope.state
        value = state.value
        change = value.change()
        texts = value.document.getTexts()
        _ <- {
          texts.foreach { textItem =>
            // Find links in text and turn them into real link nodes
            val text = textItem.text
            val links = linkFinder.matches(text)
            if (links != null) { // scalastyle:ignore null
              links.foreach { link =>
                val range = Range.create(
                  js.Dynamic.literal(
                    anchorKey = textItem.key,
                    anchorOffset = link.index,
                    focusKey = textItem.key,
                    focusOffset = link.lastIndex
                  )
                )
                val inline = js.Dynamic.literal(
                  `type` = "link",
                  data = js.Dynamic.literal(href = link.url)
                )
                change
                  .extendToStartOf(textItem)
                  .wrapInlineAtRange(range, inline)
              }
            }
          }
          scope.modState(_.copy(value = change.value))
        }
      } yield ()
    }
  }

  private val component = ScalaComponent
    .builder[ReadOnlyEditor](ComponentName)
    .initialStateFromProps { props =>
      val body = props.maxLengthOpt.map(props.html.substring(0, _)).getOrElse(props.html)
      State(value = Serializer.deserialize(body))
    }
    .renderBackend[Backend]
    .componentDidMount(_.backend.findLinks)
    .build
}
