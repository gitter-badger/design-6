// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.react

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}

@JSImport("react-dom", JSImport.Namespace, "ReactDOM")
@js.native
object ReactDom extends ReactDom

@js.native
trait ReactDom extends js.Object {
  @JSName("unstable_renderSubtreeIntoContainer")
  def renderSubtreeIntoContainer(component: Any, children: Any, container: js.Object): Unit =
    js.native // linter:ignore UnusedParameter
}
