// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.scalajs.draftjs

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

// scalastyle:off number.of.types multiple.string.literals
@JSImport("draft-js", JSImport.Namespace, "Draft")
@js.native
object Draft extends js.Object {

  type RangeCallback = js.Function2[Int, Int, Unit]

  // Data conversion
  def convertFromRaw(rawState: RawDraftContentState): ContentState = js.native // linter:ignore UnusedParameter
  def convertToRaw(contentState: ContentState): RawDraftContentState = js.native // linter:ignore UnusedParameter
  def convertFromHTML(rawMarkup: String): BlocksFromHTML = js.native // linter:ignore UnusedParameter

  def getDefaultKeyBinding(e: SyntheticKeyboardEvent): String = js.native // linter:ignore UnusedParameter

  @js.native
  trait RawDraftContentState extends js.Object

  @js.native
  trait BlocksFromHTML extends js.Object {
    val contentBlocks: js.Array[ContentBlock] = js.native
    val entityMap: js.Object = js.native
  }
}

@JSImport("draft-js", "CompositeDecorator", "Draft.CompositeDecorator")
@js.native
class CompositeDecorator(decorators: js.Array[DraftDecorator]) extends js.Object

@js.native
trait DraftDecorator extends js.Object {
  val strategy: DraftDecorator.Strategy = js.native
  val component: DraftDecorator.Component = js.native
}

object DraftDecorator {
  type Strategy = js.Function3[ContentBlock, Draft.RangeCallback, ContentState, Unit]
  type Component = js.Function1[DecoratorComponentProps, js.Object]

  def apply(strategy: Strategy, component: Component): DraftDecorator = {
    js.Dynamic.literal(strategy = strategy, component = component).asInstanceOf[DraftDecorator]
  }
}

@js.native
trait DecoratorComponentProps extends js.Object {
  val children: js.Object = js.native
  val entityKey: js.Any = js.native
  val decoratedText: String = js.native
  val contentState: ContentState = js.native
}

@js.native
trait EditorStateRecord extends js.Object {
  val decorator: CompositeDecorator = js.native
}

object EditorStateRecord {
  def apply(decorator: CompositeDecorator): EditorStateRecord = {
    js.Dynamic.literal(decorator = decorator).asInstanceOf[EditorStateRecord]
  }
}

@JSImport("draft-js", "Editor", "Draft.Editor")
@js.native
object Editor extends js.Object

@js.native
trait SyntheticKeyboardEvent extends js.Object {
  def keyCode: Int = js.native
}

@js.native
trait EditorProps extends js.Object {
  val editorState: EditorState = js.native
  val placeholder: EditorState = js.native
  val readOnly: Boolean = js.native
  val onChange: js.Function1[EditorState, Unit] = js.native
  val keyBindingFn: js.Function1[SyntheticKeyboardEvent, String] = js.native
  val handleKeyCommand: js.Function1[String, String] = js.native
}

@JSImport("draft-js", "EditorState", "Draft.EditorState")
@js.native
object EditorState extends js.Object {
  def createEmpty(): EditorState = js.native
  def createWithContent(contentState: ContentState): EditorState = js.native // linter:ignore UnusedParameter
  def createWithContent(contentState: ContentState, decorator: CompositeDecorator): EditorState = js.native // linter:ignore UnusedParameter
  def redo(editorState: EditorState): EditorState = js.native // linter:ignore UnusedParameter
  def undo(editorState: EditorState): EditorState = js.native // linter:ignore UnusedParameter
  def push(editorState: EditorState, contentState: ContentState, changeType: String): EditorState = js.native // linter:ignore UnusedParameter
  def set(editorState: EditorState, options: EditorStateRecord): EditorState = js.native // linter:ignore UnusedParameter
}

@js.native
trait EditorState extends js.Object {
  def getSelection(): SelectionState = js.native
  def getCurrentContent(): ContentState = js.native
  def getCurrentInlineStyle(): DraftInlineStyle = js.native
  def getRedoStack(): ContentStateStack = js.native
  def getUndoStack(): ContentStateStack = js.native
}

@js.native
trait ContentStateStack extends js.Object {
  def isEmpty(): Boolean = js.native
}

@js.native
trait SelectionState extends js.Object {
  def getStartKey(): String = js.native
  def isCollapsed(): Boolean = js.native
  def getStartOffset(): Int = js.native
}

