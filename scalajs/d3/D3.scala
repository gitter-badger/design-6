// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.d3

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import org.scalajs.dom.raw.Element

@JSImport("d3", JSImport.Namespace)
@js.native
// Provide the shortcuts to access sub-packages
object D3 extends js.Object {
  // selection
  def select(selector: String): Selection[Element] = js.native

  // shape
  def pie(): PieGenerator = js.native
  def arc(): ArcGenerator = js.native
}
