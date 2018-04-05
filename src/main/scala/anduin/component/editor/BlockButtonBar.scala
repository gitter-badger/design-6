// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor

import anduin.component.icon.{Icon, Iconv2}
import anduin.scalajs.slate.Slate.{Change, Value}
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[editor] final case class BlockButtonBar(
  value: Value,
  onChange: Change => Callback
) {
  def apply(): VdomElement = BlockButtonBar.component(this)
}

private[editor] object BlockButtonBar {

  private val ComponentName = this.getClass.getSimpleName

  private val DefaultNodeType = ParagraphNode.nodeType

  private case class Backend(scope: BackendScope[BlockButtonBar, _]) {

    private def hasBlock(value: Value, blockNode: NodeType) = {
      value.blocks.some(item => item.nodeType == blockNode.nodeType)
    }

    // scalastyle:off cyclomatic.complexity
    private def onClick(nodeType: NodeType) = {
      for {
        props <- scope.props
        value = props.value
        change = value.change()
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
              val isType = value.blocks.some(block => {
                doc.getClosest(block.key, parent => parent.nodeType == nodeType.nodeType).isDefined
              })
              if (isList && isType) {
                change
                  .setBlock(DefaultNodeType)
                  .unwrapBlock(UnorderedListNode.nodeType)
                  .unwrapBlock(OrderedListNode.nodeType)
              } else if (isList) {
                change
                  .unwrapBlock(wrapBlockType)
                  .wrapBlock(nodeType.nodeType)
              } else {
                change
                  .setBlock(ListItemNode.nodeType)
                  .wrapBlock(nodeType.nodeType)
              }
              props.onChange(change)

            case blockNode: BlockNode =>
              val isActive = hasBlock(value, blockNode)
              val isList = hasBlock(value, ListItemNode)
              val newNodeType = if (isActive) DefaultNodeType else nodeType.nodeType

              if (isList) {
                change
                  .setBlock(newNodeType)
                  .unwrapBlock(UnorderedListNode.nodeType)
                  .unwrapBlock(OrderedListNode.nodeType)
              } else {
                change.setBlock(newNodeType)
              }
              props.onChange(change)

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
          (BlockQuoteNode, Iconv2.blockquote(), "Quote"),
          (UnorderedListNode, Icon.bulletPoint(), "Bulleted List"),
          (OrderedListNode, Icon.orderedList(), "Numbered List")
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