@JSImport("draft-js", "ContentState", "Draft.ContentState")
@js.native
object ContentState extends js.Object {
  def createFromBlockArray(blocks: js.Array[ContentBlock]): ContentState = js.native // linter:ignore UnusedParameter
  def createFromBlockArray(blocks: js.Array[ContentBlock], entityMap: js.Object): ContentState = js.native // linter:ignore UnusedParameter
  def createFromText(text: String): ContentState = js.native // linter:ignore UnusedParameter
}

@js.native
trait ContentState extends js.Object {
  def getBlockForKey(key: String): ContentBlock = js.native // linter:ignore UnusedParameter
  def hasText(): Boolean = js.native
  def getBlockMap(): BlockMap = js.native
  def getEntity(key: String): DraftEntityInstance = js.native // linter:ignore UnusedParameter
  def createEntity(entityType: String, mutability: String, data: js.Object): ContentState = js.native // linter:ignore UnusedParameter
  def getLastCreatedEntityKey(): String = js.native
  def getFirstBlock(): ContentBlock = js.native
  def getPlainText(): String = js.native
}

@js.native
trait DraftEntityInstance extends js.Object {
  def getType(): String = js.native
  def getData(): js.Dynamic = js.native
}

@js.native
trait BlockMap extends js.Object {
  def first(): ContentBlock = js.native
  def forEach(fn: js.Function1[ContentBlock, Unit]): Unit = js.native // linter:ignore UnusedParameter
}

@js.native
trait ContentBlock extends js.Object {
  def getText(): String = js.native
  def getType(): String = js.native
  def getLength(): Int = js.native
  def getEntityAt(startOffset: Int): String = js.native // linter:ignore UnusedParameter
  def findEntityRanges(filterFn: js.Function1[CharacterMetadata, Boolean], callback: Draft.RangeCallback): Unit = js.native // linter:ignore UnusedParameter
}

@js.native
trait DraftInlineStyle extends js.Object {
  def has(blockType: String): Boolean = js.native // linter:ignore UnusedParameter
  def add(style: String): DraftInlineStyle = js.native // linter:ignore UnusedParameter
}

@js.native
trait CharacterMetadata extends js.Object {
  def getEntity(): js.Any = js.native
}

sealed trait Style {
  def style: String
}

// Inline style
case object BoldInlineStyle extends Style { override val style = "BOLD" }
case object ItalicInlineStyle extends Style { override val style = "ITALIC" }
case object UnderlineInlineStyle extends Style { override val style = "UNDERLINE" }
case object CodeInlineStyle extends Style { override val style = "CODE" }
case object StrikeThroughInlineStyle extends Style { override val style = "STRIKETHROUGH" }

// Block type
case object H1Block extends Style { override val style = "header-one" }
case object H2Block extends Style { override val style = "header-two" }
case object H3Block extends Style { override val style = "header-three" }
case object H4Block extends Style { override val style = "header-four" }
case object H5Block extends Style { override val style = "header-five" }
case object H6Block extends Style { override val style = "header-six" }
case object UnorderedListBlock extends Style { override val style = "unordered-list-item" }
case object OrderedListBlock extends Style { override val style = "ordered-list-item" }
case object BlockQuoteBlock extends Style { override val style = "blockquote" }
case object CodeBlock extends Style { override val style = "code-block" }

// RichUtils
@JSImport("draft-js", "RichUtils", "Draft.RichUtils")
@js.native
object RichUtils extends js.Object {
  def toggleInlineStyle(editorState: EditorState, inlineStyle: String): EditorState = js.native // linter:ignore UnusedParameter
  def toggleBlockType(editorState: EditorState, blockType: String): EditorState = js.native // linter:ignore UnusedParameter
  def toggleLink(editorState: EditorState, targetSelection: SelectionState, entityKey: String): EditorState = js.native // linter:ignore UnusedParameter
}

// Key binding
@JSImport("draft-js", "KeyBindingUtil", "Draft.KeyBindingUtil")
@js.native
object KeyBindingUtil extends js.Object {
  def hasCommandModifier(e: SyntheticKeyboardEvent): Boolean = js.native // linter:ignore UnusedParameter
}
// scalastyle:on number.of.types
