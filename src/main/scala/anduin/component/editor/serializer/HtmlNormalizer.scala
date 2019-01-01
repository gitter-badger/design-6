// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.serializer

import scala.scalajs.js

import anduin.component.editor.DivNode
import anduin.scalajs.immutable.ImmutableList
import anduin.scalajs.slate.Slate
import anduin.scalajs.slate.Slate.ValueJson

object HtmlNormalizer {

  private def isBlockNode(node: Slate.Node) = node.`object` == "block"

  private def createNewBlock(openWrapperBlock: List[Slate.Node]) = {
    Slate.Block.fromJSON {
      js.Dynamic.literal(
        `type` = DivNode.nodeType,
        `object` = "block",
        nodes = ImmutableList.fromList(openWrapperBlock)
      )
    }
  }

  private def joinBlock(prevNodes: List[Slate.Node], openWrapperBlock: List[Slate.Node]) = {
    if (openWrapperBlock.nonEmpty) {
      prevNodes :+ createNewBlock(openWrapperBlock)
    } else {
      prevNodes
    }
  }

  private def shouldAddChild(child: Slate.Node, openWrapperBlock: List[Slate.Node]) = {
    openWrapperBlock.nonEmpty && child.nodeType == DivNode.nodeType && child.nodes.exists(_.isEmpty)
  }

  private def wrapMixedBlockList(nodes: ImmutableList[Slate.Node]): ImmutableList[Slate.Node] = {
    val blockChildrenCount = nodes.count(isBlockNode)
    if (blockChildrenCount > 0 && blockChildrenCount < nodes.length) {
      (joinBlock _).tupled {
        nodes.foldLeft(List.empty[Slate.Node] -> List.empty[Slate.Node]) {
          case ((prevNodes, openWrapperBlock), child) =>
            if (isBlockNode(child)) {
              val joinedBlock = joinBlock(prevNodes, openWrapperBlock)
              val childOpt = Option(child).filterNot(shouldAddChild(_, openWrapperBlock))
              joinedBlock ++ childOpt -> List()
            } else {
              prevNodes -> (openWrapperBlock :+ child)
            }
        }
      }
    } else {
      nodes
    }
  }

  private def wrapMixedBlockNode(node: Slate.Node): Slate.Node = {
    node.nodes.fold {
      node
    } { nodes =>
      node.set("nodes", wrapMixedBlockList(nodes.map(wrapMixedBlockNode)))
    }
  }

  def apply(valueJson: ValueJson): Slate.Value = {
    Slate.Value.fromJSON {
      new ValueJson(
        document = wrapMixedBlockNode(
          Slate.Document.fromJSON(valueJson.document)
        ),
        `object` = "value"
      )
    }
  }
}
