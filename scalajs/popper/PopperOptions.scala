// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.popper
import scala.scalajs.js

import org.scalajs.dom.raw.HTMLElement

// Placement

sealed abstract class Placement(val value: String)

case object PlacementAutoStart extends Placement("auto-start")
case object PlacementAuto extends Placement("auto")
case object PlacementAutoEnd extends Placement("auto-end")
case object PlacementTopStart extends Placement("top-start")
case object PlacementTop extends Placement("top")
case object PlacementTopEnd extends Placement("top-end")
case object PlacementRightStart extends Placement("right-start")
case object PlacementRight extends Placement("right")
case object PlacementRightEnd extends Placement("right-end")
case object PlacementBottomEnd extends Placement("bottom-end")
case object PlacementBottom extends Placement("bottom")
case object PlacementBottomStart extends Placement("bottom-start")
case object PlacementLeftEnd extends Placement("left-end")
case object PlacementLeft extends Placement("left")
case object PlacementLeftStart extends Placement("left-start")

// Data

final class Data(
  val arrowElement: js.UndefOr[HTMLElement]
) extends js.Object

// Modifiers

final class OffsetModifier(
  val offset: String
) extends js.Object

object OffsetModifier {
  def apply(x: Double, y: Double): OffsetModifier = new OffsetModifier(
    offset = s"${x.toString}px, ${y.toString}px"
  )
}

final class Modifiers(
  val offset: js.UndefOr[OffsetModifier]
) extends js.Object

object Modifiers {
  def apply(
    offset: js.UndefOr[OffsetModifier] = js.undefined
  ): Modifiers = new Modifiers(
    offset
  )
}

// This is JS interface (@ScalaJSDefine), so:
// - It should have primitive type only and
// - No default value
final class PopperOptions(
  val placement: String,
  val onCreate: js.Function1[Data, Unit],
  val modifiers: js.UndefOr[Modifiers]
) extends js.Object

// Here we can have custom types, default values and do conversions
object PopperOptions {
  def apply(
    placement: Placement = PlacementBottom,
    onCreate: Data => Unit = (_: Data) => (),
    // avoid using Option here to avoid importing the costly JS Converters
    modifiers: js.UndefOr[Modifiers] = js.undefined
  ): PopperOptions = new PopperOptions(
    placement.value,
    onCreate,
    modifiers
  )
}
