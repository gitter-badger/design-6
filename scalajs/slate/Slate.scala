// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.slate

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}

import anduin.scalajs.immutable.{ImmutableList, ImmutableMap, ImmutableSet}

// scalastyle:off multiple.string.literals
object Slate {

  // See https://docs.slatejs.org/slate-core/value
  @JSImport("slate", "Value")
  @js.native
  object Value extends js.Object {
    def fromJSON(json: js.Object): Value = js.native // linter:ignore UnusedParameter
  }

  @js.native
  trait Value extends js.Object {
    def activeMarks: ImmutableSet[Mark] = js.native // linter:ignore UnusedParameter
    def blocks: ImmutableList[Block] = js.native // linter:ignore UnusedParameter
    def inlines: ImmutableList[Inline] = js.native // linter:ignore UnusedParameter
    def change(): Change = js.native
    def hasUndos: Boolean = js.native
    def hasRedos: Boolean = js.native
    def isExpanded: Boolean = js.native
    val document: Document = js.native
  }

  // See https://docs.slatejs.org/slate-core/document
  @JSImport("slate", "Document")
  @js.native
  final class Document extends Node

  // See https://docs.slatejs.org/slate-core/change
  @JSImport("slate", "Change")
  @js.native
  final class Change(val value: Value) extends js.Object {
    def addMark(mark: String): Change = js.native // linter:ignore UnusedParameter
    def toggleMark(mark: String): Change = js.native // linter:ignore UnusedParameter
    def undo(): Change = js.native
    def redo(): Change = js.native
    def collapseToEnd(): Change = js.native
    def unwrapInline(inlineType: String): Change = js.native // linter:ignore UnusedParameter
    def wrapInline(props: js.Object): Change = js.native // linter:ignore UnusedParameter
    def insertText(text: String): Change = js.native // linter:ignore UnusedParameter
    def extend(num: Int): Change = js.native // linter:ignore UnusedParameter
    def setBlock(block: String): Change = js.native // linter:ignore UnusedParameter
    def unwrapBlock(block: String): Change = js.native // linter:ignore UnusedParameter
    def wrapBlock(block: String): Change = js.native // linter:ignore UnusedParameter
    def insertInlineAtRange(range: Range, properties: js.Object): Change = js.native // linter:ignore UnusedParameter
    def wrapInlineAtRange(range: Range, properties: js.Object): Change = js.native // linter:ignore UnusedParameter
    def moveOffsetsTo(anchorOffset: Int, focusOffset: Int): Change = js.native // linter:ignore UnusedParameter
    def deselect(): Change = js.native
    def extendToStartOf(node: Node): Change = js.native // linter:ignore UnusedParameter
  }

  final class WrapInlineProps(
    @JSName("type") val inlineType: String,
    val data: js.Object
  ) extends js.Object

  // See https://docs.slatejs.org/slate-core/mark
  @JSImport("slate", "Mark")
  @js.native
  final class Mark(
    @JSName("type") val markType: String,
    val kind: String = "mark"
  ) extends js.Object

  // See https://docs.slatejs.org/slate-core/node
  @js.native
  trait Node extends js.Object {
    val key: String = js.native
    val kind: String = js.native
    val data: Data = js.native
    @JSName("type") val nodeType: String = js.native
    val nodes: ImmutableList[Node] = js.native
    val text: String = js.native

    def getClosest(key: String, @JSName("match") find: js.Function1[Node, Boolean]): js.UndefOr[Node] = js.native // linter:ignore UnusedParameter
    def getTexts(): ImmutableList[Text] = js.native
  }

  // See https://docs.slatejs.org/slate-core/inline
  @JSImport("slate", "Inline")
  @js.native
  final class Inline(
    @JSName("type") val inlineType: String = js.native // link, hashtag, for example
  ) extends js.Object

  // See https://docs.slatejs.org/slate-core/data
  @JSImport("slate", "Data")
  @js.native
  final class Data extends ImmutableMap[String, js.Object]

  // See https://docs.slatejs.org/slate-core/block
  @JSImport("slate", "Block")
  @js.native
  final class Block extends Node

  // See https://docs.slatejs.org/slate-core/range
  @JSImport("slate", "Range")
  @js.native
  object Range extends js.Object {
    def create(properties: js.Object): Range = js.native // linter:ignore UnusedParameter
  }

  final class Range(
    val anchorKey: String,
    val anchorOffset: Int,
    val focusKey: String,
    val focusOffset: Int
  ) extends js.Object

  // See https://docs.slatejs.org/slate-core/text
  @JSImport("slate", "Text")
  @js.native
  final class Text extends Node
}
// scalastyle:on multiple.string.literals
