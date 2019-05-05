// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.facades.reactvirtualized

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:off underscore.import

object ReactVirtualizedList {

  trait RowRenderProps extends js.Object {
    val index: Int
    val isScrolling: Boolean
    val isVisible: Boolean
    val key: String
    val parent: js.Object
    val style: js.Object
  }

  class Props(
    val height: Int,
    val rowCount: Int,
    val rowHeight: Int | js.Function1[Int, Int],
    val rowRenderer: js.Function1[RowRenderProps, raw.React.Node],
    val width: Int
  ) extends js.Object

  @JSImport("react-virtualized/dist/commonjs/List", JSImport.Default)
  @js.native
  object RawComponent extends js.Object

  val component = JsComponent[Props, Children.None, Null](RawComponent)
}
