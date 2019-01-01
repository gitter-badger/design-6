// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.popper

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}

import org.scalajs.dom.raw.{Element, HTMLElement}

@JSImport("popper.js", JSImport.Default, "Popper")
@js.native
class Popper(
  reference: Element,
  popper: Element,
  options: Popper.Options
) extends js.Object {
  def destroy(): Unit = js.native
  def update(): Unit = js.native
  def scheduleUpdate(): Unit = js.native
}

object Popper {

  final class Data(
    val arrowElement: js.UndefOr[HTMLElement]
  ) extends js.Object

  final class Options(
    val placement: PopperPlacement = PopperPlacement.Bottom,
    val onCreate: js.Function1[Data, Unit] = _ => (),
    val modifiers: js.UndefOr[PopperModifiers] = js.undefined
  ) extends js.Object {
    @JSName("placement")
    val placementRaw: String = placement.raw
  }

}
