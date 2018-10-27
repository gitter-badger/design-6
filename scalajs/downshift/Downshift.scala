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
  private type RenderProps = DownshiftRenderProps[A]
  private type State = DownshiftState[A]
  private type StateChanges = DownshiftStateChanges[A]

  class Props(
    val onChange: js.Function1[A, Unit],
    val itemToString: js.Function1[A, String],
    val stateReducer: js.Function2[State, StateChanges, StateChanges],
    val children: js.Function1[RenderProps, raw.React.Node],
    // ===
    val selectedItem: js.UndefOr[A | Null]
  ) extends js.Object

  val component =
    JsComponent[Props, Children.None, Null](Downshift)
}

@JSImport("downshift", JSImport.Default)
@js.native
object Downshift extends js.Object {
  val stateChangeTypes: DownshiftStateChangeTypes = js.native
}
