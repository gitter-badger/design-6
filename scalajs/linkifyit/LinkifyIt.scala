// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.linkifyit

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}

// See https://github.com/markdown-it/linkify-it/

@js.native
trait LinkifyIt extends js.Object {

  def tlds(list: js.Array[String], keepOld: Boolean = false)
    : LinkifyIt = js.native
  @JSName("match")
  def matches(text: String): js.Array[MatchingItem] = js.native
}

@js.native
trait MatchingItem extends js.Object {
  val index: Int = js.native
  val lastIndex: Int = js.native
  val url: String = js.native
}

@js.native
@JSImport("linkify-it", JSImport.Namespace, "LinkifyIt")
object LinkifyIt extends js.Object {

  def apply(schemas: js.Object = new js.Object(), options: js.Object = new js.Object()): LinkifyIt =
    js.native
}
