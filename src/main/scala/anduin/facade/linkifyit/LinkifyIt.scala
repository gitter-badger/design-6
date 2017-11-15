// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.facade.linkifyit

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobal, JSName}

// See https://github.com/markdown-it/linkify-it/

@js.native
trait LinkifyIt extends js.Object {

  def tlds(list: js.Array[String], keepOld: Boolean = false): LinkifyIt = js.native // linter:ignore UnusedParameter
  @JSName("match")
  def matches(text: String): js.Array[MatchingItem] = js.native // linter:ignore UnusedParameter
}

@js.native
trait MatchingItem extends js.Object {
  val index: Int = js.native
  val lastIndex: Int = js.native
  val url: String = js.native
}

@js.native
@JSGlobal("LinkifyIt")
object LinkifyIt extends js.Object {

  def apply(schemas: js.Object = new js.Object(), options: js.Object = new js.Object()): LinkifyIt = js.native // linter:ignore UnusedParameter
}
