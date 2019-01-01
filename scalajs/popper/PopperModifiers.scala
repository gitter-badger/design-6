// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.popper

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

// ===

final class PopperModifiers(
  val offset: js.UndefOr[PopperModifiers.Offset] = js.undefined,
  val preventOverflow: js.UndefOr[PopperModifiers.PreventOverflow] = js.undefined
) extends js.Object

object PopperModifiers {

  final class Offset(
    val x: Double,
    val y: Double
  ) extends js.Object {
    // actual prop in the lib
    val offset: String = s"${x.toString}px, ${y.toString}px"
  }

  // ===

  final class PreventOverflow(
    val boundariesElement: PreventOverflow.Boundaries
  ) extends js.Object {
    @JSName("boundariesElement")
    val boundariesElementRaw: String = boundariesElement.raw
  }

  object PreventOverflow {
    sealed abstract class Boundaries(val raw: String)
    case object BoundariesScrolledParent extends Boundaries("scrollParent")
    case object BoundariesWindow extends Boundaries("window")
    case object BoundariesViewPort extends Boundaries("viewport")
  }
}
