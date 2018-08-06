// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.react

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}

import org.scalajs.dom.raw.Node

@JSImport("react-dom", JSImport.Namespace, "ReactDOM")
@js.native
object ReactDom extends ReactDom

@js.native
trait ReactDom extends js.Object {
  @JSName("unstable_renderSubtreeIntoContainer")
  def renderSubtreeIntoContainer(
    parentComponent: js.Object,
    component: Any,
    container: Node
  ): Unit = js.native
}
