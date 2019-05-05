// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.facades.reactvirtualized

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:off underscore.import

object ReactVirtualizedAutoSizer {

  trait RenderProps extends js.Object {
    val width: Int
    val height: Int
  }

  class Props(
    val children: js.Function1[RenderProps, raw.React.Node],
    val disableWidth: js.UndefOr[Boolean] = js.undefined,
    val disableHeight: js.UndefOr[Boolean] = js.undefined
  ) extends js.Object

  @JSImport("react-virtualized/dist/commonjs/AutoSizer", JSImport.Default)
  @js.native
  object RawComponent extends js.Object

  val component = JsComponent[Props, Children.None, Null](RawComponent)
}
