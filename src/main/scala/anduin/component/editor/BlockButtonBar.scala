// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor

import anduin.component.icon.Icon
import anduin.scalajs.slate.Slate.{Editor, Value}
import anduin.scalajs.slate.SlateReact
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[editor] final case class BlockButtonBar(
  editorRef: () => SlateReact.EditorComponentRef,
  value: Value,
  onChange: Editor => Callback
) {
  def apply(): VdomElement = BlockButtonBar.component(this)
}

private[editor] object BlockButtonBar {

  private val ComponentName = this.getClass.getSimpleName

  private val DefaultNodeType = ParagraphNode.nodeType

  private class Backend(scope: BackendScope[BlockButtonBar, _]) {

    private def hasBlock(value: Value, blockNode: NodeType) = {
      value.blocks.exists(item => item.nodeType == blockNode.nodeType)
    }

    // scalastyle:off cyclomatic.complexity
    private def onClick(nodeType: NodeType) = {
      for {
        props <- scope.props
        editorInstance <- props.editorRef().get
        editor = editorInstance.raw
        value = props.value
        _ <- {
          nodeType match {
            // See https://github.com/ianstormtaylor/slate/blob/master/examples/rich-text/index.js
            case UnorderedListNode | OrderedListNode =>
              val wrapBlockType = if (nodeType.nodeType == UnorderedListNode.nodeType) {
                OrderedListNode.nodeType
              } else {
                UnorderedListNode.nodeType
              }
              val isList = hasBlock(value, ListItemNode)
              val doc = value.document
              val isType = value.blocks.exists(block => {
                doc.getClosest(block.key, parent => parent.nodeType == nodeType.nodeType).isDefined
              })
              if (isList && isType) {
                editor
                  .setBlock(DefaultNodeType)
                  .unwrapBlock(UnorderedListNode.nodeType)
                  .unwrapBlock(OrderedListNode.nodeType)
              } else if (isList) {
                editor
                  .unwrapBlock(wrapBlockType)
                  .wrapBlock(nodeType.nodeType)
              } else {
                editor
                  .setBlock(ListItemNode.nodeType)
                  .wrapBlock(nodeType.nodeType)
              }
              props.onChange(editor)

            case blockNode: BlockNode =>
              val isActive = hasBlock(value, blockNode)
              val isList = hasBlock(value, ListItemNode)
              val newNodeType = if (isActive) DefaultNodeType else nodeType.nodeType

              if (isList) {
                editor
                  .setBlock(newNodeType)
                  .unwrapBlock(UnorderedListNode.nodeType)
                  .unwrapBlock(OrderedListNode.nodeType)
              } else {
                editor.setBlock(newNodeType)
              }
              props.onChange(editor)

            case _: MarkNode | _: InlineNode =>
              Callback.empty
          }
        }
      } yield ()
    }
    // scalastyle:on cyclomatic.complexity

    def render(props: BlockButtonBar): VdomElement = {
      <.div(
        Style.flexbox.flex,
        List(
          (BlockQuoteNode, Icon(name = Icon.Glyph.Citation)(), "Quote"),
          (UnorderedListNode, Icon(name = Icon.Glyph.ListBullet)(), "Bulleted List"),
          (OrderedListNode, Icon(name = Icon.Glyph.ListNumber)(), "Numbered List")
        ).toVdomArray {
          case (blockNode, icon, tip) =>
            ToolbarButton(
              key = blockNode.nodeType,
              tip = tip,
              active = hasBlock(props.value, blockNode),
              onClick = onClick(blockNode)
            )(icon)
        }
      )
    }
  }

  private val component = ScalaComponent
    .builder[BlockButtonBar](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}
