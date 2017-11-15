// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.editor

import japgolly.scalajs.react.vdom.TagOf
import org.scalajs.dom.html.Div

import anduin.component.icon.Icon
import anduin.scalajs.draftjs.{EditorState, OrderedListBlock, RichUtils, UnorderedListBlock}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[editor] final case class BlockStyleBar(
  editorState: EditorState,
  onToggle: EditorState => Callback
) {
  def apply(): ScalaComponent.Unmounted[_, _, _] = BlockStyleBar.component(this)
}

private[editor] object BlockStyleBar {

  private val ComponentName = this.getClass.getSimpleName

  private case class Backend(scope: BackendScope[BlockStyleBar, _]) {

    def render(props: BlockStyleBar): TagOf[Div] = {
      <.div(
        List(
          (UnorderedListBlock, Icon.bulletPoint(), "Unordered list"),
          (OrderedListBlock, Icon.orderedList(), "Odered list")
        ).toVdomArray { case (blockType, icon, tip) =>
          val selection = props.editorState.getSelection()
          val tpe = props.editorState.getCurrentContent()
            .getBlockForKey(selection.getStartKey())
            .getType()

          StyleButton(
            style = blockType,
            tip = tip,
            active = blockType.style == tpe,
            onToggle = blockType => props.onToggle(RichUtils.toggleBlockType(props.editorState, blockType.style))
          )(icon)
        }
      )
    }
  }

  private val component = ScalaComponent.builder[BlockStyleBar](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}

