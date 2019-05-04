// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.facades.popper

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}
import org.scalajs.dom.raw.HTMLElement

import scala.scalajs.js.|

@JSImport("popper.js", JSImport.Default, "Popper")
@js.native
class Popper(
  val reference: HTMLElement,
  val popper: HTMLElement,
  val options: Popper.Options
) extends js.Object {
  def destroy(): Unit = js.native
  def update(): Unit = js.native
  def scheduleUpdate(): Unit = js.native
}

object Popper {

  trait Data extends js.Object {
    def instance: Popper
    def arrowElement: js.UndefOr[HTMLElement]
    def placement: String
  }

  final class Options(
    val placement: PopperPlacement = PopperPlacement.BottomMiddle,
    val onCreate: js.Function1[Data, Unit] = _ => (),
    val onUpdate: js.Function1[Data, Unit] = _ => (),
    val modifiers: js.UndefOr[Modifiers] = js.undefined
  ) extends js.Object {
    @JSName("placement")
    val placementRaw: String = placement.raw
  }

  final class Modifiers(
    val offset: js.UndefOr[Offset] = js.undefined,
    val preventOverflow: js.UndefOr[Overflow] = js.undefined
  ) extends js.Object

  final class Offset(
    val offset: String | Int
  ) extends js.Object

  final class Overflow(
    val boundaries: Boundaries
  ) extends js.Object {
    val boundariesElement: String = boundaries.raw
  }

  sealed abstract class Boundaries(val raw: String)
  object Boundaries {
    object ScrolledParent extends Boundaries("scrollParent")
    object Window extends Boundaries("window")
    object ViewPort extends Boundaries("viewport")
  }
}
