// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

import org.scalajs.dom.raw.{Node, TreeWalker}

@js.native
@JSGlobal("window.document")
object DocumentAlias extends js.Object {
  def createTreeWalker(root: Node, whatToShow: Int, filter: NodeFilterAlias): TreeWalker = js.native
}
