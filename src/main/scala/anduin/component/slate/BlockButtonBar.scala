// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.slate

import anduin.component.icon.Icon
import anduin.scalajs.slate.Slate.{Change, Value}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[slate] final case class BlockButtonBar(
  value: Value,
  onChange: Change => Callback
) {
  def apply(): ScalaComponent.Unmounted[_, _, _] = BlockButtonBar.component(this)
}

private[slate] object BlockButtonBar {

  private val ComponentName = this.getClass.getSimpleName

  private final val DefaultNodeType = ParagraphNode.nodeType

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
        List(
          (BlockQuoteNode, Icon.speechBubble(), "Quote"), // TODO: Change the icon
          (UnorderedListNode, Icon.bulletPoint(), "Unordered list"),
          (OrderedListNode, Icon.orderedList(), "Ordered list")
        ).toVdomArray { case (blockNode, icon, tip) =>
          ToolbarButton(
            nodeType = blockNode,
            tip = tip,
            active = hasBlock(props.value, blockNode),
            onClick = onClick
          )(icon)
        }
      )
    }
  }

  private val component = ScalaComponent.builder[BlockButtonBar](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}
