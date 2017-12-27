// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.slate

sealed trait NodeType {
  def nodeType: String
}

// Mark node
sealed trait MarkNode extends NodeType

case object BoldNode extends MarkNode { override val nodeType = "bold" }
case object ItalicNode extends MarkNode { override val nodeType = "italic" }
case object UnderlineNode extends MarkNode { override val nodeType = "underline" }
case object CodeNode extends MarkNode { override val nodeType = "code" }
case object StrikeThroughNode extends MarkNode { override val nodeType = "strikethrough" }

// Block node
sealed trait BlockNode extends NodeType

case object ListItemNode extends BlockNode { override val nodeType = "list-item" }
case object OrderedListNode extends BlockNode { override val nodeType = "ordered-list" }
case object UnorderedListNode extends BlockNode { override val nodeType = "unordered-list" }
case object BlockQuoteNode extends BlockNode { override val nodeType = "block-quote" }
case object ParagraphNode extends BlockNode { override val nodeType = "paragraph" }

// Inline node
sealed trait InlineNode extends NodeType

case object LinkNode extends InlineNode { override val nodeType = "link" }
