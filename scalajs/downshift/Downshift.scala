// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.downshift

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:off underscore.import

// A for Item
class Downshift[A] {

  // Also alias for public uses
  type RenderProps = DownshiftRenderProps[A]
  type State = DownshiftState[A]
  type StateChanges = DownshiftStateChanges[A]

  class Props(
    val onChange: js.Function1[A, Unit],
    val itemToString: js.Function1[A, String],
    val stateReducer: js.Function2[State, StateChanges, StateChanges],
    val children: js.Function1[RenderProps, raw.React.Node],
    // ===
    val selectedItem: js.UndefOr[A | Null]
  ) extends js.Object

  val component =
    JsComponent[Props, Children.None, Null](Downshift.RawComponent)
}

object Downshift {
  @JSImport("downshift", JSImport.Default, "Downshift")
  @js.native
  object RawComponent extends js.Object

  // Alias for public uses
  val StateChangeTypes = DownshiftStateChangeTypes
}
