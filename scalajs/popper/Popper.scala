// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.popper

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import org.scalajs.dom.raw.{Element, HTMLElement}

// We have to import the popper from external resource instead of npm as
// ```
// @JSImport("popper.js-lite", JSImport.Default, "Popper")
// ```
// because at the time of writing this, the latest version on npm (2.0.0-rc.1)
// has problem with `destroy()` method

// The UMD version of popper can be downloaded from
// https://unpkg.com/popper.js@1.14.3/dist/umd/popper.min.js
@JSImport("./external/js/popper/popper-1.14.3.min.js", JSImport.Default, "Popper")
@js.native
class Popper(val reference: Element, val popper: Element, val options: PopperOptions) extends js.Object {
  def destroy(): Unit = js.native
  def update(): Unit = js.native
}

object Popper extends js.Object {
  type ModifierFn = js.Function2[Data, js.Object, Data]
}

final class PopperOptions(
  // The placement can be
  // - auto
  // - top
  // - right
  // - bottom
  // - left
  // Each placement can have a variation
  // - X-start
  // - X-end
  val placement: String = "bottom",
  val positionFixed: Boolean = false,
  val onCreate: js.Function1[Data, Unit] = (_: Data) => (),
  val onUpdate: js.Function1[Data, Unit] = (_: Data) => (),
  // TODO: I don't know why `js.UndefOr[js.Modifiers]` doesn't work
  val modifiers: js.UndefOr[js.Object] = js.undefined
) extends js.Object

// See https://github.com/FezVrasta/popper.js/blob/HEAD/docs/_includes/popper-documentation.md#dataObject
final class Data(
  val instance: Popper,
  val placement: String,
  val originalPlacement: String,
  val flipped: Boolean,
  val hide: Boolean,
  val arrowElement: HTMLElement,
  val styles: js.Object,
  val arrowStyles: js.Object,
  val boundaries: js.Object,
  val offsets: Offsets
) extends js.Object

final class Offsets(
  val popper: Offset,
  val reference: Offset,
  val arrow: Offset
) extends js.Object

final class Offset(
  val top: Double,
  val left: Double,
  val width: Double,
  val height: Double
) extends js.Object

// Modifiers
// See https://popper.js.org/popper-documentation.html#modifiers
final class Modifiers(
  val offset: js.UndefOr[OffsetModifier] = js.undefined
) extends js.Object

final class OffsetModifier(
  val offset: String = "0",
  val enabled: Boolean = true,
  val order: Int = 200,
  val fn: js.UndefOr[Popper.ModifierFn] = js.undefined
) extends js.Object
