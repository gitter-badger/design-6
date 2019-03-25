// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.downshift

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:off underscore.import

// A for Item
class Downshift[A] {

  private type RenderProps = DownshiftRenderProps[A]
  private type State = DownshiftState[A]
  private type StateChanges = DownshiftStateChanges[A]

  class Props(
    val onChange: js.Function1[A, Unit],
    val itemToString: js.Function1[A | Null, String],
    val stateReducer: js.Function2[State, StateChanges, StateChanges],
    val children: js.Function1[RenderProps, raw.React.Node],
    // ===
    val onInputValueChange: js.UndefOr[js.Function1[String, Unit]],
    val inputValue: js.UndefOr[String],
    val selectedItem: js.UndefOr[A | Null]
  ) extends js.Object

  // Don't try to type this. It's a complicated internal type of ScalaJSReact
  // that eventually will be VdomElement
  val component = JsComponent[Props, Children.None, Null](Downshift)
}

@JSImport("downshift", JSImport.Default)
@js.native
object Downshift extends js.Object {
  val stateChangeTypes: DownshiftStateChangeTypes = js.native
}
