// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.popper

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import org.scalajs.dom.raw.Element

@JSImport("popper.js", JSImport.Default, "Popper")
@js.native
class Popper(
  reference: Element,
  popper: Element,
  options: PopperOptions
) extends js.Object {
  def destroy(): Unit = js.native
  def update(): Unit = js.native
  def scheduleUpdate(): Unit = js.native
}
