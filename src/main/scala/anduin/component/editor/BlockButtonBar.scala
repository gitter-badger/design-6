// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor

import anduin.component.icon.Icon
import anduin.scalajs.slate.Slate.Value
import anduin.scalajs.slate.SlateReact
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[editor] final case class BlockButtonBar(
  value: Value,
  editorRef: () => SlateReact.EditorComponentRef
) {
  def apply(): VdomElement = BlockButtonBar.component(this)
}

private[editor] object BlockButtonBar {

  private type Props = BlockButtonBar

  private val DefaultNodeType = ParagraphNode.nodeType

  private class Backend(scope: BackendScope[Props, _]) {

    private def hasBlock(value: Value, blockNode: NodeType) = {
      blockNode match {
        case UnorderedListNode | OrderedListNode =>
          value.blocks.headOption.fold(false) { first =>
            val parent = value.document.getParent(first.key)
            parent.fold(false) { p =>
              val hasItemBlock = value.blocks.exists(_.nodeType == ListItemNode.nodeType)
              hasItemBlock && p.nodeType == blockNode.nodeType
            }
          }
        case _ =>
          value.blocks.exists(_.nodeType == blockNode.nodeType)
      }
    }

    // scalastyle:off cyclomatic.complexity
    private def onClick(nodeType: NodeType) = {
      for {
        props <- scope.props
        editorInstance <- props.editorRef().get
        editor = editorInstance.raw
        value = props.value
        _ <- Callback {
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
                  .setBlocks(DefaultNodeType)
                  .unwrapBlock(UnorderedListNode.nodeType)
                  .unwrapBlock(OrderedListNode.nodeType)
              } else if (isList) {
                editor
                  .unwrapBlock(wrapBlockType)
                  .wrapBlock(nodeType.nodeType)
              } else {
                editor
                  .setBlocks(ListItemNode.nodeType)
                  .wrapBlock(nodeType.nodeType)
              }

            case blockNode: BlockNode =>
              val isActive = hasBlock(value, blockNode)
              val isList = hasBlock(value, ListItemNode)
              val newNodeType = if (isActive) DefaultNodeType else nodeType.nodeType

              if (isList) {
                editor
                  .setBlocks(newNodeType)
                  .unwrapBlock(UnorderedListNode.nodeType)
                  .unwrapBlock(OrderedListNode.nodeType)
              } else {
                editor.setBlocks(newNodeType)
              }

            case _: MarkNode | _: InlineNode =>
              ()
          }
        }
      } yield ()
    }
    // scalastyle:on cyclomatic.complexity

    def render(props: Props): VdomElement = {
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
    .builder[BlockButtonBar](this.getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .build
}
